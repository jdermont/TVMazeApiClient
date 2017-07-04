package omg.jd.tvmazeapiclient.db.model

import android.os.Parcel
import android.os.Parcelable
import com.raizlabs.android.dbflow.annotation.*
import com.raizlabs.android.dbflow.structure.BaseModel
import omg.jd.tvmazeapiclient.db.dbflow.TvMazeDatabase
import omg.jd.tvmazeapiclient.db.dbflow.utils.StringList
import paperparcel.PaperParcel

@PaperParcel
@Table(name = "tvshows", database = TvMazeDatabase::class)
data class TvShow(@PrimaryKey(autoincrement = false) @Column(name = "id") var id: Long = 0,
                  @Column(name = "url") var url: String?,
                  @Column(name = "name") var name: String?,
                  @Column(name = "type") var type: String?,
                  @Column(name = "language") var language: String?,
                  @Column(name = "genres") var genres: StringList,
                  @Column(name = "status") var status: String?,
                  @Column(name = "runtime") var runtime: Int = 0,
                  @Column(name = "premiered") var premiered: String?,
                  @Column(name = "scheduleTime") var scheduleTime: String?,
                  @Column(name = "scheduleDays") var scheduleDays: StringList,
                  @Column(name = "rating") var rating: Double = 0.0,
                  @Column(name = "weight") var weight: Int = 0,
                  @ForeignKey(tableClass = Network::class) var network: Network?,
                  @Column(name = "mediumImage") var mediumImage: String?,
                  @Column(name = "originalImage") var originalImage: String?,
                  @Column(name = "summary") var summary: String?,
                  @Column(name = "updated") var updated: Long = 0,
                  @ForeignKey(tableClass = Links::class) var links: Links?,
                  var episodes: List<Episode>) : BaseModel(), Parcelable {

    companion object {
        @JvmField val CREATOR = PaperParcelTvShow.CREATOR
    }

    @Deprecated(message = "Do not use this constructor. This is workaround for DBFlow.")
    constructor() : this(0L, "", "", "", "", StringList(), "", 0, "", "", StringList(), 0.0, 0, null, "", "", "", 0, null, listOf())

    @OneToMany(methods = arrayOf(OneToMany.Method.ALL), isVariablePrivate = true, variableName = "tracks")
    fun getEps(): List<Episode>? {
//        if (_eps?.isEmpty() ?: true) {
//            val condition = OperatorGroup.nonGroupingClause()
//                    .and(DbFlowTrack_Table.playlist_streamId.eq(this.streamId))
//            _eps = ContentUtils.queryList(OpeDatabase.DbFlowTrackProvider.CONTENT_URI, DbFlowTrack::class.java, condition, null)
//                    .sortedBy { it.endTimestamp }
//        }
//        return _eps
        TODO("")
    }

    fun setEps(dbEps: List<Episode>?) {
        dbEps?.forEach { it.tvShow = this }
        //_tracks = dbTracks
        TODO("")
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        PaperParcelTvShow.writeToParcel(this, dest, flags)
    }

}