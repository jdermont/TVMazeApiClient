package omg.jd.tvmazeapiclient.components.details

import com.raizlabs.android.dbflow.sql.language.SQLite
import io.reactivex.Observable
import omg.jd.tvmazeapiclient.db.MainDatabase
import omg.jd.tvmazeapiclient.db.model.DbFlowTvShow
import omg.jd.tvmazeapiclient.db.model.DbFlowTvShow_Table
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

    override fun checkForTvShowInDB(): Observable<MainDatabase.TvShowInDB> {
        return Observable.fromCallable {
            val inDb = SQLite.select().from(DbFlowTvShow::class.java)
                    .where(DbFlowTvShow_Table.id.eq(tvShow.id))
                    .querySingle() != null
            MainDatabase.TvShowInDB.valueOf(inDb)
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

    override fun saveTvShow(): Observable<MainDatabase.TvShowInDB> {
        return MainDatabase.saveTvShow(tvShow)
    }

    override fun deleteTvShow(): Observable<MainDatabase.TvShowInDB> {
        return MainDatabase.deleteTvShow(tvShow)
    }

}