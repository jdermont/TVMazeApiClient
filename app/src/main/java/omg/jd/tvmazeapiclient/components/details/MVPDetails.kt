package omg.jd.tvmazeapiclient.components.details

import io.reactivex.Observable
import omg.jd.tvmazeapiclient.db.model.Episode
import omg.jd.tvmazeapiclient.db.model.TvShow
import omg.jd.tvmazeapiclient.mvp.BasePresenter
import omg.jd.tvmazeapiclient.mvp.BaseView
import omg.jd.tvmazeapiclient.ws.model.WsShow

interface MVPDetails {
    interface Interactor {
        var tvShow: TvShow

        fun retrieveEpisodes(): Observable<TvShow>
    }

    interface Presenter : BasePresenter<View> {
        fun onInit(tvShow: TvShow)
    }

    interface View : BaseView {
        fun loadImageHeader(imageUrl: String?)
        fun setupViews(tvShow: TvShow)
        fun setupEpisodes(latestEpisode: Episode?, nextEpisode: Episode?)
    }
}