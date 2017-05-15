package omg.jd.tvmazeapiclient.ws.model

import com.google.gson.annotations.SerializedName

data class WsShow(val id : Long = 0,
                  val url : String?,
                  val name : String?,
                  val type : String?,
                  val language : String?,
                  val genres : List<String>,
                  val status : String?,
                  val runtime : Int = 0,
                  val premiered : String?,
                  val schedule : WsSchedule?,
                  val rating : WsRating?,
                  val weight : Int = 0,
                  val network : WsNetwork?,
                //val webChannel : Any?, // ?
                  val externals : WsExternals?,
                  val image : WsImage?,
                  val summary : String?,
                  val updated : Long = 0,
                  @SerializedName("_links") val links : WsLinks?)


