package omg.jd.tvmazeapiclient.db.model

import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel
import omg.jd.tvmazeapiclient.db.dbflow.TvMazeDatabase

@Table(name = "networks", database = TvMazeDatabase::class)
data class DbFlowNetwork(@PrimaryKey(autoincrement = true) @Column(name = "_id") var _id: Long = 0,
                         @Column(name = "id") var id: Long = 0,
                         @Column(name = "name") var name: String?,
                         @Column(name = "countryName") var countryName: String?,
                         @Column(name = "countryCode") var countryCode: String?,
                         @Column(name = "countryTimezone") var countryTimezone: String?) : BaseModel() {

    @Deprecated(message = "Do not use this constructor. This is workaround for DBFlow.")
    constructor() : this(0L, 0L, "", "", "", "")
}