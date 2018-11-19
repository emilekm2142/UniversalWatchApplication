package io.universalwatch.universalwatchapplication

import AllWatchSerialize.*
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.universalwatch.uwlib.BroadcastTypes
import com.universalwatch.uwlib.Requirements
import com.universalwatch.uwlib.WatchInfo
import com.universalwatch.uwlib.WatchUtils
import kotlinx.coroutines.experimental.launch
import org.json.JSONObject
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.nio.ByteBuffer

/**
 * Created by emile on 24.05.2018.
 */
open class SenderReceiver:Service(){

    protected val DEBUG_MODE_BT_RECV_ACTION = "BT_DEBUG"
    companion object {
        @JvmStatic
        val mapOfLastSentViews = hashMapOf<String, JSONObject>()


        @JvmStatic
        protected val INTERNAL_SENDERRECEIVER_RECEIVER_ACTION = ".SenderReceiver"
        @JvmStatic
        fun sendToSender(data:JSONObject,context: Context){
            var i = Intent(context.applicationContext.packageName + SenderReceiver.INTERNAL_SENDERRECEIVER_RECEIVER_ACTION)
            val inString  = data.toString()
            i.putExtra("data",inString)
            i.`package` = context.applicationContext.packageName
            i.flags = Intent.FLAG_RECEIVER_FOREGROUND
            context.sendBroadcast(i)
        }
        @JvmStatic
        private fun handToReceiverModule(context: Context, type: BroadcastTypes, data:String){
            val i = Intent("UniversalWatch.ReceiveData")
            i.`package` = context.packageName
            i.flags = Intent.FLAG_RECEIVER_FOREGROUND
            i.putExtra("type", type.toString())
            i.putExtra("data", data)
            context.sendBroadcast(i)
        }
    }
    open fun initialize(){

    }
    override fun onCreate() {

        initialize()

        val recvFilter = IntentFilter(applicationContext.packageName + INTERNAL_SENDERRECEIVER_RECEIVER_ACTION)
        val recv = SenderReceiverInternalReceiver()
        registerReceiver(recv,recvFilter)
        super.onCreate()
    }
    override fun onBind(p0: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        /*IN FOREGROUND*/
        val notificationIntent = Intent(this, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0)

        val notification = NotificationCompat.Builder(this)
                .setContentTitle("Watch service")
                .setContentText("do not dismiss")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent).build()

