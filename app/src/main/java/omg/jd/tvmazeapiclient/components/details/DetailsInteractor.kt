package omg.jd.tvmazeapiclient.components.details

import com.raizlabs.android.dbflow.sql.language.SQLite
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import omg.jd.tvmazeapiclient.db.model.DbFlowEpisode
import omg.jd.tvmazeapiclient.db.model.DbFlowEpisode_Table
import omg.jd.tvmazeapiclient.db.model.DbFlowTvShow
import omg.jd.tvmazeapiclient.db.model.DbFlowTvShow_Table
import omg.jd.tvmazeapiclient.entity.TvShow
import omg.jd.tvmazeapiclient.ws.convertToEpisodesListEntity
import omg.jd.tvmazeapiclient.ws.ApiClient
import omg.jd.tvmazeapiclient.ws.convertToTvShowDbFlow

class DetailsInteractor : MVPDetails.Interactor {

    private var cachedTvShow: TvShow? = null

    override val tvShow: TvShow
        get() = cachedTvShow ?: throw IllegalStateException("tvShow in interactor is null")

    override var tvShowExistsInDb: Boolean = false

    override fun setTvShowIfNeeded(tvShow: TvShow) {
        if (cachedTvShow == null) {
            cachedTvShow = tvShow
        }
    }

    override fun checkIfTvShowExistsInDb() {
        tvShowExistsInDb = SQLite.select().from(DbFlowTvShow::class.java)
                .where(DbFlowTvShow_Table.id.eq(tvShow.id))
                .querySingle() != null
    }

    override fun retrieveEpisodes(): Observable<TvShow> {
        val cachedTvShow: TvShow = this.cachedTvShow ?: throw IllegalStateException("tvShow in interactor is null")
        val observable =
                if (cachedTvShow.episodes.isNotEmpty()) {
                    Observable.fromCallable { cachedTvShow }
                            .subscribeOn(Schedulers.io())
                } else {
                    ApiClient.retrieveTVShow(tvShow.id.toString())
                            .subscribeOn(Schedulers.io())
                            .map {
                                if (it.embedded != null) {
                                    cachedTvShow.episodes = it.embedded.convertToEpisodesListEntity()
                                }
                                cachedTvShow
                            }
                }
        return observable
    }

    override fun saveTvShow() {
        val tvShowDbFlow = tvShow.convertToTvShowDbFlow()
        tvShowDbFlow.save()
        tvShowExistsInDb = true
    }

}