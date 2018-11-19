package io.universalwatch.universalwatchapplication

import android.content.Context
import android.net.Uri
import com.universalwatch.uwlib.*

object MessagingApp : ApplicationSingleton() {
    lateinit var friendsListView: ListView
    override fun createApplication(context: Context): Application? {
        MessagingAppDataService.make()

        val app = Application(context, "Messaging", listOf(Requirements.unicode), Uri.parse("messagingIcon.jpg"))
        friendsListView = ListView("Friends and conversations", mutableListOf())
        //making list view:
        val conversationCallback = fun(extras: IncomingExtras) {
            val members = MessagingAppDataService.getRecentConversationMembers(Integer.parseInt(extras.getJson().getString("conversationId")))
            val messages = MessagingAppDataService.getRecentMessages(Integer.parseInt(extras.getJson().getString("conversationId")))

            val messagesView = MessagingView(members[1].name, members, messages)
            messagesView.systemCallbacks.onBack = { c, s -> app.showView(context, friendsListView) }
            app.showView(context, messagesView)

        }
        // ListEntry(ListItemType.WithIcon, mutableListOf("Ania", "2 new messages"), Uri.parse("aniaAvatar.jpg"), Action(conversationCallback,"open", """{"conversationId":"${id}"}""" ) )
        //populating list of friends
        val listEntries = mutableListOf<ListEntry>()
        for ((i, friend) in MessagingAppDataService.getFriends().withIndex()) {
            listEntries.add(
                    ListEntry(
                            ListItemType.WithIcon,
                            mutableListOf(friend.name),
                            friend.avatarUri,
                            Action(conversationCallback, "open", """{"conversationId":"${i}"}""")
                    )
            )
        }
        friendsListView.elements.addAll(listEntries)
        app.onOpen = { c, s ->
            app.showView(c, friendsListView)
        }
        return app
    }

    fun displayList(context: Context) {
        getApplication(context).showView(context, friendsListView)
    }
}

class MessagingRuntime : ApplicationRuntime() {
    override fun makeApplication(context: Context): Application {
        return MessagingApp.getApplication(context)
    }
}