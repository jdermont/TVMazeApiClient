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
        fun saveTvShow(): Observable<Presenter.TvShowInDB>
        fun deleteTvShow(): Observable<Presenter.TvShowInDB>
        fun checkForTvShowInDB(): Observable<Presenter.TvShowInDB>
    }

    interface Presenter : BasePresenter<View> {
        enum class TvShowInDB {
            NOT_IN_DB, IN_DB;
            companion object {
                fun valueOf(inDb: Boolean): TvShowInDB = if (inDb) IN_DB else NOT_IN_DB
            }
        }

        fun onInit(tvShow: TvShow)
        fun onFabClicked(tvShowInDB: TvShowInDB)
    }

    interface View : BaseView {
        fun loadImageHeader(imageUrl: String?)
        fun setupViews(tvShow: TvShow)
        fun enableFloatingActionProgress()
        fun disableFloatingActionProgress()
        fun setFloatingActionButton(tvShowInDB: Presenter.TvShowInDB)
        fun writeEpisodes(latest: String, next: String)
    }
}