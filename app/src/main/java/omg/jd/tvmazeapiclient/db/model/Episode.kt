package omg.jd.tvmazeapiclient.db.model

import android.os.Parcel
import android.os.Parcelable
import com.raizlabs.android.dbflow.annotation.*
import omg.jd.tvmazeapiclient.db.dbflow.TvMazeDatabase
import omg.jd.tvmazeapiclient.utils.DateTimeUtils
import org.joda.time.DateTime
import paperparcel.PaperParcel

@PaperParcel
@Table(name = "episodes", database = TvMazeDatabase::class)
data class Episode(@PrimaryKey(autoincrement = false) @Column(name = "id") var id: Long = 0,
                   @Column(name = "url") var url: String?,
                   @Column(name = "name") var name: String?,
                   @Column(name = "season") var season: Int = 0,
                   @Column(name = "number") var number: Int = 0,
                   @Column(name = "airdate") var airdate: String?,
                   @Column(name = "airtime") var airtime: String?,
                   @Column(name = "airstamp") var airstamp: String?,
                   @Column(name = "runtime") var runtime: Int = 0,
                   @Column(name = "mediumImage") var mediumImage: String?,
                   @Column(name = "originalImage") var originalImage: String?,
                   @Column(name = "summary") var summary: String?,
                   @ForeignKey(tableClass = Links::class) var links: Links?,
                   @ForeignKey(saveForeignKeyModel = false, stubbedRelationship = true, onDelete = ForeignKeyAction.CASCADE)
                   var tvShow: TvShow? = null) : Parcelable {

    companion object {
        @JvmField val CREATOR = PaperParcelEpisode.CREATOR
    }

    @delegate:Transient val datetime: DateTime by lazy { DateTimeUtils.getEpisodeDateTime(airstamp) }

    @Deprecated(message = "Do not use this constructor. This is workaround for DBFlow.")
    constructor() : this(0L, "", "", 0, 0, "", "", "", 0, "", "", "", null)

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        PaperParcelEpisode.writeToParcel(this, dest, flags)
    }

}