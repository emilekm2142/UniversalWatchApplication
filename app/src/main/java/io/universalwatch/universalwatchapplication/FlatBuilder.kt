package io.universalwatch.universalwatchapplication

import AllWatchSerialize.*
import AllWatchSerialize.Type.HorizontalLayout
import AllWatchSerialize.Type.Text
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.google.flatbuffers.FlatBufferBuilder
import com.universalwatch.uwlib.ListItemType
import com.universalwatch.uwlib.WatchUtils
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream


/**
 * Created by emile on 04.10.2018.
 */
class FlatBuilder {
    private lateinit var context: Context
    fun makeColor(hex: String): Int {
        return Integer.parseInt(hex.replace("#", ""), 16)
    }

    constructor(context: Context) {
        this.context = context

    }

    fun translate(message: JSONObject): FlatBufferBuilder {
        var buffer = FlatBufferBuilder(1)
        val fName = buffer.createString(message.getString("friendlyName"))
        val pName = buffer.createString(message.getString("targetPackage"))
        Id.startId(buffer)

        Id.addFriendlyName(buffer, fName)

        Id.addPackageName(buffer, pName)
        val id = Id.endId(buffer)


        var x = message
        var cmd: Int = 0
        var commandType: Byte? = null
        when (message.getString("type")) {
            "application" -> {
                var icon: Int? = null
                if (message.getJSONObject("data").has("icon")) icon = serializeImage(buffer, Uri.parse(message.getJSONObject("data").getString("icon")))
                Application.startApplication(buffer)
                icon?.let {
                    Application.addIcon(buffer, it)
                }
                Application.addId(buffer, id)
                Application.addReinstall(buffer, false)
                //no icon, requirements, voice phrases and widget
                cmd = Application.endApplication(buffer)
                commandType = CommandType.Application
            }
            "view", "updateView" -> {
                if (message.getString("type") == "view") {
                    commandType = CommandType.View
                } else {
                    commandType = CommandType.Update
                }

                val data = message.getJSONObject("data")
                var serializedActions = mutableListOf<Int>()
                //View.createActionsVector(buffer, serializedActions.toIntArray())
                if (data.has("actions")) {
                    val actions = data.getJSONArray("actions")
                    serializedActions = this.serializeActions(buffer, actions).toMutableList()
                }
                val serializedActionsInBuffer = View.createActionsVector(buffer, serializedActions.toIntArray())
                //TODO: add more datatypes
                val datatype = hashMapOf(
                        "text" to DefaultDatatypes.Text,
                        "list" to DefaultDatatypes.List,
                        "media" to DefaultDatatypes.Media,
                        "messaging" to DefaultDatatypes.Messaging,
                        "map" to DefaultDatatypes.Map,
                        "custom" to DefaultDatatypes.Custom)[data.getString("datatype")]
                var viewData: Int = 0

                val usesTemplate = data.has("template")
                var template: Int = 0

                if (usesTemplate) {
                    template = serializeTemplate(buffer, data.getJSONObject("template"))
                }

                when (datatype) {
                    DefaultDatatypes.Text -> {
                        val major = buffer.createString(data.getString("significant"))
                        val minor = buffer.createString(data.getString("minor"))

                        val additionalComponents = mutableListOf<Int>()
                        if (data.has("progress")) {
                            ProgressBar.startProgressBar(buffer)
                            ProgressBar.addProgress(buffer, data.getDouble("progress").toFloat())
                            val pgb = ProgressBar.endProgressBar(buffer)

                            Component.startComponent(buffer)
                            Component.addCmpType(buffer, Components.ProgressBar)
                            Component.addCmp(buffer, pgb)
                            additionalComponents.add(Component.endComponent(buffer))
                        }
                        if (data.has("imageUri") && commandType != CommandType.Update) {
                            val img = serializeImageFromTemplate(buffer, data, "imageUri")
                            ImageBackground.startImageBackground(buffer)
                            ImageBackground.addImage(buffer, img!!)
                            val imgb = ImageBackground.endImageBackground(buffer)


                            Component.startComponent(buffer)
                            Component.addCmpType(buffer, Components.ImageBackground)
                            Component.addCmp(buffer, imgb)
                            additionalComponents.add(Component.endComponent(buffer))
                        }
                        var serializedAdditional: Int? = null
                        if (additionalComponents.size > 0) {
                            serializedAdditional = TextData.createAdditionalComponentsVector(buffer, additionalComponents.toIntArray())
                        }
                        TextData.startTextData(buffer)
                        TextData.addMajor(buffer, major)
                        TextData.addMinor(buffer, minor)
                        serializedAdditional?.let {
                            TextData.addAdditionalComponents(buffer, it)
                        }
                        viewData = TextData.endTextData(buffer)

                    }
                    DefaultDatatypes.Messaging -> {
                        //serialize profiles
                        val innerData = data.getJSONObject("data")
                        val profiles = innerData.getJSONArray("profiles")
                        val actions = innerData.getJSONArray("actions")
                        val messages = innerData.getJSONArray("messages")
                        val serializedActions = serializeActions(buffer, actions)

                        val profilesSerialized = mutableListOf<Int>()
                        for (i in (0..profiles.length() - 1)) {
                            val profile = profiles.getJSONObject(i)
                            val name = buffer.createString(profile.getString("name"))
                            var image: Int? = null
                            if (profile.has("avatar") && profile.getString("avatar").length > 1) {
                                image = serializeImage(buffer, Uri.parse(profile.getString("avatar")))
                            }
                            Profile.startProfile(buffer)
                            Profile.addName(buffer, name)
                            image?.let {
                                Profile.addImage(buffer, it)
                            }
                            profilesSerialized.add(Profile.endProfile(buffer))
                        }
                        val messagesSerialized = mutableListOf<Int>()
                        for (i in (0..messages.length() - 1)) {
                            val message = messages.getJSONObject(i)
                            val sender = buffer.createString(message.getString("sender"))
                            val content = buffer.createString(message.getString("content"))
                            var image: Int? = null
                            if (message.has("image") && message.getString("image").length > 1) {
                                image = serializeImage(buffer, Uri.parse(message.getString("image")))
                            }
                            Message.startMessage(buffer)
                            Message.addContent(buffer, content)
                            Message.addSenderName(buffer, sender)
                            image?.let {
                                Message.addImage(buffer, it)
                            }
                            messagesSerialized.add(Message.endMessage(buffer))
                        }
                        val messagesOffset = MessagingData.createMessagesVector(buffer, messagesSerialized.toIntArray())
                        val profilesOffset = MessagingData.createProfilesVector(buffer, profilesSerialized.toIntArray())
                        MessagingData.startMessagingData(buffer)
                        MessagingData.addProfiles(buffer, profilesOffset)
                        MessagingData.addMessages(buffer, messagesOffset)
                        viewData = MessagingData.endMessagingData(buffer)

                    }
                    DefaultDatatypes.List -> {
                        val simpleEntries = data.getJSONArray("simpleEntries")
                        val serializedEntries = mutableListOf<Int>()
                        for (i in (0..simpleEntries.length() - 1)) {
                            val entry = simpleEntries.getJSONObject(i)
                            var icon: Int? = null
                            if (entry.has("icon")) {
                                icon = serializeImage(buffer, Uri.parse(entry.getString("icon")))
                            }
                            var mainAction: Int? = null
                            if (entry.has("mainAction")) {
                                mainAction = serializeAction(buffer, entry.getJSONObject("mainAction"))
                            }
                            var secondaryActions: List<Int>? = null
                            if (entry.has("secondaryActions")) {
                                secondaryActions = serializeActions(buffer, entry.getJSONArray("secondaryActions"))
                            }
                            var lines: Int? = null
                            if (entry.has("lines")) {
                                val l = mutableListOf<Int>()
                                for (childIndex in 0..entry.getJSONArray("lines").length() - 1)
                                    l.add(buffer.createString(entry.getJSONArray("lines").getString(childIndex)))
                                lines = SimpleListEntry.createLinesVector(buffer, l.toIntArray())
                            }
                            SimpleListEntry.startSimpleListEntry(buffer)
                            icon?.let {
                                SimpleListEntry.addIcon(buffer, it)
                            }
                            mainAction?.let {
                                SimpleListEntry.addMainAction(buffer, it)
                            }
                            secondaryActions?.let {
                                SimpleListEntry.addSideActions(buffer, SimpleListEntry.createSideActionsVector(buffer, it.toIntArray()))
                            }
                            lines?.let {
                                SimpleListEntry.addLines(buffer, it)
                            }
                            SimpleListEntry.addLayout(buffer, mapOf(ListItemType.Text to Layout.Text, ListItemType.WithIcon to Layout.WithIcon)[ListItemType.valueOf(entry.getString("layoutType"))]!!)
                            serializedEntries.add(SimpleListEntry.endSimpleListEntry(buffer))
                        }
                        //listData
                        val entriesVector = ListData.createSimpleEntriesVector(buffer, serializedEntries.toIntArray())
                        ListData.startListData(buffer)
                        ListData.addUseCustomTemplate(buffer, false)
                        ListData.addSimpleEntries(buffer, entriesVector)
                        viewData = ListData.endListData(buffer)
                    }
                }
                //TODO: add additional componenents
                if (data.has("background")) {
                    //TODO: serialize image, check the format!
                    //val backgroundImage = this.serializeImageFromTemplate(buffer,data,"image")

                }
                //TODO: add progress bars
                //if (data.)

                val viewName = buffer.createString(data.getString("name"))
                View.startView(buffer)
                //todo: add more
                val datatypesToTypes = mapOf(DefaultDatatypes.List to ViewData.ListData, DefaultDatatypes.Messaging to ViewData.MessagingData, DefaultDatatypes.Text to ViewData.TextData)



                View.addDataType(buffer, datatypesToTypes[datatype]!!)
                View.addDatatype(buffer, datatype!!)
                View.addTemplate(buffer, template)
                View.addName(buffer, viewName)
                View.addId(buffer, id)
                View.addActions(buffer, serializedActionsInBuffer)
                val styles = mapOf("Notification" to DefaultStyle.Notification, "Center" to DefaultStyle.Center, "TextWall" to DefaultStyle.TextWall, "ToBottom" to DefaultStyle.ToBottom, "ToTop" to DefaultStyle.ToTop)
                try {
                    View.addDefaultStyle(buffer, styles[data.getString("style")]!!)
                } catch (e: org.json.JSONException) {

                }
                View.addData(buffer, viewData)
                cmd = View.endView(buffer)
                //If we are updating - pack it into another strucure
                if (commandType == CommandType.Update) {
                    Update.startUpdate(buffer)
                    Update.addView(buffer, cmd)
                    cmd = Update.endUpdate(buffer)
                }
            }
            "handshake" -> {

            }
        }



        Command.startCommand(buffer)
        Command.addId(buffer, id)
        Command.addCommand(buffer, cmd)
        Command.addCommandType(buffer, commandType!!) //cannot be null
        val command = Command.endCommand(buffer)
        Command.finishCommandBuffer(buffer, command)

        return buffer
    }

