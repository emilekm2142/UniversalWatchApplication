package io.universalwatch.universalwatchapplication

import android.net.Uri

/**
 * Created by emile on 03.11.2018.
 */
object MockchatAppDataService {
    data class Message(var sender: String, var image: Uri)

    private var messages: MutableList<Message> = mutableListOf(Message("Jacek xd", Uri.parse("im4.jpg")), Message("Ania", Uri.parse("im5.jpg")), Message("Krzysiu :D", Uri.parse("im6.jpg")))
    fun getMessages(): MutableList<Message> {
        return messages
    }

    fun removeMessageBySender(sender: String) {

        messages.removeAt(messages.indexOf(messages.find({ d -> d.sender == sender })))
    }

}