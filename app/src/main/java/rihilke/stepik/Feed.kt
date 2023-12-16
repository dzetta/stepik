package rihilke.stepik

class Feed(
    val items: ArrayList<FeedItem>

)


class FeedItem(
    val title: String,
    val link: String,
    val enclosure: Enclosure,
)

class Enclosure(
    val link: String,
    val type: String
)

