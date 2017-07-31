package omg.jd.tvmazeapiclient.ws.model

data class WsEpisode(val id: Long = 0,
                     val url: String?,
                     val name: String?,
                     val season: Int = 0,
                     val number: Int = 0,
                     val airdate: String?,
                     val airtime: String?,
                     val airstamp: String?,
                     val runtime: Int = 0,
                     val image: WsImage?,
                     val summary: String?)
