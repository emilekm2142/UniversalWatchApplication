package io.universalwatch.universalwatchapplication

import android.net.Uri
import com.universalwatch.uwlib.MessagingMessage
import com.universalwatch.uwlib.MessagingProfile

/**
 * Created by emile on 03.11.2018.
 */
object MessagingAppDataService {
    lateinit var profiles: MutableList<MessagingProfile>
    lateinit var messages: MutableList<MessagingMessage>
    fun make() {
        profiles = mutableListOf()
        messages = mutableListOf()
        profiles.add(MessagingProfile("Emil", Uri.parse("emileAvatar.jpg")))
        profiles.add(MessagingProfile("Piotr", Uri.parse("piotrAvatar.jpg")))
        profiles.add(MessagingProfile("Ania", Uri.parse("aniaAvatar.jpg")))
        messages.add(MessagingMessage(profiles[0].name, "Hej, hej czy to działa?", null))
        messages.add(MessagingMessage(profiles[1].name, "Działa, jak najbardziej", null))
        messages.add(MessagingMessage(profiles[0].name, "Ok. super", null))
        messages.add(MessagingMessage(profiles[1].name, "Myślałeś, że nie zadziała???", null))
        messages.add(MessagingMessage(profiles[0].name, "Nie tylko tak sprawdzam xd", null))
        messages.add(MessagingMessage(profiles[1].name, "Ah ok xd", null))
    }

    fun getRecentMessages(i: Int): MutableList<MessagingMessage> {
        return messages
    }

    fun getRecentConversationMembers(i: Int): MutableList<MessagingProfile> {
        return profiles
    }

    fun getFriends(): MutableList<MessagingProfile> {
        return mutableListOf(profiles[1], profiles[2])
    }
}