package omg.jd.tvmazeapiclient.components.details

import io.reactivex.Observable
import omg.jd.tvmazeapiclient.db.MainDatabase
import omg.jd.tvmazeapiclient.db.MainDatabase.TvShowInDB
import omg.jd.tvmazeapiclient.entity.Episode
import omg.jd.tvmazeapiclient.entity.TvShow
import omg.jd.tvmazeapiclient.ws.ApiClient
import omg.jd.tvmazeapiclient.ws.model.convertToEpisodesListEntity

class DetailsInteractor : MVPDetails.Interactor {

    private var cachedTvShow: TvShow? = null

    override val tvShow: TvShow
        get() = cachedTvShow ?: throw IllegalStateException("tvShow in interactor is null")

    private var tvShowInDB: TvShowInDB? = null

    override fun setTvShowIfNeeded(tvShow: TvShow, withEpisodes: Boolean) {
        if (cachedTvShow == null) {
            if (withEpisodes) {
                val episodes: List<Episode> = MainDatabase.loadShowEpisodes(tvShow)
                tvShow.episodes = episodes
                tvShowInDB = TvShowInDB.IN_DB
            }
            cachedTvShow = tvShow
        }
    }

    override fun checkForTvShowInDB(): Observable<TvShowInDB> {
        return Observable.fromCallable {
            if (tvShowInDB == null) {
                tvShowInDB = MainDatabase.checkIfInDatabase(tvShow)
            }
            tvShowInDB
        }
    }

    override fun retrieveEpisodes(): Observable<TvShow> {
        val cachedTvShow: TvShow = this.cachedTvShow ?: throw IllegalStateException("tvShow in interactor is null")
        val wsObservable = ApiClient.retrieveTVShow(tvShow.id.toString())
                .map {
                    if (it.embedded != null) {
                        cachedTvShow.episodes = it.embedded.convertToEpisodesListEntity()
                    }
                    cachedTvShow
                }
                .doOnNext {
                    if (tvShowInDB == TvShowInDB.IN_DB) {
                        updateTvShow()
                    }
                }

        if (cachedTvShow.episodes.isNotEmpty()) {
            val cacheObservable = Observable.fromCallable { cachedTvShow }
            return Observable.mergeDelayError<TvShow>(cacheObservable,wsObservable)
        } else {
            return wsObservable
        }
    }

    override fun saveTvShow(): Observable<TvShowInDB> {
        return MainDatabase.saveTvShow(tvShow)
                .doOnNext { tvShowInDB = it }
    }

    override fun deleteTvShow(): Observable<TvShowInDB> {
        return MainDatabase.deleteTvShow(tvShow)
                .doOnNext { tvShowInDB = it }
    }

    private fun updateTvShow(): Observable<TvShowInDB> {
        return MainDatabase.updateTvShow(tvShow)
                .doOnNext { tvShowInDB = it }
    }

}