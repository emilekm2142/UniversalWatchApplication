package io.universalwatch.universalwatchapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.universalwatch.uwlib.Requirements
import com.universalwatch.uwlib.WatchInfo
import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by emile on 23.02.2018.
 */
/*
A helper extension function to convert the List to a JsonArray
 */
fun <T> List<T>?.toJsonWATCH(): JSONArray {
    val r = JSONArray()
    this?.let {

        for (o in this) {
            r.put(o.toString())
        }
    }
    return  r
}
/**
*
*This object contains a single watchInfo field. We need it because even when WatchInfoServer gets destroyed, this thing is still alive.
*
* @property watchInfo contains a cached watchInfo
*
 */
object Cache{
var watchInfo:WatchInfo?=null
}
/**
* This receiver receives the requests from other applications, requesting watch parameters saved in @Cache object. We serialize this object to JSON and then send it back
* to the receiver. The action of this intent is determined from the sourcePackage field in extras. It is set in the function that sends the request.
* TODO: You should not assume it is correct as it is now!
 */
class WatchInfoServer:BroadcastReceiver(){
    override fun onReceive(p0: Context?, p1: Intent?) {
        val src = p1!!.getStringExtra("sourcePackage")
        //TODO: get info from singleton there
        var serialized =""
        Cache.watchInfo?.let {
           serialized= """
                    {
                    "modelName":"${Cache.watchInfo?.modelName}",
                    "manufacturer":"${Cache.watchInfo?.manufacturer}",
                    "requirements":${Cache.watchInfo?.availableRequirements.toJsonWATCH()},
                    "extraModelInfo":"${Cache.watchInfo?.extraModelInfo}",
                    "firmwareVersion":"${Cache.watchInfo?.firmwareVersion}",
                    "bufferKbSize":${Cache.watchInfo?.bufferKbSize},
                    "dataTransferMode":"${Cache.watchInfo?.dataTransferMode}",
                    "screenUpdates":${Cache.watchInfo?.screenUpdates}
                    }
                """.trimIndent()
        }

        val i = Intent(src+".UniversalWatch.TempRecv")
        i.putExtra("data",serialized)
        i.setFlags(Intent.FLAG_RECEIVER_FOREGROUND)
        p0!!.sendBroadcast(i)
    }
}