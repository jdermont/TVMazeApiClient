package omg.jd.tvmazeapiclient.db.model

import com.raizlabs.android.dbflow.annotation.*
import com.raizlabs.android.dbflow.sql.language.OrderBy
import com.raizlabs.android.dbflow.sql.language.SQLite
import com.raizlabs.android.dbflow.structure.BaseModel
import omg.jd.tvmazeapiclient.db.dbflow.TvMazeDatabase
import omg.jd.tvmazeapiclient.db.dbflow.utils.StringList

@Table(name = "tvshows", database = TvMazeDatabase::class)
data class DbFlowTvShow(@PrimaryKey(autoincrement = false) @Column(name = "id") var id: Long = 0,
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
                        @ForeignKey(tableClass = DbFlowNetwork::class, saveForeignKeyModel = true, deleteForeignKeyModel = true)
                        var network: DbFlowNetwork?,
                        @Column(name = "mediumImage") var mediumImage: String?,
                        @Column(name = "originalImage") var originalImage: String?,
                        @Column(name = "summary") var summary: String?,
                        @Column(name = "updated") var updated: Long = 0) : BaseModel() {

    @Deprecated(message = "Do not use this constructor. This is workaround for DBFlow.")
    constructor() : this(0L, "", "", "", "", StringList(), "", 0, "", "", StringList(), 0.0, 0, null, "", "", "", 0)

    // workaround for DBFlow's OneToMany and kotlin data class
    private var _episodes: List<DbFlowEpisode>? = null
    var episodes: List<DbFlowEpisode>?
        @OneToMany(methods = arrayOf(OneToMany.Method.ALL), isVariablePrivate = true, variableName = "episodes")
        get() {
            if (_episodes == null) {
                _episodes = SQLite.select().from(DbFlowEpisode::class.java)
                        .where(DbFlowEpisode_Table.tvShow_id.eq(this.id))
                        .orderBy(DbFlowEpisode_Table.airstamp, true)
                        .queryList()
                _episodes?.forEach { it.tvShow = this }
            }
            return _episodes
        }
        set(episodes) {
            episodes?.forEach { it.tvShow = this }
            _episodes = episodes
        }
}