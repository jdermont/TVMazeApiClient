package omg.jd.tvmazeapiclient.db.model

import android.os.Parcel
import android.os.Parcelable
import paperparcel.PaperParcel

@PaperParcel
data class Links(val self: String?,
                   val previousepisode: String?,
                   val nextepisode: String?) : Parcelable {

    companion object {
        @JvmField val CREATOR = PaperParcelLinks.CREATOR
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        PaperParcelLinks.writeToParcel(this, dest, flags)
    }

}
