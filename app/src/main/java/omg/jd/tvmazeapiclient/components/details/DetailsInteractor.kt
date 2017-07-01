package omg.jd.tvmazeapiclient.components.details

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import omg.jd.tvmazeapiclient.db.model.TvShow
import omg.jd.tvmazeapiclient.utils.convertToEpisodes
import omg.jd.tvmazeapiclient.ws.ApiClient

class DetailsInteractor : MVPDetails.Interactor {

    private lateinit var cachedTvShow: TvShow

    override var tvShow: TvShow
        get() = cachedTvShow
        set(value) {
            cachedTvShow = value
        }

    override fun retrieveEpisodes(): Observable<TvShow> {
        return ApiClient.retrieveTVShow(tvShow.id.toString())
                .subscribeOn(Schedulers.io())
                .map {
                    if (it.embedded != null) {
                        cachedTvShow.episodes = it.embedded.convertToEpisodes()
                    }
                    cachedTvShow
                }
    }

}