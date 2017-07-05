package omg.jd.tvmazeapiclient.entity

import android.os.Parcel
import android.os.Parcelable
import paperparcel.PaperParcel

@PaperParcel
data class Network(val id: Long = 0,
                   val name: String?,
                   val countryName: String?,
                   val countryCode: String?,
                   val countryTimezone: String?) : Parcelable {

    companion object {
        @JvmField val CREATOR = PaperParcelNetwork.CREATOR
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        PaperParcelNetwork.writeToParcel(this, dest, flags)
    }

}