package omg.jd.tvmazeapiclient.components.details

import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import omg.jd.tvmazeapiclient.db.MainDatabase
import omg.jd.tvmazeapiclient.entity.TvShow
import omg.jd.tvmazeapiclient.utils.DateTimeUtils
import omg.jd.tvmazeapiclient.utils.StringUtils
import org.joda.time.DateTime

class DetailsPresenter(val interactor: MVPDetails.Interactor) : MVPDetails.Presenter {
    override var view: MVPDetails.View? = null

    override fun onInit(tvShow: TvShow, withEpisodes: Boolean) {
        interactor.setTvShowIfNeeded(tvShow,withEpisodes)
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
                    val latestEpisode = it.episodes.lastOrNull { it.datetime != DateTimeUtils.INVALID_DATETIME && it.datetime <= now }
                    val nextEpisode = it.episodes.firstOrNull { it.datetime > now }
                    val str = if (nextEpisode == null) "" else " (${DateTimeUtils.getDateString(nextEpisode.datetime)})"
                    Pair(StringUtils.makeEpisodeNumber(latestEpisode),StringUtils.makeEpisodeNumber(nextEpisode)+str)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { // onNext
                            view?.writeEpisodes(it.first,it.second)
                        },
                        { // onError
                            it.printStackTrace()
                        },
                        { // onComplete
                        }
                )
    }

    override fun onFabClicked(tvShowInDB: MainDatabase.TvShowInDB) {
        view?.enableFloatingActionProgress()
        val observable: Observable<MainDatabase.TvShowInDB>
        when (tvShowInDB) {
            MainDatabase.TvShowInDB.NOT_IN_DB ->
                observable = interactor.saveTvShow()
            MainDatabase.TvShowInDB.IN_DB ->
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