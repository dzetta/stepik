package rihilke.stepik

class Feed(
    val items: ArrayList<FeedItem>

)


class FeedItem(
    val title: String,
    val link: String,
    //у РИА Новости описания нет
    val description:String,
    val enclosure: Enclosure,
)

class Enclosure(
    val link: String,
    val type: String
)

