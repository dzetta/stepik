package rihilke.stepik

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.zipWith
import io.reactivex.schedulers.Schedulers
import org.jetbrains.annotations.Async.Schedule
import rihilke.stepik.ui.theme.StepikTheme
import java.lang.RuntimeException
import java.net.HttpURLConnection
import java.net.URL

/*
* Все активити отдельны. Они ничего друг о друге не знают.
* Есть только один способ передавать данные между экранами
* ИНТЕНТ
* */
/*
* Главный поток - UI поток.
* Если он висит больше 5 секунд, система предлагает убить приложение.
* Система не дает исполнять сетевые запросы в юай-потоке.
* */
@Suppress("UNREACHABLE_CODE")
class MainActivity : ComponentActivity() {
    //var val разница повторить
    //переменная класса
    lateinit var vText:TextView
    //что это диспосабле
    var request:Disposable?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        /* .v сообщения
        * .e эрроры
        * */
        Log.v("tag", "onCreate запустился" )

        //гарантированно отработает при запуске приложения
        super.onCreate(savedInstanceState)
        //здесь где-то сетконтент вызывает эксемельку с экраном. но где.
        //а вот и не эксемелька, а тхеме.кт
        // как экраны делать блять? Где эксемельки блиать?
        /*
        setContent {
            StepikTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }

         */
        setContentView(R.layout.activity_main)
        //спец Дженерик???? функция
        vText=findViewById<TextView>(R.id.btn_1_text)
        //меняем цвет. не работает. я хз
        //vText.setTextColor(0xFFFF00.toInt())
        //обработчик нажатия
//        vText.setOnClickListener {
            Log.v("click_tag", "нажата кнопа")
            /* мы не можем влиять на активити напрямую
            * сообщаем системе, что хотим активити через интент
            * ВСЕ ДЕЛАЕТСЯ ЧЕРЕЗ ИНТЕНТ
            * что такое :: ???
            * */
//            val i = Intent(this, SecondActivity::class.java)
            //почему tag1
            //по именам данных tag1 ищутся данные в интентах
//            i.putExtra("tag1", vText.text)
            // меняем startActivity(i) на
 //           @Suppress("DEPRECATION")
 //           startActivityForResult(i,0)
//        }

        /* Тред для сетевого запроса
        * удобно, эффективно
        * нельзя делать много таких потоков.
        * тоже слишком плохо, удаляем.
        * */
        /*
        val t=object:Thread(){
            override fun run() {
                //TODO сетевой запрос
                // передача в юи поток
                this@MainActivity.runOnUiThread {

                }
            }
        }
        t.start()
         */ //конец тоже слишком плохо
        /*Спец. инструмент для потоков, который уже депрекатед.
        * много жрет, может делать утечки памяти, когда пользователь уже закрыл приложение
        * поэтому вынесем его в отдельный класс
        object:AsyncTask<String, Int, String>(){
            override fun doInBackground(vararg params: String?): String {
                return ""
            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
            }

        }.execute()
//AT(this).execute()
        *
         */
        /*Реактивное. из io.reactivex
        * здесь можно делать flatMap - последовательно несколько действий
        * и zipWith - действия параллельно
        * и map - обработка полученного
        * */
        // url rss, пропущенный через джейсонатор
        // для интернета нужно запросить у оси разрешение на интернет в манифесте
        val url = "https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fria.ru%2Fexport%2Frss2%2Farchive%2Findex.xml"
        val o = createRequest(url)
            //.flatMap { Observable.create<String>{} }
            //.zipWith(Observable.create<String>{})
            .map{ Gson().fromJson(it, Feed::class.java) }
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        //1 - пойдет в онНекст, 2 - на случай эррора
        request = o.subscribe({
            for(item in it.items) {
                Log.w("test", "title: ${item.title}")
            }
        },{
            // здесь будут все ошибки из o
            Log.e("test", "", it)
        })

    } //конец онкреате

    // коды реквестов и результов - по ним можно различать кто что вернул
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        @Suppress("DEPRECATION")
        super.onActivityResult(requestCode, resultCode, data)
        if(data!=null){
            val str = data.getStringExtra("tag2")
            vText.text = str
        }
    }

    /*
    * вызывается, когда приложение загрузило стартовый экран
    * всё можно +- спокойно пихать в онрезюме.
    * */
    override fun onStart() {
        super.onStart()
    }

    /*
    * Когда экран стал активен, когда приложение действительно начало работать.
    * Разница между онстарт и онрезюме есть, но нам пока не надо.
    * */
    override fun onResume() {
        super.onResume()
    }

    /*
    * Приложение, например, свернуто.
    * После него приложение вполне может быть убито системой.
    * */
    override fun onPause() {
        super.onPause()
    }
    /*
    * Симметричен онСтарт. Не используется, потому что приложение могут убить в онПауз.
    * */
    override fun onStop() {
        super.onStop()
    }
    /*
    * Еще есть онРеСтарт, но он +- бесполезен.*/
    /*
    * онДестрой вызывается, когда "Экземпляр класса окончательно убивается системой и не будет
    * возобновлен."
    * Тут можно удалять закэшенное.*/
    override fun onDestroy() {
        //закрываем все запросы, ничего не остается жрать память
        request?.dispose()
        super.onDestroy()
    }
}

//выносим асинхрТаск в отдельный класс. он всё равно депрекатед.
// всё это слишком плохо. удаляем.
/*
class AT(val act:MainActivity):AsyncTask<String, Int, String>(){
    override fun doInBackground(vararg params: String?): String {
        return ""
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
    }

}
 */ //конец слишком плохо, удаляем.

/* От созданного по умолчанию.
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
/* как-то неизвестно пока превьюшка работает, но очень интересно*/
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StepikTheme {
        Greeting("Android")
    }
}

 конец от созданного по умолчанию. */

/* структура джсона
"items": [
{
"title": "Ученые в США научились выявлять риск появления мыслей о суициде"
"pubDate": "2023-12-16 13:06:00"
"link": "https://ria.ru/20231216/ssha-1916209978.html"
"guid": "https://ria.ru/20231216/ssha-1916209978.html"
"author": ""
"thumbnail": ""
"description": ""
"content": ""
"enclosure": {
}
"categories": [
"Лента новостей"
]
}

 */