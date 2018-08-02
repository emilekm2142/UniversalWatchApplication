package io.universalwatch.universalwatchapplication

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.util.Log

import com.macroyau.blue2serial.BluetoothDeviceListDialog
import com.universalwatch.uwlib.Application
import com.universalwatch.uwlib.BroadcastTypes
import kotlinx.coroutines.experimental.launch
import org.json.JSONObject
import org.msgpack.core.MessagePack
import com.macroyau.blue2serial.BluetoothSerial
import com.macroyau.blue2serial.BluetoothSerialListener

import android.support.v4.app.ActivityCompat.invalidateOptionsMenu
import android.support.v4.app.ActivityCompat.invalidateOptionsMenu
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.bluetooth.BluetoothAdapter
import android.content.DialogInterface

import android.bluetooth.BluetoothDevice
import android.app.PendingIntent
import android.support.v4.app.NotificationCompat

private val DEBUG_MODE = false
private val USE_BT = false


class BluetoothModule:SenderReceiver(), BluetoothSerialListener{

    private var bluetoothSerial: BluetoothSerial? = null

    override fun initialize() {
        if (USE_BT) {
            bluetoothSerial = BluetoothSerial(applicationContext, this)
            //my code here


            if (DEBUG_MODE) {
                val debugRecvFilter = IntentFilter(DEBUG_MODE_BT_RECV_ACTION);
                val debugRecv = DEBUG_BLUETOOTH_RECEIVER()
                registerReceiver(debugRecv, debugRecvFilter)
            }

            bluetoothSerial!!.setup()
            bluetoothSerial!!.connect("00:1A:7D:DA:71:13")
            bluetoothSerial!!.start()
        }
    }






    fun requestHandshake(){

       sendSignal(BroadcastTypes.HANDSHAKE, packageName, JSONObject("""{"type":"handshake", "data":{}}"""))
    }

    override fun sendRawData(string: String) {
        if (USE_BT)
        bluetoothSerial!!.write(string, true)
    }

    override fun sendRawData(data: ByteArray) {
        if (USE_BT)
        bluetoothSerial!!.write(data)
    }



    fun onBluetoothDeviceSelected(device: BluetoothDevice) {
        // Connect to the selected remote Bluetooth device
        if (USE_BT)
        bluetoothSerial!!.connect(device)
    }
    /*implementation*/

    override fun onBluetoothSerialRead(message: String) {
        // Print the incoming message on the terminal screen
        onReceive(message)
    }

    override fun onConnectingBluetoothDevice() {

    }

    override fun onBluetoothDeviceConnected(name: String, address: String) {
        requestHandshake()
    }

    override fun onBluetoothDisabled() {

    }

    override fun onBluetoothDeviceDisconnected() {
        //Log.d("dis", "dscnt")
        //bluetoothSerial!!.connect("00:1B:10:00:2A:EC")
    }

    override fun onBluetoothNotSupported() {

    }

    override fun onBluetoothSerialWrite(message: String) {
        // Print the outgoing message on the terminal screen

    }


}
