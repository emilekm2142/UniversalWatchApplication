package io.universalwatch.universalwatchapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.provider.MediaStore
import android.R.attr.data
import android.content.Context
import android.util.Log
import com.universalwatch.uwlib.getByAttribute
import org.json.JSONException
import org.json.JSONObject

import android.content.ContentValues.TAG
import android.os.Environment
import android.widget.Toast
import android.os.Environment.getExternalStorageDirectory
import java.io.*
import java.nio.file.Files.exists
import java.util.*

import android.graphics.drawable.Drawable
import com.universalwatch.uwlib.WatchUtils

import android.graphics.Bitmap.CompressFormat




/**
 * Created by emile on 03.02.2018.
 */
fun getBytesFromBitmap(bitmap: Bitmap): ByteArray {
    val stream = ByteArrayOutputStream()
    bitmap.compress(CompressFormat.JPEG, 70, stream)
    return stream.toByteArray()
}


fun convertAllURIsFromJSONObjectToBase64EncodedImages(context: Context, json:JSONObject){

    val keysToConvert = listOf("icon", "imageUri")
    val changeKeys = hashMapOf<String, String>("icon" to "icon", "imageUri" to "image")
    val queue = mutableListOf<JSONObject>()
    queue.add(json)
    var size = queue.size
    var index = 0
    while (true) {
        var obj = queue[index]

        for (key in obj.keys()) {

            if (keysToConvert.contains(key)) {

                //convert, cancel on error
                if (obj.getString(key) != "") {
                    try {
                        val uri = Uri.fromFile(File(obj.getString(key)))

                        val image = WatchUtils.convertUriToImage(context, uri)
                        val encodedImage = WatchUtils.encodeToBase64(image, Bitmap.CompressFormat.JPEG, 30)

                        obj.put(key, encodedImage)
                        Log.d("k", encodedImage)

                        //val d = obj.toString()
                    } catch (e: Exception) {
                        Log.d("error", "an error occured in convertAllTheURIStoBase64EncodedImagesFromJSONObject xd")
                        Log.d("error", e.toString())
                        Log.d("error", e.stackTrace.toString())
                    }
                }
            }



            try {
                obj.getJSONObject(key)

                queue.add(obj.getJSONObject(key))




            }
            catch (e: JSONException){

            }
            try {
                val arr = obj.getJSONArray(key)
                for (i in 0..arr.length()){
                    try {
                        queue.add(arr.getJSONObject(i))

                    }
                    catch (e: JSONException){

                    }
                }
                queue.add(obj.getJSONObject(key))

            }
            catch (e: JSONException){

            }

        }

        size = queue.size
        index=index+1
        if (index<size){
            Log.d("nope","nope xd ${size}, $index")
        }
        else{
            break
        }
    }
    }
