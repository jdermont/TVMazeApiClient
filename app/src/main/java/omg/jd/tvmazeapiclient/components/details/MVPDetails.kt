package omg.jd.tvmazeapiclient.components.details

import io.reactivex.Observable
import omg.jd.tvmazeapiclient.db.model.DbFlowTvShow
import omg.jd.tvmazeapiclient.entity.TvShow
import omg.jd.tvmazeapiclient.mvp.BasePresenter
import omg.jd.tvmazeapiclient.mvp.BaseView

interface MVPDetails {
    interface Interactor {
        val tvShow: TvShow
        var tvShowExistsInDb: Boolean

        fun setTvShowIfNeeded(tvShow: TvShow)
        fun checkIfTvShowExistsInDb()
        fun retrieveEpisodes(): Observable<TvShow>
        fun saveTvShow()
    }

    interface Presenter : BasePresenter<View> {
        fun onInit(tvShow: TvShow)
        fun onFabClicked()
    }

    interface View : BaseView {
        fun loadImageHeader(imageUrl: String?)
        fun setupViews(tvShow: TvShow)
        fun setFloatingActionButton(tvShowExistsInDb: Boolean)
        fun writeEpisodes(latest: String, next: String)
    }
}