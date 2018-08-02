package io.universalwatch.universalwatchapplication

import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.universalwatch.uwlib.WatchUtils
import org.json.JSONObject

/**
 * Created by emile on 09.04.2018.
 */
fun sendNotification(context:Context, notification:JSONObject){

}
class NotificationSenderModule:NotificationListenerService(){

    override fun onBind(intent: Intent?): IBinder {
        return super.onBind(intent)
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        //TODO: TEST
        val notif = JSONObject("""
            {
                "type":"notification",
                "data":{
                    "test?:"test"
                }
            }
        """.trimIndent())
        Log.d("notifcation","posted")

       // WatchUtils.sendToBluetooth(notif, applicationContext)

    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {

    }
}