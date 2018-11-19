package io.universalwatch.universalwatchapplication

import android.bluetooth.BluetoothDevice
import android.content.IntentFilter
import com.macroyau.blue2serial.BluetoothSerial
import com.macroyau.blue2serial.BluetoothSerialListener

private val DEBUG_MODE = false
private val USE_BT = true


class BluetoothModule:SenderReceiver(), BluetoothSerialListener{

    private var bluetoothSerial: BluetoothSerial? = null

    override fun initialize() {
        if (USE_BT) {
            bluetoothSerial = BluetoothSerial(applicationContext, this)
            //my code here


            if (DEBUG_MODE) {
                val debugRecvFilter = IntentFilter(DEBUG_MODE_BT_RECV_ACTION)
                val debugRecv = DEBUG_BLUETOOTH_RECEIVER()
                registerReceiver(debugRecv, debugRecvFilter)
            }

            bluetoothSerial!!.setup()
            bluetoothSerial!!.connect("00:1A:7D:DA:71:13")
            bluetoothSerial!!.start()
        }
    }






    fun requestHandshake(){

        // sendSignal(BroadcastTypes.HANDSHAKE, packageName, JSONObject("""{"type":"handshake", "data":{}}"""))
    }

    override fun sendRawData(string: String) {
        if (USE_BT)
        bluetoothSerial!!.write(string, true)
    }

    override fun sendRawData(data: ByteArray) {
        if (USE_BT) {
            bluetoothSerial!!.write(data.size.toString(), true)
            bluetoothSerial!!.write(data)

        }
    }



    fun onBluetoothDeviceSelected(device: BluetoothDevice) {
        // Connect to the selected remote Bluetooth device
        if (USE_BT)
        bluetoothSerial!!.connect(device)
    }
    /*implementation*/

    override fun onBluetoothSerialRead(message: String) {
        // Print the incoming message on the terminal screen
        onReceive(message.toByteArray())
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
