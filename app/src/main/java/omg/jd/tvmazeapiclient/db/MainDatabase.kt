package omg.jd.tvmazeapiclient.db

import com.raizlabs.android.dbflow.sql.language.SQLite
import io.reactivex.Observable
import omg.jd.tvmazeapiclient.db.dbflow.utils.deleteInTransaction
import omg.jd.tvmazeapiclient.db.dbflow.utils.saveInTransation
import omg.jd.tvmazeapiclient.db.model.DbFlowTvShow
import omg.jd.tvmazeapiclient.db.model.convertToTvShowDbFlow
import omg.jd.tvmazeapiclient.entity.TvShow
import omg.jd.tvmazeapiclient.entity.convertToTvShowEntity

object MainDatabase {
    enum class TvShowInDB {
        NOT_IN_DB, IN_DB;
        companion object {
            fun valueOf(inDb: Boolean): TvShowInDB = if (inDb) IN_DB else NOT_IN_DB
        }
    }

    var dbChanged: Boolean = false

    fun saveTvShow(tvShow: TvShow): Observable<TvShowInDB> {
        return Observable.fromCallable {
            val tvShowDbFlow = tvShow.convertToTvShowDbFlow()
            tvShowDbFlow.saveInTransation()
            dbChanged = true
            TvShowInDB.IN_DB
        }
    }

    fun deleteTvShow(tvShow: TvShow): Observable<TvShowInDB> {
        return Observable.fromCallable {
            val tvShowDbFlow = tvShow.convertToTvShowDbFlow()
            tvShowDbFlow.deleteInTransaction()
            dbChanged = true
            TvShowInDB.NOT_IN_DB
        }
    }

    fun loadShowList(): Observable<List<TvShow>> {
        return Observable.fromCallable {
            SQLite.select().from(DbFlowTvShow::class.java)
                    .queryList()
        }
                .map { it.map { it.convertToTvShowEntity() } }
    }
}