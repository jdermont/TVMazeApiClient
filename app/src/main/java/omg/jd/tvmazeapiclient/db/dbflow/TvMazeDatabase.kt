package omg.jd.tvmazeapiclient.db.dbflow

import com.raizlabs.android.dbflow.annotation.Database

@Database(name = TvMazeDatabase.NAME,
        version = TvMazeDatabase.VERSION)
object TvMazeDatabase {
    const val NAME: String = "tvmazedb"
    const val VERSION: Int = 1
}
