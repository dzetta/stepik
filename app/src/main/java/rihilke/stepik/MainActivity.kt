package rihilke.stepik

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.RealmList

class MainActivity : ComponentActivity() {
    lateinit var vRecView: RecyclerView
    var request: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.v("tag", "onCreate запустился")
        vRecView = findViewById(R.id.act1_recView)
        val url_rambler =
            "https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fnews.rambler.ru%2Frss%2Fworld%2F"
        val o = createRequest(url_rambler)
            .map { Gson().fromJson(it, FeedAPI::class.java) }
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        request = o.subscribe({
            val feed = Feed(
                it.items.mapTo(
                    RealmList<FeedItem>()
                ) { feed ->
                    FeedItem(
                        feed.title,
                        feed.link,
                        feed.description,
                        Enclosure(
                            feed.enclosureAPI.link,
                            feed.enclosureAPI.type,
                        )
                    )
                }
            )
            Realm.getDefaultInstance().executeTransaction { realm ->
                val oldList = realm.where(Feed::class.java).findAll()
                if (oldList.size > 0)
                    for (item in oldList)
                        item.deleteFromRealm()
                realm.copyToRealm(feed)
                showRecView()
            }
        }, {
            Log.e("test", "", it)
            showRecView()
        })
    }

    fun showRecView() {
        Realm.getDefaultInstance().executeTransaction { realm ->
            val feed = realm.where(Feed::class.java).findAll()
            if (feed.size > 0){
                vRecView.adapter = RecAdapter(feed[0]!!.items)
                vRecView.layoutManager = LinearLayoutManager(this)
            }
        }

    }

    class RecAdapter(val items: RealmList<FeedItem>) : RecyclerView.Adapter<RecHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecHolder {
            val inflater = LayoutInflater.from(parent!!.context)
            val view = inflater.inflate(R.layout.list_item, parent, false)
            return RecHolder(view)
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(holder: RecHolder, position: Int) {
            val item = items[position]!!
            holder?.bind(item)
        }
    }

    class RecHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: FeedItem) {
            val vTitle = itemView.findViewById<TextView>(R.id.item_title)
            val vDesc = itemView.findViewById<TextView>(R.id.item_desc)
            val vThumb = itemView.findViewById<ImageView>(R.id.item_thumb)
            vTitle.text = item.title
            vDesc.text = item.description
            Picasso.with(vThumb.context).load(item.enclosure?.link).into(vThumb)
            itemView.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(item.link)
                vThumb.context.startActivity(i)
            }
        }
    }

    override fun onDestroy() {
        //закрываем все запросы, ничего не остается жрать память
        request?.dispose()
        super.onDestroy()
    }
}

