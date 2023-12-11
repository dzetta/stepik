package rihilke.stepik

import android.os.Bundle
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //гарантированно отработает при запуске приложения
        super.onCreate(savedInstanceState)
        //здесь где-то сетконтент вызывает эксемельку с экраном. но где.
        //а вот и не эксемелька, а тхеме.кт
        // как экраны делать блять? Где эксемельки блиать?
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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StepikTheme {
        Greeting("Android")
    }
}