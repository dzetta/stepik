package rihilke.stepik

import io.realm.RealmList
import io.realm.RealmObject


class FeedAPI(
    val items: ArrayList<FeedItemAPI>
)

class FeedItemAPI(
    val title: String,
    val link: String,
    //у РИА Новости описание пустое
    val description: String,
    val enclosureAPI: EnclosureAPI,
)

class EnclosureAPI(
    val link: String,
    val type: String
)

open class Feed(
    var items: RealmList<FeedItem> = RealmList<FeedItem>()
) : RealmObject()

open class FeedItem(
    var title: String = "",
    var link: String = "",
    //у РИА Новости описание пустое
    var description: String = "",
    var enclosure: Enclosure = Enclosure("", ""),
) : RealmObject()

open class Enclosure(
    var link: String = "",
    var type: String = "",
) : RealmObject()
