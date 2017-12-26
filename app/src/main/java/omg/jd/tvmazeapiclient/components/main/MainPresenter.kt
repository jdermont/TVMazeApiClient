package omg.jd.tvmazeapiclient.components.main

import android.content.Context
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import omg.jd.tvmazeapiclient.entity.EntityUtils.SORT_BY
import omg.jd.tvmazeapiclient.entity.TvShow
import omg.jd.tvmazeapiclient.job.JobUtil
import omg.jd.tvmazeapiclient.recyclerview.TvShowViewHolder
import omg.jd.tvmazeapiclient.utils.DateTimeUtils
import org.joda.time.DateTime

class MainPresenter(val interactor: MVPMain.Interactor) : MVPMain.Presenter {
    override var view: MVPMain.View? = null

    override fun onResume() {
        if (interactor.needToReload) {
            view?.setLoading()
            interactor.loadFromDbShowList()
                    .doOnNext { updateShowList(it) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        it.forEach {
                            val now = DateTime.now()
                            val nextEpisode = it.episodes.firstOrNull { it.datetime > now }
                            if (nextEpisode != null) {
                                JobUtil.scheduleNotificationJob(view as Context, it.id, nextEpisode.datetime.millis)
                            }
                        }
                        view?.setShows(it)
                    }
        } else {
            view?.setShows(interactor.getShowList())
        }
    }

    override fun onMenuCreated() {
        view?.checkSortBy(interactor.sortBy)
    }

    override fun onStartSearchComponentClicked() {
        view?.startSearchComponent()
    }

    override fun onSortByClicked(sortBy: SORT_BY) {
        view?.setShows(interactor.sortShowList(sortBy))
        view?.checkSortBy(interactor.sortBy)
    }

    override fun onItemClick(viewHolder: TvShowViewHolder) {
        view?.showDetails(viewHolder.data as TvShow, viewHolder.transitedView)
    }

    override fun onReset() {
        interactor.destroy()
    }

    override fun onSettingsClicked() {
        view?.startSettingsComponent()
    }

    private fun updateShowList(showList: List<TvShow>) {
        interactor.updateShowList(showList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    view?.setShows(it)
                }
    }
}