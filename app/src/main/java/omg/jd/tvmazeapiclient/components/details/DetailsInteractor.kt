package omg.jd.tvmazeapiclient.components.details

import android.os.SystemClock
import com.raizlabs.android.dbflow.sql.language.SQLite
import io.reactivex.Observable
import omg.jd.tvmazeapiclient.components.details.MVPDetails.Presenter.TvShowInDB
import omg.jd.tvmazeapiclient.db.dbflow.utils.deleteInTransaction
import omg.jd.tvmazeapiclient.db.dbflow.utils.saveInTransation
import omg.jd.tvmazeapiclient.db.model.DbFlowTvShow
import omg.jd.tvmazeapiclient.db.model.DbFlowTvShow_Table
import omg.jd.tvmazeapiclient.db.model.convertToTvShowDbFlow
import omg.jd.tvmazeapiclient.entity.TvShow
import omg.jd.tvmazeapiclient.ws.ApiClient
import omg.jd.tvmazeapiclient.ws.model.convertToEpisodesListEntity

class DetailsInteractor : MVPDetails.Interactor {

    private var cachedTvShow: TvShow? = null

    override val tvShow: TvShow
        get() = cachedTvShow ?: throw IllegalStateException("tvShow in interactor is null")

    override fun setTvShowIfNeeded(tvShow: TvShow) {
        if (cachedTvShow == null) {
            cachedTvShow = tvShow
        }
    }

    override fun checkForTvShowInDB(): Observable<TvShowInDB> {
        return Observable.fromCallable {
            SystemClock.sleep(500)
            val inDb = SQLite.select().from(DbFlowTvShow::class.java)
                    .where(DbFlowTvShow_Table.id.eq(tvShow.id))
                    .querySingle() != null
            TvShowInDB.valueOf(inDb)
        }
    }

    override fun retrieveEpisodes(): Observable<TvShow> {
        val cachedTvShow: TvShow = this.cachedTvShow ?: throw IllegalStateException("tvShow in interactor is null")
        val observable =
                if (cachedTvShow.episodes.isNotEmpty()) {
                    Observable.fromCallable { cachedTvShow }
                } else {
                    ApiClient.retrieveTVShow(tvShow.id.toString())
                            .map {
                                if (it.embedded != null) {
                                    cachedTvShow.episodes = it.embedded.convertToEpisodesListEntity()
                                }
                                cachedTvShow
                            }
                }
        return observable
    }

    override fun saveTvShow(): Observable<TvShowInDB> {
        return Observable.fromCallable {
            SystemClock.sleep(500)
            val tvShowDbFlow = tvShow.convertToTvShowDbFlow()
            tvShowDbFlow.saveInTransation()
            TvShowInDB.IN_DB
        }
    }

    override fun deleteTvShow(): Observable<TvShowInDB> {
        return Observable.fromCallable {
            SystemClock.sleep(500)
            val tvShowDbFlow = tvShow.convertToTvShowDbFlow()
            tvShowDbFlow.deleteInTransaction()
            TvShowInDB.NOT_IN_DB
        }
    }

}