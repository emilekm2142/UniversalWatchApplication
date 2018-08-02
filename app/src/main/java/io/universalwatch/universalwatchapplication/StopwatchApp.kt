package io.universalwatch.universalwatchapplication

import android.content.Context
import android.net.Uri
import android.os.Debug
import android.util.Log
import com.universalwatch.uwlib.*
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import org.json.JSONObject

/**
 * Created by emile on 04.04.2018.
 */
object SingletonStopwatch:ApplicationSingleton(){
    var useStyling = false
    var counting = false
    var seconds=0
    var milis=0
    val DELAY = 900

    override fun createApplication(context: Context){
        Log.d("d", "inside")
        val requirements = mutableListOf(Requirements.color)
        SingletonStopwatch.app = Application(context, "Stopwatch", requirements)

        //overriding default!
        SingletonStopwatch.app?.let {
        val startButton = Action({
            //start counting
            counting=true
            launch{
                while (counting) {
                    delay(DELAY)
                    milis += DELAY
                    if (milis >= 1000) {
                        seconds += 1
                        milis %=1000
                    }
                    val tree = JSONObject(
                            """
                                {
                                "significant":"${seconds}",
                                "minor":"${milis}"
                                }
                                """.trimIndent()
                    )
                    SingletonStopwatch.app?.updateView(context, "main", tree)
                }
            }
        }, "Start","")
        val lapButton = Action({}, "lap", "")
        val stopButton = Action({ counting=false}, "Stop","")
        val initialView = TextView("main","00", "00", actions = mutableListOf(startButton, lapButton, stopButton),onBack = {c,s -> app!!.close(c)})
        if (useStyling) {
            initialView.template = JSONObject("""
{
	"datatype": "text",
	"templateName": "main",
	"layout": {
		"width": 1,
		"x": 0,
		"y": 0,
        "background":{"type":"color", "color":"#e74c3c"},
		"content": [

			{
				"type": "rectangle",
				"width": 1,

				"content": [{
					"type": "verticalLayout",
					"width": 1,

					"marginTop": 5,
					"VAlign": "bottom",
					"content": [

						{
							"type": "rectangle",
							"width": 1,




							"content": [{
								"id": "significant",
								"type": "text",
								"HAlign": "center",
								"text": "",
								"fontNumber": 1,
								"fontSize": 13,

								"textAlign": "center"
							}]
						},
						{
							"type": "rectangle",
							"width": 1,


							"content": [{
								"type": "text",

								"id": "minor",
								"HAlign": "center",
								"textAlign": "center",
								"text": "",
								"fontNumber": 1
							}]
						},
						{
							"type": "action",
							"name": "Start",
							"callbackName": "startButtonPress",
							"extras": ""
						}

					]
				}]


			}



		]
	}

}

        """.trimIndent())
        }
            SingletonStopwatch.app?.onOpen={context, jsonObject ->

                SingletonStopwatch.app?.showView(context,initialView)


            }
            SingletonStopwatch.app?.onClose={context, jsonObject ->
                counting=false
                seconds=0
                milis=0
                app ==null
               wasInitialized =false
            }
        }


        }


    }


class StopwatchApp:ApplicationRuntime(){
    override fun makeApplication(context: Context): Application {
        return SingletonStopwatch.getApplication(context)
    }
}