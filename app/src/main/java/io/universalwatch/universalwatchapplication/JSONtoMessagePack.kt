package io.universalwatch.universalwatchapplication

import org.json.JSONException
import org.json.JSONObject
import org.msgpack.core.MessagePack
import org.msgpack.core.MessageBufferPacker



/**
 * Created by emile on 20.02.2018.








fun JSONtoMessagePack(j:JSONObject){
    fun recursive(j:JSONObject, m:MessageBufferPacker, isNewObj:Boolean=false, newObjName:String=""){
        for (key in j.keys()){

            if (isNewObj){

            }
            //try to get JSON
            try {
               val a=  j.getJSONObject(key)
                recursive(a,m,true,key)
            }
            catch (e:Exception){

            }
            //try to get JSON array
            try {
                val arr = j.getJSONArray(key)
                for (i in 0..arr.length()){
                    try {


                    }
                    catch (e: JSONException){

                    }
                }


            }
            catch (e: JSONException){

            }
        }
    }
    val packer = MessagePack.newDefaultBufferPacker()
    recursive(j,packer)




}
 */