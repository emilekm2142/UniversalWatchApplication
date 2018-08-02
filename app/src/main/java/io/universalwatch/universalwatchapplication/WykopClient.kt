package io.universalwatch.universalwatchapplication

import android.content.Context
import android.net.Uri
import android.os.Debug
import android.util.Log
import com.universalwatch.uwlib.Application
import com.universalwatch.uwlib.ApplicationRuntime
import com.universalwatch.uwlib.ApplicationSingleton
import khttp.responses.Response
import java.security.MessageDigest

/**
 * Created by emile on 08.06.2018.
 * wój klucz aplikacji to udV9l8jF8c, a sekret to K2fZgrKRJa. Zapisz te dane, żeby użyć ich w swojej aplikacji.


 */
val APIKEY = "udV9l8jF8c"
val SEKRET = "K2fZgrKRJa"
class Wykop(){
    fun login(u:String){
        val d =WykopApiHelpers.sendRequest("user/login", hashMapOf("accountkey" to u)).text
        Debug.waitForDebugger()
    }
}
object WykopApiHelpers{
    fun mapToWykopArguments(map: HashMap<String, String>, addAppKey: Boolean = false): String {

        if (addAppKey) {
            map["appkey"] = APIKEY
        }
        var m2 = map.toMap()
        val o = m2.toList()
        m2 = o.sortedBy({
            it.first
        }).toMap()
        var url = ""
        for (entry in m2) {
            val key = entry.key
            url = """${url},${key},${entry.value}"""
        }
        if (url.startsWith(",")){
            url = url.substring(1,url.length)
        }
        map.remove("appkey")
        return url
    }
    fun mapToValuesList(map: HashMap<String, String>):String{
        var m2 = map.toMap()
        val o = m2.toList()
        m2 = o.sortedBy({
            it.first
        }).toMap()

        var url = ""
        for (entry in m2) {
            val key = entry.key
            url = """${url},${entry.value}"""
        }
        if (url.startsWith(",")){
            url = url.substring(1,url.length)
        }


        return url
    }
    fun md5(a: String): String {
        val md = MessageDigest.getInstance("MD5")
        val digested = md.digest(a.toByteArray())
        return digested.joinToString("") {
            String.format("%02x", it)
        }
    }

    fun getHeaders(request: String): HashMap<String, String> {
        return hashMapOf("apisign" to md5(request))
    }

    fun sendRequest(endpoint: String, postParameters: HashMap<String, String>): Response {
        var url = "http://a.wykop.pl/" + endpoint + "/appkey,"+ APIKEY
        val toSign = SEKRET+url+ mapToValuesList(postParameters)
        val header = getHeaders(toSign)
        return khttp.get(
                url = url,
                headers = header,
                params = postParameters
        )
    }

    fun login(user: String, pswd: String): Response {
        return sendRequest("user/login", hashMapOf("login" to user, "password" to pswd))
    }
}
object WykopClientApp:ApplicationSingleton() {

    override fun createApplication(context: Context) {
       val w = Wykop()
        w.login("oEejlFrkKpDtTM5POW4f")
        app = Application(context, "vipok.ru", listOf(), Uri.EMPTY)
    }


}


class WykopClient:ApplicationRuntime(){
    override fun makeApplication(context: Context): Application {
        return WykopClientApp.getApplication(context)
    }
}