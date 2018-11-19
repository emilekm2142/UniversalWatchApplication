package io.universalwatch.universalwatchapplication

import android.content.Context
import android.net.Uri
import com.universalwatch.uwlib.*
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

object MockchatApp : ApplicationSingleton() {
    lateinit var friendsListView: ListView
    var shouldCount = true
    lateinit var showCallback: (IncomingExtras) -> Unit
    override fun createApplication(context: Context): Application? {
        MessagingAppDataService.make()

        val app = Application(context, "Mockchat", listOf(Requirements.unicode, Requirements.images, Requirements.color), Uri.parse("mockchatLogo.jpg"))
        friendsListView = ListView("Messages from friends", mutableListOf())
        //making list view:
        showCallback = fun(extras: IncomingExtras) {
            val message = MockchatAppDataService.getMessages()[Integer.parseInt(extras.extras.getString("id"))]

            val imageView = TextView("Image", "", "")
            imageView.imageUri = message.image
            imageView.progress = 1.0
            imageView.systemCallbacks.onBack = { c, s -> app.showView(context, friendsListView); shouldCount = false }
            app.showView(context, imageView)

            launch {
                imageView.progress?.let {
                    while (imageView.progress!! > 0 && shouldCount) {
                        delay(100)
                        imageView.progress = imageView.progress!! - 0.01
                        app.updateView(context, imageView)
                    }
                    MockchatAppDataService.removeMessageBySender(message.sender)
                    friendsListView.elements = fillFriendsList(showCallback)
                    app.showView(context, friendsListView)

                }
            }


        }
        // ListEntry(ListItemType.WithIcon, mutableListOf("Ania", "2 new messages"), Uri.parse("aniaAvatar.jpg"), Action(conversationCallback,"open", """{"conversationId":"${id}"}""" ) )
        //populating list of friends

        friendsListView.elements = fillFriendsList(showCallback)
        app.onOpen = { c, s ->
            app.showView(c, friendsListView)
        }
        return app
    }

    fun displayList(context: Context) {
        getApplication(context).showView(context, friendsListView)
    }

    fun fillFriendsList(callback: (IncomingExtras) -> Unit): MutableList<ListEntry> {
        val listEntries = mutableListOf<ListEntry>()
        for ((i, friend) in MockchatAppDataService.getMessages().withIndex()) {
            listEntries.add(
                    ListEntry(
                            ListItemType.Text,
                            mutableListOf(friend.sender),
                            null,
                            Action(callback, "open", """{"id":"${i}"}""")
                    )
            )
        }
        return listEntries
    }
}

class MockchatRuntime : ApplicationRuntime() {
    override fun makeApplication(context: Context): Application {
        return MockchatApp.getApplication(context)
    }
}