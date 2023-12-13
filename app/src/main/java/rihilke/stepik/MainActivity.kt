package rihilke.stepik

import android.content.Intent
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
import rihilke.stepik.ui.theme.StepikTheme

/*
* Все активити отдельны. Они ничего друг о друге не знают.
* Есть только один способ передавать данные между экранами
* ИНТЕНТ
* */
class MainActivity : ComponentActivity() {
    //var val разница повторить
    //переменная класса
    lateinit var vText:TextView

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
        vText.setOnClickListener {
            Log.v("click_tag", "нажата кнопа")
            /* мы не можем влиять на активити напрямую
            * сообщаем системе, что хотим активити через интент
            * ВСЕ ДЕЛАЕТСЯ ЧЕРЕЗ ИНТЕНТ
            * что такое :: ???
            * */
            val i = Intent(this, SecondActivity::class.java)
            //почему tag1
            //по именам данных tag1 ищутся данные в интентах
            i.putExtra("tag1", vText.text)
            // меняем startActivity(i) на
            startActivityForResult(i,0)
        }
    }

    // коды реквестов и результов - по ним можно различать кто что вернул
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
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
        super.onDestroy()
    }
}
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