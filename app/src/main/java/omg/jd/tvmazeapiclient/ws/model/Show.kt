package omg.jd.tvmazeapiclient.ws.model

import com.google.gson.annotations.SerializedName

data class Show(val id : Long = 0,
                val url : String?,
                val name : String?,
                val type : String?,
                val language : String?,
                var genres : List<String> = ArrayList(),
                var status : String?,
                val runtime : Int = 0,
                val premiered : String?,
                val schedule : Schedule?,
                val rating : Rating?,
                val weight : Int = 0,
                val network : Network?,
                //val webChannel : Any? = null,
                val externals : Externals?,
                val image : Image?,
                val summary : String?,
                val updated : Long = 0,
                @SerializedName("_links") val links : Links?)


