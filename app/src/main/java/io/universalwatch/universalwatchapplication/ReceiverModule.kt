package io.universalwatch.universalwatchapplication

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.universalwatch.uwlib.BroadcastTypes
import com.universalwatch.uwlib.Requirements
import com.universalwatch.uwlib.WatchInfo
import io.universalwatch.universalwatchapplication.Cache.watchInfo
import org.json.JSONObject

/**
 * Created by emile on 30.11.2017.
 */


//FROM PHONE TO WATCH
//receives data from the dendJSONObject from library
class SenderModule:BroadcastReceiver(){
    private fun combine(innerBody:JSONObject, extras: Bundle):JSONObject{
        return JSONObject()
    }

    override fun onReceive(p0: Context?, p1: Intent?) {
        val type = BroadcastTypes.valueOf(p1!!.getStringExtra("type"))
        val sourcePackage = p1!!.`package`
        val sourceApp = p1!!.getStringExtra("sourceApp") //without _ instead of space
        val data = JSONObject(p1.getStringExtra("data"))
        var shouldSendToBt=true
        run {
            when (type) {
                BroadcastTypes.VIEW_SHOW -> {

                }
                BroadcastTypes.APPLICATION_INSTALL -> {
                    //co xd? NANI
                    //shouldSendToBt=false

                }
                BroadcastTypes.APPLICATION_CLOSE -> {

                }
                BroadcastTypes.APPLICATION_OPEN -> {

                }
                BroadcastTypes.CUSTOM_NOTIFICATION_SEND -> {

                }
                BroadcastTypes.SYSTEM_NOTIFICATION_SEND -> {

                }
                BroadcastTypes.VIEW_DELETE -> {

                }
                BroadcastTypes.VIEW_REPLACE -> {

                }
                BroadcastTypes.VIEW_UPDATE -> {

                }



            }
        }
        if(shouldSendToBt) {
            //preparing data
            val outer = JSONObject()
            val fromPackage = p1!!.`package`

            val watchSignalType = BroadcastTypes.broadcastTypesToWatchDatatypes[type]
            Log.d("a", watchSignalType)
            outer.put("type", watchSignalType)
            outer.put("targetPackage", fromPackage)
            outer.put("friendlyName", sourceApp)
            outer.put("data", data)
            SenderReceiver.sendToSender(outer, p0!!)

        }
    }
}

//FROM WATCH TO PHONE
class ReceiverModule:BroadcastReceiver(){
    private fun combine(innerBody:JSONObject, extras: Bundle):JSONObject{
        return JSONObject()
    }
    override fun onReceive(p0: Context?, p1: Intent?) {
        val type = BroadcastTypes.valueOf(p1!!.getStringExtra("type"))
        val data = JSONObject(p1.getStringExtra("data"))
        when(type){
            BroadcastTypes.APPLICATION_CLOSE->{
                val innerData=data.getJSONObject("data")
                val targetApplication = innerData.getString("package")
                val friendlyName = innerData.getString("friendlyName")
                val intent = Intent(targetApplication+".UniversalWatch."+friendlyName.replace(' ','_'))
                intent.putExtra("data", data.getJSONObject("data").toString() )
                intent.putExtra("type",type.toString())
                intent.setPackage(targetApplication)
                intent.setFlags(Intent.FLAG_RECEIVER_FOREGROUND)
                p0!!.applicationContext.sendBroadcast(intent)
            }
            BroadcastTypes.HANDSHAKE->{
                val innerData=data.getJSONObject("data")
                val reqs = mutableListOf<Requirements>()
                val array = innerData.getJSONArray("requirements")
                //from json to enum
                for (i in (0..array.length()-1)) {
                    reqs.add(Requirements.valueOf(array.getString(i)))
                }
                Cache.watchInfo= WatchInfo(
                        innerData.getString("modelName"),
                        innerData.getString("manufacturer"),
                        reqs.toList(),
                        innerData.getString("extraModelInfo"),
                        innerData.getString("firmwareVersion"),
                        try{innerData.getBoolean("isBufferLimited")}catch (e:Exception){false},
                        try{innerData.getInt("bufferKbSize")}catch (e:Exception){0}
                        )


            }
            BroadcastTypes.APPLICATION_OPEN->{
                val innerData=data.getJSONObject("data")
                val targetApplication = innerData.getString("package")
                val friendlyName = innerData.getString("friendlyName")
                val intent = Intent(targetApplication+".UniversalWatch."+friendlyName.replace(' ','_'))
                Log.d("tera", friendlyName)
                intent.putExtra("data", data.getJSONObject("data").toString() )
                intent.putExtra("type",type.toString())
                intent.setPackage(targetApplication)
                intent.setFlags(Intent.FLAG_RECEIVER_FOREGROUND)
                p0!!.applicationContext.sendBroadcast(intent)
            }
            BroadcastTypes.VIEW_DELETE->{

            }
            BroadcastTypes.LIST_VIEW_CLICK->{
                val targetApplication = data.getString("targetPackage")
                val intent = Intent(targetApplication+".UniversalWatch."+data.getString("friendlyName").replace(' ','_'))
                intent.putExtra("data", data.getJSONObject("data").toString() )
                intent.putExtra("type",type.toString())
                intent.setPackage(targetApplication)
                intent.setFlags(Intent.FLAG_RECEIVER_FOREGROUND)
                p0!!.applicationContext.sendBroadcast(intent)
            }

            BroadcastTypes.ACTION->{

                //TODO: test, czy sypie się gdy apka nie jest włączona? Nie powinno!
                val targetApplication = data.getString("targetPackage")
                val intent = Intent(targetApplication+".UniversalWatch."+data.getString("friendlyName").replace(' ','_'))
                intent.putExtra("data", data.getJSONObject("data").toString() )
                intent.putExtra("type",type.toString())
                intent.setPackage(targetApplication)
                intent.setFlags(Intent.FLAG_RECEIVER_FOREGROUND)
                p0!!.applicationContext.sendBroadcast(intent)
            }
            BroadcastTypes.WATCH_RESPONSE->{
                val targetApplication = data.getString("targetPackage")
                val intent = Intent(targetApplication+".UniversalWatch.Return."+data.getString("friendlyName").replace(' ','_'))
                intent.putExtra("data", data.getJSONObject("data").toString() )
                intent.putExtra("type",type.toString())
                intent.setPackage(targetApplication)
                intent.setFlags(Intent.FLAG_RECEIVER_FOREGROUND)
                p0!!.applicationContext.sendBroadcast(intent)
            }
            else->{
                val targetApplication = data.getString("targetPackage")
                val intent = Intent(targetApplication+".UniversalWatch."+data.getString("friendlyName").replace(' ','_'))
                intent.putExtra("data", data.getJSONObject("data").toString() )
                intent.putExtra("type",type.toString())
                intent.setPackage(targetApplication)
                intent.setFlags(Intent.FLAG_RECEIVER_FOREGROUND)
                p0!!.applicationContext.sendBroadcast(intent)

            }
        }
    }
}