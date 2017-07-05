package omg.jd.tvmazeapiclient.db.model

import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel
import omg.jd.tvmazeapiclient.db.dbflow.TvMazeDatabase

@Table(name = "links", database = TvMazeDatabase::class)
data class DbFlowLinks(@PrimaryKey(autoincrement = true) @Column(name = "id") var id: Long = 0,
                       @Column(name = "self") var self: String?,
                       @Column(name = "previousepisode") var previousepisode: String?,
                       @Column(name = "nextepisode") var nextepisode: String?) : BaseModel() {

    @Deprecated(message = "Do not use this constructor. This is workaround for DBFlow.")
    constructor() : this(0L, "", "", "")

}
