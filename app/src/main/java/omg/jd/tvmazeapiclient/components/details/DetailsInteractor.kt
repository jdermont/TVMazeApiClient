package omg.jd.tvmazeapiclient.components.details

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import omg.jd.tvmazeapiclient.db.model.TvShow
import omg.jd.tvmazeapiclient.utils.convertToEpisodes
import omg.jd.tvmazeapiclient.ws.ApiClient

class DetailsInteractor : MVPDetails.Interactor {

    private var cachedTvShow: TvShow? = null

    override val tvShow: TvShow
        get() = cachedTvShow ?: throw IllegalStateException("tvShow in interactor is null")

    override fun setTvShowIfNeeded(tvShow: TvShow) {
        if (cachedTvShow == null) {
            cachedTvShow = tvShow
        }
    }

    override fun retrieveEpisodes(): Observable<TvShow> {
        val cachedTvShow: TvShow = this.cachedTvShow ?: throw IllegalStateException("tvShow in interactor is null")
        val observable =
                if (cachedTvShow.episodes?.isNotEmpty() ?: false) {
                    Observable.fromCallable { cachedTvShow }
                            .subscribeOn(Schedulers.io())
                } else {
                    ApiClient.retrieveTVShow(tvShow.id.toString())
                            .subscribeOn(Schedulers.io())
                            .map {
                                if (it.embedded != null) {
                                    cachedTvShow.episodes = it.embedded.convertToEpisodes()
                                }
                                cachedTvShow
                            }
                }
        return observable
    }

}