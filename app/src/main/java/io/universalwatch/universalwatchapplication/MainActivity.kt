package io.universalwatch.universalwatchapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.UserManager
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import android.os.StrictMode
import android.util.Log
import android.view.View
import android.widget.Switch
import com.universalwatch.uwlib.Watch
import com.universalwatch.uwlib.WatchUtils
import com.universalwatch.uwlib.*
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import org.json.JSONObject
import android.widget.CompoundButton




class MainActivity : AppCompatActivity() {
    lateinit var switch: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        //starting bluetooth
        switch = findViewById(R.id.useStyling)
        switch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            SingletonStopwatch.useStyling=switch.isChecked
            SingletonStopwatch.forceRecreation(applicationContext)

        })
        val intent = Intent(applicationContext, BluetoothModule::class.java)
        startService(intent)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()

        StrictMode.setThreadPolicy(policy)
        //due to bt service being async


        launch {
            delay(2000)
          //  SingletonStopwatch.getApplication(applicationContext)!!.install(applicationContext)
           // delay(100)
           // Mockchat.createApplication(applicationContext)!!.install(applicationContext)
           // delay(100)
           // NotesApplication.getApplication(applicationContext)!!.install(applicationContext)
          //  delay(100)
           // Memes.getApplication(applicationContext)!!.install(applicationContext)
           // delay(100)
            //StylesApplications.getApplication(applicationContext)!!.install(applicationContext)
            WykopClientApp.getApplication(applicationContext)
        }


        val tinyDb = TinyDB(applicationContext)
        fab.setOnClickListener { view ->
            //SingletonNotesApp.getApplication(applicationContext).install(applicationContext)
            //add note
            var titles:ArrayList<String>?=null
            var content:ArrayList<String>?=null
            try {
                titles = tinyDb.getListString("notes_titles")
                content = tinyDb.getListString("notes_contents")
            }
            catch(e:Exception){
                titles= ArrayList()
                content= ArrayList()
            }
            titles!!.add("New note")
            content!!.add("xd")

            tinyDb.putListString("notes_titles", titles!!)
            tinyDb.putListString("notes_contents", content!!)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
