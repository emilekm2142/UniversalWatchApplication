package io.universalwatch.universalwatchapplication

import android.content.Context
import android.content.res.AssetManager
import android.net.Uri
import com.universalwatch.uwlib.*

/**
 * Created by emile on 10.05.2018.
 */
object Memes:ApplicationSingleton(){
    val memesDatabase = mutableListOf(
            Uri.parse("im1.jpg"),
            Uri.parse("im2.jpg"),
            Uri.parse("im3.jpg")
    )
    override fun createApplication(context: Context) {







        val requirements = mutableListOf(Requirements.images, Requirements.color)
        app = Application(context, "Memes", requirements)
        val initialMeme = getMemeScreen(context, 0)
        app!!.initialView=initialMeme
        app!!.onOpen={context, jsonObject -> app!!.showView(context, initialMeme) }
    }
    fun getMemeScreen(context:Context, id:Int):TextView{
        val actions = mutableListOf(
                Action({}, "love",  id.toString()),
                Action({extras->
                    val current_id = Integer.parseInt(extras.getString())
                    var next_id=current_id+1
                    if (next_id > memesDatabase.size - 1){
                        next_id=0
                    }
                    val newMemeScreen = getMemeScreen(context, next_id)
                    app!!.showView(context, newMemeScreen)
                }, "next",  id.toString())
        )
        val memeView = TextView("meme", "", "",actions, imageUri = memesDatabase[id] , onBack = {context, s -> app!!.close(context); })
        return memeView
    }
}

class MemesApp:ApplicationRuntime(){
    override fun makeApplication(context: Context): Application {
        return Memes.getApplication(context);
    }
}