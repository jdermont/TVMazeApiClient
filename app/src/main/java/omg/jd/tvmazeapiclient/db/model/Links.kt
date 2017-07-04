package omg.jd.tvmazeapiclient.db.model

import android.os.Parcel
import android.os.Parcelable
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel
import omg.jd.tvmazeapiclient.db.dbflow.TvMazeDatabase
import paperparcel.PaperParcel

@PaperParcel
@Table(name = "links", database = TvMazeDatabase::class)
data class Links(@PrimaryKey(autoincrement = true) @Column(name = "id") var id: Long = 0,
                 @Column(name = "self") var self: String?,
                 @Column(name = "previousepisode") var previousepisode: String?,
                 @Column(name = "nextepisode") var nextepisode: String?) : BaseModel(), Parcelable {

    companion object {
        @JvmField val CREATOR = PaperParcelLinks.CREATOR
    }

    @Deprecated(message = "Do not use this constructor. This is workaround for DBFlow.")
    constructor() : this(0L, "", "", "")

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        PaperParcelLinks.writeToParcel(this, dest, flags)
    }

}
