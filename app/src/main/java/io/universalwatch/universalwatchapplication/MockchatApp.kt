package io.universalwatch.universalwatchapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.universalwatch.uwlib.*
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import org.json.JSONObject

/**
 * Created by emile on 23.04.2018.
 */
object Mockchat{
    var wasInitialized=false
    var app: Application?=null

    var mockTimerStart =15;

    var count = false
    fun createApplication(context: Context):Application{
        if (!Mockchat.wasInitialized) {
            val requirements = mutableListOf(Requirements.color, Requirements.images, Requirements.templates)
            Mockchat.app = Application(context, "Mockchat", requirements)

            //overriding default!
            Mockchat.app?.let {
                //make app here
                Mockchat.wasInitialized = true

                //an initial view with mocks
                val mocks = mutableListOf("John", "Ann", "Anton")
                val images = mutableListOf(
                        Uri.parse("/assets/im1.jpg"),
                        Uri.parse("/assets/im2.jpg"),
                        Uri.parse("/assets/im3.jpg")
                )

                val mocksList = ListView("mockslist", simpleElements = mocks  )

                mocksList.onClick = {context, id, extras ->
                    val selectedMock = mocks[id]
                    val image = images[id]
                    val actions = mutableListOf<Action>()

                    val imageView = TextView(
                            "mock",
                            mockTimerStart.toString(),
                            "",
                            imageUri = image,
                            onBack = {c,s->Mockchat.app?.showView(c,mocksList); count=false},
                            progress=1.0,
                            style = TextView.Companion.Layouts.TO_BOTTOM)
                    Mockchat.app?.showView(context, imageView)
                    launch {
                        count=true
                        delay(1000)

                        var mockTimer:Double = mockTimerStart.toDouble();
                        while (mockTimer > 0 && count){
                            delay(200)
                            mockTimer-=0.2
                            Mockchat.app?.updateView(context,"mock", JSONObject("""{"significant":${mockTimer.toString()}, "progress":${mockTimer/mockTimerStart.toDouble()}}"""))
                        }
                        count=false
                        Mockchat.app?.showView(context, mocksList)
                    }

                    mocks.removeAt(id)
                    images.removeAt(id)

                }
                Mockchat.app?.initialView = mocksList
                mocksList.systemCallbacks.onBack = {c,s->
                    count=false
                    Mockchat.app?.close(c)
                }


            }
        }
        return app!!

    }
}
class MockchatApp:ApplicationRuntime(){
    override fun makeApplication(context: Context): Application {
        return Mockchat.createApplication(context)
    }
}