package io.universalwatch.universalwatchapplication

import android.content.Context
import android.net.Uri
import com.universalwatch.uwlib.*
import org.json.JSONObject

object StylesApplications:ApplicationSingleton(){
    override fun createApplication(context: Context): Application {
        val app = Application(context, "Styles example", mutableListOf(Requirements.templates), Uri.parse("stylesIcon.jpg"))
        val styledText = TextView("styled", "this", "is styled", mutableListOf(), template = JSONObject("""

        {
        "datatype": "text",
        "templateName": "stylingDemo",
        "layout": {
            "width":1,
            "x": 0,
            "y": 0,
            "content":[
            {
            "type":"rectangle",
            "VAlign":"center",
            "width":0.9,
            "HAlign":"center",
            "color":"#2ecc71",
            "content":[
                {
                "type":"verticalLayout",
                "content":[
                    {
                    "HAlign":"center",
                    "type":"text",
                    "id":"significant",
                    "color":"#ecf0f1"
                    },
                    { "type":"text",
                    "HAlign":"center",
                    "id":"minor",
                    "color":"#ecf0f1"
                    }
                ]
                }
            ]
            }
           ]
        }
    }


        """.trimIndent()), onBack = { context, s -> app.close(context) })
        app.onOpen = { context, jsonObject -> app.showView(context, styledText) }
        return app
    }
}
class StylesRuntime:ApplicationRuntime(){
    override fun makeApplication(context: Context): Application {
        return StylesApplications.getApplication(context)
    }
}