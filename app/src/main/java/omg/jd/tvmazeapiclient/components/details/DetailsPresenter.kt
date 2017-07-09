package omg.jd.tvmazeapiclient.components.details

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import omg.jd.tvmazeapiclient.components.details.MVPDetails.Presenter.TvShowInDB
import omg.jd.tvmazeapiclient.entity.Episode
import omg.jd.tvmazeapiclient.entity.TvShow
import omg.jd.tvmazeapiclient.utils.DateTimeUtils
import omg.jd.tvmazeapiclient.utils.StringUtils
import org.joda.time.DateTime

class DetailsPresenter(val interactor: MVPDetails.Interactor) : MVPDetails.Presenter {
    override var view: MVPDetails.View? = null

    override fun onInit(tvShow: TvShow) {
        interactor.setTvShowIfNeeded(tvShow)
        view?.loadImageHeader(interactor.tvShow.originalImage)
        view?.setupViews(interactor.tvShow)

        interactor.checkForTvShowInDB()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    view?.setFloatingActionButton(it)
                }

        interactor.retrieveEpisodes()
                .map {
                    val now = DateTime.now()
                    val latestEpisode = it.episodes.lastOrNull { it.datetime <= now }
                    val nextEpisode = it.episodes.firstOrNull { it.datetime > now }
                    val str = if (nextEpisode == null) "" else " (${DateTimeUtils.getDateString(nextEpisode.datetime)})"
                    Pair(makeEpisodeNumber(latestEpisode),makeEpisodeNumber(nextEpisode)+str)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    view?.writeEpisodes(it.first,it.second)
                }
    }

    private fun makeEpisodeNumber(episode: Episode?): String {
        if (episode == null) {
            return "-"
        } else {
            return "${StringUtils.startPadZero(episode.season)}x${StringUtils.startPadZero(episode.number)}"
        }
    }

    override fun onFabClicked(tvShowInDB: TvShowInDB) {
        view?.enableFloatingActionProgress()
        val observable: Observable<TvShowInDB>
        when (tvShowInDB) {
            TvShowInDB.NOT_IN_DB ->
                observable = interactor.saveTvShow()
            TvShowInDB.IN_DB ->
                observable = interactor.deleteTvShow()
        }
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    view?.setFloatingActionButton(it)
                    view?.disableFloatingActionProgress()
                }

    }
}