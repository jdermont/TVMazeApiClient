package omg.jd.tvmazeapiclient.components.details

import io.reactivex.android.schedulers.AndroidSchedulers
import omg.jd.tvmazeapiclient.db.model.Episode
import omg.jd.tvmazeapiclient.db.model.TvShow
import omg.jd.tvmazeapiclient.utils.StringUtils
import org.joda.time.DateTime


class DetailsPresenter(val interactor: MVPDetails.Interactor) : MVPDetails.Presenter {
    override var view: MVPDetails.View? = null

    override fun onInit(tvShow: TvShow) {
        interactor.setTvShowIfNeeded(tvShow)
        view?.loadImageHeader(interactor.tvShow.originalImage)
        view?.setupViews(interactor.tvShow)

        interactor.retrieveEpisodes()
                .map {
                    val now = DateTime.now()
                    val latest = it.episodes?.lastOrNull { it.datetime <= now }
                    val next = it.episodes?.firstOrNull { it.datetime > now }
                    Pair(makeEpisodeNumber(latest),makeEpisodeNumber(next))
                }
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
}