    private fun serializeActions(buffer: FlatBufferBuilder, actions: JSONArray): List<Int> {
        val serializedActions = mutableListOf<Int>()
        for (i in 0..(actions.length() - 1)) {
            val action = actions.getJSONObject(i)
            serializedActions.add(this.serializeAction(buffer, action))
        }
        return serializedActions
    }

    private fun serializeAction(buffer: FlatBufferBuilder, action: JSONObject): Int {
        val name = buffer.createString(action.getString("name"))
        val callback = buffer.createString(action.getString("callback"))
        var extras: JSONObject? = null
        try {
            extras = action.getJSONObject("extras")
        } catch (e: org.json.JSONException) {
            extras = JSONObject(action.getString("extras"))
        }
        val serializedExtrasList = serializeExtras(buffer, extras!!)
        val serializedExtras = Action.createExtrasVector(buffer, serializedExtrasList.toIntArray())
        //making extras

        Action.startAction(buffer)
        Action.addActionName(buffer, name)
        Action.addCallback(buffer, callback)
        Action.addExtras(buffer, serializedExtras)
        return Action.endAction(buffer)
    }

    private fun serializeExtras(buffer: FlatBufferBuilder, extras: JSONObject): MutableList<Int> {

        data class SerializedOffsets(val key: Int, val value: Int)

        val map = mutableMapOf<String, SerializedOffsets>()
        for (i in extras.keys()) {
            val key = buffer.createString(i)
            val value = buffer.createString(extras.getString(i))
            map.put(i, SerializedOffsets(key, value))
        }
        val extrasSerializedList = mutableListOf<Int>()
        for (i in extras.keys()) {
            Extras.startExtras(buffer)
            Extras.addValue(buffer, map[i]!!.value)
            Extras.addKey(buffer, map[i]!!.key)
            extrasSerializedList.add(Extras.endExtras(buffer))
        }
        return extrasSerializedList

    }

