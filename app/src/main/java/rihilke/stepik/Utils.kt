package rihilke.stepik

import io.reactivex.Observable
import java.lang.RuntimeException
import java.net.HttpURLConnection
import java.net.URL

fun createRequest(url:String) = Observable.create<String> {
        //net
        //it.onNext("test")
        /* сниппет * */
        val urlConnection = URL(url).openConnection() as HttpURLConnection
        try {
            urlConnection.connect()
            //проверяем, что пришел 200ый
            if (urlConnection.responseCode != HttpURLConnection.HTTP_OK)
                it.onError(RuntimeException(urlConnection.responseMessage))
            else {
                //bufferedReader и readText из библиотеки котлин, хорошие, годные
                val str = urlConnection.inputStream.bufferedReader().readText()
                // Передаем дальше в обработку
                it.onNext(str)
            }
        } finally {
            //обязательно нужно закрыть
            urlConnection.disconnect()
        }
        /*конец сниппета*/
    }