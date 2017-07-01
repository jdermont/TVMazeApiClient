package omg.jd.tvmazeapiclient.components.details

import io.reactivex.android.schedulers.AndroidSchedulers
import omg.jd.tvmazeapiclient.db.model.TvShow
import org.joda.time.DateTime


class DetailsPresenter(val interactor: MVPDetails.Interactor) : MVPDetails.Presenter {
    override var view: MVPDetails.View? = null

    override fun onInit(tvShow: TvShow) {
        interactor.tvShow = tvShow
        view?.loadImageHeader(tvShow.originalImage)
        view?.setupViews(tvShow)

        interactor.retrieveEpisodes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val now = DateTime.now()
                    val latest = it.episodes.lastOrNull { it.datetime <= now }
                    val next = it.episodes.firstOrNull { it.datetime > now }
                    view?.setupEpisodes(latest,next)
                }
    }
}