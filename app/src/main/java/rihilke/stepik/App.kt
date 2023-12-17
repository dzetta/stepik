package rihilke.stepik

import android.app.Application
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.types.TypedRealmObject
import kotlin.reflect.KClass


class App : Application() {
    override fun onCreate() {
        //при создании (?) приложения, до загрузки экранов
        super.onCreate()
        val config = RealmConfiguration.create(
            schema = setOf(
                Feed::class,
                FeedItem::class,
                Enclosure::class,
            )
        )
        val realm: io.realm.kotlin.Realm = io.realm.kotlin.Realm.open(config)
    }
}