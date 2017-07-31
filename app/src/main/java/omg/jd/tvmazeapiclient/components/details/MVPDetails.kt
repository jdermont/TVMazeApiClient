package omg.jd.tvmazeapiclient.components.details

import io.reactivex.Observable
import omg.jd.tvmazeapiclient.db.MainDatabase
import omg.jd.tvmazeapiclient.entity.TvShow
import omg.jd.tvmazeapiclient.mvp.BasePresenter
import omg.jd.tvmazeapiclient.mvp.BaseView

interface MVPDetails {
    interface Interactor {
        val tvShow: TvShow

        fun setTvShowIfNeeded(tvShow: TvShow, withEpisodes: Boolean)
        fun retrieveEpisodes(): Observable<TvShow>
        fun saveTvShow(): Observable<MainDatabase.TvShowInDB>
        fun deleteTvShow(): Observable<MainDatabase.TvShowInDB>
        fun checkForTvShowInDB(): Observable<MainDatabase.TvShowInDB>
    }

    interface Presenter : BasePresenter<View> {
        fun onInit(tvShow: TvShow, withEpisodes: Boolean)
        fun onFabClicked(tvShowInDB: MainDatabase.TvShowInDB)
    }

    interface View : BaseView {
        fun loadImageHeader(imageUrl: String?)
        fun setupViews(tvShow: TvShow)
        fun enableFloatingActionProgress()
        fun disableFloatingActionProgress()
        fun setFloatingActionButton(tvShowInDB: MainDatabase.TvShowInDB)
        fun writeEpisodes(latest: String, next: String)
    }
}