    private fun serializeTemplate(buffer: FlatBufferBuilder, template: JSONObject): Int {
        //Finally, serialize all
        val name = buffer.createString(template.getString("templateName"))
        var dataBindings: MutableList<Int>? = null
        var bindingsVector: Int? = null
        if (template.has("dataBindings")) {
            dataBindings = serializeDataBindings(buffer, template.getJSONObject("dataBindings"))
            bindingsVector = buffer.createVectorOfTables(dataBindings.toIntArray())
        }
        TemplateRoot.startTemplateRoot(buffer)
        bindingsVector?.let {
            TemplateRoot.addDataBindings(buffer, it)
        }
        TemplateRoot.addName(buffer, name)

        return TemplateRoot.endTemplateRoot(buffer)
    }

    private fun serializeImage(buffer: FlatBufferBuilder, path: Uri): Int {
        val image = WatchUtils.convertUriToImage(this.context, path)
        val byteArrayOS = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOS)
        return buffer.createByteVector(byteArrayOS.toByteArray())
    }

    private fun serializeImageFromTemplate(buffer: FlatBufferBuilder, template: JSONObject, key: String): Int? {
        if (template.has(key)) {
            return serializeImage(buffer, Uri.parse(template.getString(key)))
        }
        return null
    }

    private fun serializeTemplateElement(buffer: FlatBufferBuilder, template: JSONObject): Int {
        fun serializeStyles(buffer: FlatBufferBuilder, template: JSONObject): Int? {
            fun serializeBackground(): Int? {
                if (template.has("background")) {
                    Background.startBackground(buffer)
                    val functions = mapOf("backgroundColor" to { -> Background.addBackgroundColor(buffer, this.makeColor(template.getString("backgroundColor"))) },
                            "backgroundImage" to { -> })
                    for (fieldName in functions.keys) {
                        if (template.has(fieldName)) {
                            functions.get(fieldName)!!.invoke()
                        }
                    }
                    return Background.endBackground(buffer)
                }
                return null
            }

            val background = serializeBackground()

            Style.startStyle(buffer)
            //TODO: zrobić to na resztę
            val functions = mapOf(
                    "absoluteWidth" to
                            { -> Style.addAbsoluteWidth(buffer, template.getInt("absoluteWidth").toShort()) },
                    "absoluteHeight" to { -> Style.addAbsoluteHeight(buffer, template.getInt("absoluteHeight").toShort()) },
                    "borderColor" to { -> Style.addBorderColor(buffer, template.getInt("borderColor").toLong()) },
                    "background" to { -> Style.addBackground(buffer, background!!) }, //will never be null if this lambda executes
                    "x" to { -> Style.addX(buffer, template.getInt("x").toShort()) },
                    "y" to { -> Style.addY(buffer, template.getInt("y").toShort()) },
                    "marginTop" to { -> Style.addMarginTop(buffer, template.getInt("marginTop").toShort()) },
                    "marginBottom" to { -> Style.addMarginBottom(buffer, template.getInt("marginBottom").toShort()) },
                    "marginLeft" to { -> Style.addMarginLeft(buffer, template.getInt("marginLeft").toShort()) },
                    "marginRight" to { -> Style.addMarginRight(buffer, template.getInt("marginRight").toShort()) },

                    "width" to { -> Style.addWidth(buffer, template.getDouble("width")) },
                    "height" to { -> Style.addHeight(buffer, template.getDouble("height")) },

                    "offsetX" to { -> Style.addHeight(buffer, template.getDouble("offsetX")) },
                    "offsetY" to { -> Style.addHeight(buffer, template.getDouble("offsetY")) }

            )
            for (fieldName in functions.keys) {
                if (template.has(fieldName)) {
                    functions.get(fieldName)!!.invoke()
                }
            }

            return Style.endStyle(buffer)
        }

        fun serializeAttributes(buffer: FlatBufferBuilder, template: JSONObject, type: Short): Pair<Byte, Int>? {
            when (type) {
                Type.Text -> {
                    val fontFamily = buffer.createString(template.getString("fontFamily"))
                    val fontSize = template.getInt("fontSize").toShort()
                    val text = buffer.createString(template.getString("text"))
                    val color = Integer.parseInt(template.getString("color").replace("#", ""), 16)
                    TextAttributes.startTextAttributes(buffer)
                    TextAttributes.addFontFamily(buffer, fontFamily)
                    TextAttributes.addColor(buffer, color)
                    TextAttributes.addFontSize(buffer, fontSize)
                    TextAttributes.addText(buffer, text)
                    return Pair(Attributes.TextAttributes, TextAttributes.endTextAttributes(buffer))
                }
                Type.HorizontalLayout, Type.VerticalLayout -> {
                    LayoutAttributes.startLayoutAttributes(buffer)
                    LayoutAttributes.addSpacing(buffer, template.getInt("spacing").toShort())
                    return Pair(Attributes.LayoutAttributes, LayoutAttributes.endLayoutAttributes(buffer))
                }
                Type.Card -> {
                    //TODO: serialize layout inside
                    val cardTitle = buffer.createString(template.getString("title"))

                    val image = serializeImageFromTemplate(buffer, template, "image")
                    val avatar = serializeImageFromTemplate(buffer, template, "avatar")

                    var actions: MutableList<Int>? = null
                    if (template.has("actions")) {
                        actions = serializeActions(buffer, template.getJSONArray("actions")).toMutableList()
                    }
                    CardAttributes.startCardAttributes(buffer)
                    CardAttributes.addTitle(buffer, cardTitle)
                    actions?.let {
                        val vector = CardAttributes.createActionsVector(buffer, actions!!.toIntArray())
                        CardAttributes.addActions(buffer, vector)
                    }
                    image?.let {
                        CardAttributes.addImage(buffer, image)
                    }
                    avatar?.let {
                        CardAttributes.addImage(buffer, avatar)
                    }
                    return Pair(Attributes.CardAttributes, CardAttributes.endCardAttributes(buffer))

                }
            }
            return null
        }

        //recursive!!!
        val children = template.getJSONArray("children")
        val childOffsets = mutableListOf<Int>()
        for (childIndex in 0..(children.length() - 1)) {
            val child = children.getJSONObject(childIndex)
            childOffsets.add(this.serializeTemplateElement(buffer, child))

        }
        val serializedChildren = buffer.createVectorOfTables(childOffsets.toIntArray())
        //serialize the object itself now
        //for this single element
        val typeMap = mapOf("horizontallayout" to HorizontalLayout, "item" to Type.Item, "container" to Type.Container, "text" to Text, "label" to Text, "verticallayout" to Type.VerticalLayout, "card" to Type.Card)
        val serializedType = typeMap[template.getString("type").toLowerCase()]!! //this cant be null!!
        //optional
        var hasId = template.has("id")
        var id: Int? = null
        if (hasId) {
            id = buffer.createString(template.getString("id"))
        }
        var action: Int? = null
        if (template.has("action"))
            action = serializeAction(buffer, template.getJSONObject("action"))


        val attributes = serializeAttributes(buffer, template, serializedType.toShort())

        val styles = serializeStyles(buffer, template)!! // never null - might return an empty object though
        Template.startTemplate(buffer)
        attributes?.let {
            //if not null - if there are attributes defined for this type
            val attributesType = attributes.first
            val attributesSerialized = attributes.second
            Template.addAttributes(buffer, attributesSerialized)
            Template.addAttributesType(buffer, attributesType)
        }
        id?.let {
            Template.addId(buffer, id!!)
        }
        Template.addStyle(buffer, styles)
        action?.let {
            Template.addAction(buffer, action!!) //impossible to be null there
        }
        //add the children:
        Template.addChildren(buffer, serializedChildren)



        return Template.endTemplate(buffer)
    }

    private fun serializeDataBindings(buffer: FlatBufferBuilder, bindings: JSONObject): MutableList<Int> {
        data class SerializedOffsets(val key: Int, val value: Int)

        val serializedBindings = mutableListOf<Int>()
        val map = mutableMapOf<Int, List<SerializedOffsets>>()
        val ids = bindings.keys()
        for (id in ids) {
            val serializedId = buffer.createString(id)
            val obj = bindings.getJSONObject("id")
            val serializedPropertyBindings = mutableListOf<Int>()
            for (property in obj.keys()) {

                val value = obj.getString(property)
                val serializedProperty = buffer.createString(property)
                val serializedValue = buffer.createString(value)
                PropertyBinding.startPropertyBinding(buffer)
                PropertyBinding.addPropertyName(buffer, serializedProperty)
                PropertyBinding.addValue(buffer, serializedProperty)
                serializedPropertyBindings.add(PropertyBinding.endPropertyBinding(buffer))
            }

            DataBinding.startDataBinding(buffer)
            DataBinding.addId(buffer, serializedId)
            DataBinding.createBindingsVector(buffer, serializedPropertyBindings.toIntArray())
            serializedBindings.add(DataBinding.endDataBinding(buffer))
        }
        return serializedBindings
    }
}
