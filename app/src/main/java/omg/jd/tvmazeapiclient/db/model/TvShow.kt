package omg.jd.tvmazeapiclient.db.model

import android.os.Parcel
import android.os.Parcelable
import paperparcel.PaperParcel

@PaperParcel
data class TvShow(val id: Long = 0,
                  val url: String?,
                  val name: String?,
                  val type: String?,
                  val language: String?,
                  val genres: List<String>,
                  val status: String?,
                  val runtime: Int = 0,
                  val premiered: String?,
                  val scheduleTime: String?,
                  val scheduleDays: List<String>,
                  val rating: Double = 0.0,
                  val weight: Int = 0,
                  val network: Network?,
                  val mediumImage: String?,
                  val originalImage: String?,
                  val summary: String?,
                  val updated: Long = 0,
                  val links: Links?,
                  var episodes: List<Episode>) : Parcelable {

    companion object {
        @JvmField val CREATOR = PaperParcelTvShow.CREATOR
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        PaperParcelTvShow.writeToParcel(this, dest, flags)
    }

}