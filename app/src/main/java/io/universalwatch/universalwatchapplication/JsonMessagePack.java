package io.universalwatch.universalwatchapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by emile on 24.05.2018.


public class JsonMessagePack {
    public static MessageBufferPacker convert(Context context, JSONObject data) throws IOException, JSONException {
        MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();
        packJObject(context, packer, data);
        return packer;
    }

    private static void packJObject(Context context, MessageBufferPacker packer, JSONObject data) throws IOException, JSONException {
        packer.packMapHeader(data.length());
        Iterator<String> keys = data.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            if  (key.equals("icon") || key.equals("imageUri")){
                //TODO that is an image
                Uri uri = Uri.fromFile(new File(data.getString(key)));
                Bitmap image = WatchDataPreProcessorKt.convertUriToImage(context, uri);
                ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 30, byteArrayOS);
                packer.packArrayHeader(byteArrayOS.toByteArray().length);
                for (Byte byt:byteArrayOS.toByteArray()) {
                    packer.packByte(byt);
                }
            }
            packer.packString(key); // pack the key
            Object value = data.get(key);
            if (value instanceof JSONArray) {
                packJArray(context, packer, (JSONArray) value);
            } else if (value instanceof JSONObject) {
                packJObject(context, packer, (JSONObject) value);
            } else {
                packPrimitive(context, packer, value);
            }
        }
    }

    private static void packJArray(Context context,MessageBufferPacker packer, JSONArray data) throws IOException, JSONException {
        packer.packArrayHeader(data.length());
        for (int i = 0; i < data.length(); i++) {
            Object value = data.get(i);
            if (value instanceof JSONObject) {
                packJObject(context, packer, (JSONObject) value);
            } else if (value instanceof JSONArray) {
                packJArray(context, packer, (JSONArray) value);
            } else {
                packPrimitive(context, packer, value);
            }
        }
    }

    private static void packPrimitive(Context context, MessageBufferPacker packer, Object value) throws IOException {
        if (value instanceof String) {
            packer.packString((String) value);
        } else if (value instanceof Integer) {
            packer.packInt((Integer) value);
        } else if (value instanceof Boolean) {
            packer.packBoolean((boolean) value);
        } else if (value instanceof Double) {
            packer.packDouble((double) value);
        } else if (value instanceof Long) {
            packer.packLong((long) value);
        } else {
            throw new IOException("Invalid packing value of type " + value.getClass().getName());
        }
    }
}
 */