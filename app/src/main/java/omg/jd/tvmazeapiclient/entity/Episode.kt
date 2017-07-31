package omg.jd.tvmazeapiclient.entity

import android.os.Parcel
import android.os.Parcelable
import omg.jd.tvmazeapiclient.utils.DateTimeUtils
import org.joda.time.DateTime
import paperparcel.PaperParcel

@PaperParcel
data class Episode(val id: Long = 0,
                   val url: String?,
                   val name: String?,
                   val season: Int = 0,
                   val number: Int = 0,
                   val airdate: String?,
                   val airtime: String?,
                   val airstamp: String?,
                   val runtime: Int = 0,
                   val mediumImage: String?,
                   val originalImage: String?,
                   val summary: String?) : Parcelable, Comparable<Episode> {

    companion object {
        @JvmField val CREATOR = PaperParcelEpisode.CREATOR
    }

    @delegate:Transient val datetime: DateTime by lazy { DateTimeUtils.getEpisodeDateTime(airstamp) }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        PaperParcelEpisode.writeToParcel(this, dest, flags)
    }

    override fun compareTo(other: Episode): Int {
        return datetime.compareTo(other.datetime)
    }

}