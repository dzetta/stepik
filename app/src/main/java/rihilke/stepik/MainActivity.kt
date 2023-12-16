package rihilke.stepik

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
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

class MainActivity : ComponentActivity() {
    lateinit var vList: LinearLayout
    var request: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.v("tag", "onCreate запустился")
        vList = findViewById(R.id.act1_list)
        // url rss, пропущенный через онлайн джейсонатор
        val url =
            "https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fria.ru%2Fexport%2Frss2%2Farchive%2Findex.xml"
        val o = createRequest(url)
            .map { Gson().fromJson(it, Feed::class.java) }
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

        request = o.subscribe({
            showLinearLayout(it.items)
        }, {
            Log.e("test", "", it)
        })
    }

    fun showLinearLayout(feedList: ArrayList<FeedItem>) {
        val inflater = layoutInflater
        for (f in feedList) {
            val view = inflater.inflate(R.layout.list_item, vList, false)
            val vTitle = view.findViewById<TextView>(R.id.item_title)
            vTitle.text = f.title
            vList.addView(view)
        }
    }

    override fun onStart() {
        super.onStart()
    }


    override fun onResume() {
        super.onResume()
    }


    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}


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