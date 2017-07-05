package omg.jd.tvmazeapiclient.db.model

import com.raizlabs.android.dbflow.annotation.*
import omg.jd.tvmazeapiclient.db.dbflow.TvMazeDatabase

@Table(name = "episodes", database = TvMazeDatabase::class)
data class DbFlowEpisode(@PrimaryKey(autoincrement = false) @Column(name = "id") var id: Long = 0,
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
                         @ForeignKey(tableClass = DbFlowLinks::class, saveForeignKeyModel = true, deleteForeignKeyModel = true)
                   var links: DbFlowLinks?,
                         @ForeignKey(saveForeignKeyModel = false, stubbedRelationship = true, onDelete = ForeignKeyAction.CASCADE)
                   var tvShow: DbFlowTvShow? = null) {

    @Deprecated(message = "Do not use this constructor. This is workaround for DBFlow.")
    constructor() : this(0L, "", "", 0, 0, "", "", "", 0, "", "", "", null)

}