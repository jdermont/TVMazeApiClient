package omg.jd.tvmazeapiclient.db

import com.raizlabs.android.dbflow.sql.language.SQLite
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import omg.jd.tvmazeapiclient.db.dbflow.utils.deleteInTransaction
import omg.jd.tvmazeapiclient.db.dbflow.utils.saveInTransation
import omg.jd.tvmazeapiclient.db.model.*
import omg.jd.tvmazeapiclient.entity.Episode
import omg.jd.tvmazeapiclient.entity.TvShow
import omg.jd.tvmazeapiclient.entity.convertToEpisodeEntity
import omg.jd.tvmazeapiclient.entity.convertToTvShowEntity


object MainDatabase {
    enum class TvShowInDB {
        NOT_IN_DB, IN_DB;
        companion object {
            fun valueOf(inDb: Boolean): TvShowInDB = if (inDb) IN_DB else NOT_IN_DB
        }
    }
    const val SUBJECT: Int = 0

    val dbChangedSubject: Subject<Int> = PublishSubject.create()

    fun saveTvShow(tvShow: TvShow): Observable<TvShowInDB> {
        return Observable.fromCallable {
            val tvShowDbFlow = tvShow.convertToTvShowDbFlow()
            tvShowDbFlow.saveInTransation()
            dbChangedSubject.onNext(SUBJECT)
            TvShowInDB.IN_DB
        }
    }

    fun deleteTvShow(tvShow: TvShow): Observable<TvShowInDB> {
        return Observable.fromCallable {
            val tvShowDbFlow = tvShow.convertToTvShowDbFlow()
            tvShowDbFlow.deleteInTransaction()
            dbChangedSubject.onNext(SUBJECT)
            TvShowInDB.NOT_IN_DB
        }
    }

    fun updateTvShow(tvShow: TvShow): Observable<TvShowInDB> {
        return Observable.fromCallable {
            val tvShowFromDb = loadShow(tvShow)
            if (tvShowFromDb.updated > tvShow.updated) {
                val tvShowDbFlow = tvShow.convertToTvShowDbFlow()
                tvShowDbFlow.saveInTransation()
                dbChangedSubject.onNext(SUBJECT)
            }
            TvShowInDB.IN_DB
        }
    }

    private fun loadShow(tvShow: TvShow): DbFlowTvShow {
        return SQLite.select()
                .from(DbFlowTvShow::class.java)
                .where(DbFlowTvShow_Table.id.eq(tvShow.id))
                .querySingle()!!
    }

    fun loadShowList(): Observable<List<TvShow>> {
        return Observable.fromCallable {
            SQLite.select()
                    .from(DbFlowTvShow::class.java)
                    .queryList()
        }
                .map { it.map { it.convertToTvShowEntity() } }
    }

    fun loadShowEpisodes(tvShow: TvShow): List<Episode> {
        return SQLite.select().from(DbFlowEpisode::class.java)
                .where(DbFlowEpisode_Table.tvShow_id.eq(tvShow.id))
                .queryList()
                .map { it.convertToEpisodeEntity() }
    }

    fun checkIfInDatabase(tvShow: TvShow): TvShowInDB {
        val inDb: Boolean = SQLite.selectCountOf(DbFlowTvShow_Table.id)
                .from(DbFlowTvShow::class.java)
                .where(DbFlowTvShow_Table.id.eq(tvShow.id))
                .count() > 0
        return MainDatabase.TvShowInDB.valueOf(inDb)
    }
}