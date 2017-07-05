package omg.jd.tvmazeapiclient.components.details

import io.reactivex.Observable
import omg.jd.tvmazeapiclient.db.model.DbFlowTvShow
import omg.jd.tvmazeapiclient.entity.TvShow
import omg.jd.tvmazeapiclient.mvp.BasePresenter
import omg.jd.tvmazeapiclient.mvp.BaseView

interface MVPDetails {
    interface Interactor {
        val tvShow: TvShow

        fun setTvShowIfNeeded(tvShow: TvShow)
        fun retrieveEpisodes(): Observable<TvShow>
    }

    interface Presenter : BasePresenter<View> {
        fun onInit(tvShow: TvShow)
    }

    interface View : BaseView {
        fun loadImageHeader(imageUrl: String?)
        fun setupViews(tvShow: TvShow)
        fun writeEpisodes(latest: String, next: String)
    }
}