        startForeground(2137, notification)
        return Service.START_STICKY
    }

    fun onReceive(message: ByteArray) {
        onMessage(message)
    }

    fun onReceive(message: String) {
        onMessage(message)
    }

    open fun sendRawData(data: ByteArray) {
        Log.d("length", data.size.toString())
        Log.d("length", data.size.toString())

    }

    open fun sendRawData(string: String) {

    }
    protected fun sendSignal(type:BroadcastTypes, packag:String, j:JSONObject){
        var cache = Cache.watchInfo
        //when cache is null, then connection was not established yet. For debug, lets make a cache here.
        if (cache == null) {
            cache = WatchInfo("xd", "xd", mutableListOf(Requirements.templates), "sdasda", "asfasff", false, 1111111, "flatbuffers", true)
        }


        Log.d("sent",j.toString())
        launch {

            if (type==BroadcastTypes.RESOURCE_REQUEST){
                val uri = Uri.parse(j.getString("uri"))
                try {
                    val image = WatchUtils.convertUriToImage(applicationContext, uri)
                    sendRawData(getBytesFromBitmap(image))
                }
                catch (e:FileNotFoundException){
                    Log.d("e", e.message)
                }

            }


            if (j.getString("type") == "updateView"){
                if (cache!!.screenUpdates) {

                }else{
                    //modify the view here!
                    val lastView = mapOfLastSentViews[packag]
                    //now update
                    //TODO screenUpdates
                }
            }
            //calculation heavy!
            if (cache != null && type != BroadcastTypes.RESOURCE_REQUEST) {
                Log.d("as", "asss")

                when (cache!!.dataTransferMode) {
                    "json" -> {
                        convertAllURIsFromJSONObjectToBase64EncodedImages(applicationContext, j)
                    //To avoid escaping Base64, yes it has to be unescaped twice
                    val stringToSend = Unescaper.unescapeString(Unescaper.unescapeString(j.toString()))
                    sendRawData(stringToSend)
                }
                    "flatbuffers" -> {
                        val Translator = FlatBuilder(applicationContext)
                        val buffer = Translator.translate(j)
                        val bb: ByteBuffer = ByteBuffer.wrap(buffer.sizedByteArray())
                        sendRawData(buffer.sizedByteArray())
                        //temp
                        fun writeToFile(data: ByteArray, fileName: String) {

                            val out = FileOutputStream(File.createTempFile("axd", "xd"))
                            out.write(data)
                            out.close()
                        }

                        val serialized = buffer.dataBuffer().array()

                        Log.d("a", serialized.size.toString())

                        val new = Command.getRootAsCommand(bb)
                        Log.d("d", new.id().packageName())
                        Log.d("asas", "asdas")
                    }
                }
            }
            else{
                //handshake not exchanged yet. This message is a handshake request. Always send in JSON
                val Translator = FlatBuilder(applicationContext)
                val buffer = Translator.translate(j)
                sendRawData(buffer.dataBuffer().array())
            }
        }


    }
    private fun _onMessage(JSONmsg:JSONObject){
        var type = JSONmsg.getString("type")
        var data = JSONmsg.getJSONObject("data")

        when(type){

            "dataRequestResponse"->{

            }
        /* Should not all the signals be sent to the target app and resolved there?*/
            "dataRequest"->{ //request from watch to the phone
                val requestType = data.getString("type")
                when(requestType){
                    "applicationInitialScreen"->{
                        handToReceiverModule(context = applicationContext, type = BroadcastTypes.APPLICATION_OPEN, data=JSONmsg.toString())
                    }
                }

            }


            else->{
                handToReceiverModule(context = applicationContext, type = BroadcastTypes.toBroadcastTypeFromString(type)!!, data=JSONmsg.toString())
            }

        }
    }
    fun onMessage( message:String){
        //received from watch
        Log.d("a",message)
        var JSONmsg = JSONObject(message)
        _onMessage(JSONmsg)

    }
    fun onMessage( message:ByteArray){
        val r = mutableMapOf<String, String>()
        fun deserializeExtras(msg: Action): MutableMap<String, String> {
            Log.d("d", msg.extrasLength().toString())
            for (i in (0..msg.extrasLength() - 1)) {
                val extra = msg.extras(i)
                r.put(extra.key(), extra.value())
            }
            return r
        }

        Log.d("asdasfd", "asdas")
        val d = Command.getRootAsCommand(ByteBuffer.wrap(message))
        Log.d("as", d.id().friendlyName())
        Log.d("as", d.id().packageName())
        val map = mapOf(CommandType.Back to BroadcastTypes.SYSTEM_ACTION, CommandType.Action to BroadcastTypes.ACTION, CommandType.Handshake to BroadcastTypes.HANDSHAKE, CommandType.Open to BroadcastTypes.APPLICATION_OPEN)
        val broadcastType = map[d.commandType()]
        var toHandle = JSONObject()
        when (broadcastType) {
            BroadcastTypes.ACTION -> {
                val actionData = d.command(Action()) as Action
                toHandle = JSONObject("""
                     {
                     "type":"action",
                     "targetPackage":"${d.id().packageName()}",
                     "friendlyName":"${d.id().friendlyName()}",
                     "data":{
                        "callbackName":"${actionData.callback()}",
                        "extras":${deserializeExtras(actionData).toString().replace("=", ":")}
                     }
                     }
                """.trimIndent())
            }
            BroadcastTypes.APPLICATION_OPEN -> {
                val openData = d.command(Open()) as Open
                toHandle = JSONObject("""
                    {"type":"initialViewRequest",
                    "targetPackage":"${d.id().packageName()}",
                     "friendlyName":"${d.id().friendlyName()}",
                     "data":{
                        "package":"${d.id().packageName()}",
                     "friendlyName":"${d.id().friendlyName()}"
                     }
                     }
                """.trimIndent())
            }
        //TODO: not only back
            BroadcastTypes.SYSTEM_ACTION -> {
                val backData = d.command(Back()) as Back
                toHandle = JSONObject("""
                    {"type":"systemAction",
                    "targetPackage":"${d.id().packageName()}",
                     "friendlyName":"${d.id().friendlyName()}",
                     "data":{
                        "actionName":"back"

                     }
                     }
                """.trimIndent())
            }
        }


        handToReceiverModule(applicationContext, broadcastType!!, toHandle.toString())
    }


    inner class SenderReceiverInternalReceiver: BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
            //hand it to the function
            //TODO: check if works

            val stringName = JSONObject(p1!!.getStringExtra("data")).getString("type")
            val type = BroadcastTypes.toBroadcastTypeFromString(stringName)!!

            Log.d("iu", "yutuy")


            sendSignal(type, p1.`package`, JSONObject(p1.getStringExtra("data")))

        }
    }
    inner class DEBUG_BLUETOOTH_RECEIVER: BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {

            onMessage(p1!!.getStringExtra("message"))
        }
    }
}