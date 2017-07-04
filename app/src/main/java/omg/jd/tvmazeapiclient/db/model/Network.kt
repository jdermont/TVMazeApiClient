package omg.jd.tvmazeapiclient.db.model

import android.os.Parcel
import android.os.Parcelable
import com.raizlabs.android.dbflow.annotation.*
import com.raizlabs.android.dbflow.structure.BaseModel
import omg.jd.tvmazeapiclient.db.dbflow.TvMazeDatabase
import paperparcel.PaperParcel

@PaperParcel
@Table(name = "networks", database = TvMazeDatabase::class)
data class Network(@PrimaryKey(autoincrement = false) @Column(name = "id") var id: Long = 0,
                   @Column(name = "name") var name: String?,
                   @Column(name = "countryName") var countryName: String?,
                   @Column(name = "countryCode") var countryCode: String?,
                   @Column(name = "countryTimezone") var countryTimezone: String?) : BaseModel(), Parcelable {

    companion object {
        @JvmField val CREATOR = PaperParcelNetwork.CREATOR
    }

    @Deprecated(message = "Do not use this constructor. This is workaround for DBFlow.")
    constructor() : this(0L, "", "", "", "")

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        PaperParcelNetwork.writeToParcel(this, dest, flags)
    }

}