package io.universalwatch.universalwatchapplication

import android.content.Context
import android.net.Uri
import com.universalwatch.uwlib.Application
import com.universalwatch.uwlib.ApplicationRuntime
import com.universalwatch.uwlib.ApplicationSingleton

/**
 * Created by emile on 08.06.2018.
 * wój klucz aplikacji to udV9l8jF8c, a sekret to K2fZgrKRJa. Zapisz te dane, żeby użyć ich w swojej aplikacji.


 */
object WykopClientApp:ApplicationSingleton() {

    override fun createApplication(context: Context): Application {

        val a = Application(context, "vipok.ru", listOf(), Uri.parse("im1.jpg"))

        return a
    }


}


class WykopClient:ApplicationRuntime(){
    override fun makeApplication(context: Context): Application {
        return WykopClientApp.getApplication(context)
    }
}