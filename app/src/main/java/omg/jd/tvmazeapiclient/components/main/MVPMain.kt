package omg.jd.tvmazeapiclient.components.main

import io.reactivex.Observable
import omg.jd.tvmazeapiclient.db.MainDatabase
import omg.jd.tvmazeapiclient.entity.EntityUtils
import omg.jd.tvmazeapiclient.entity.TvShow
import omg.jd.tvmazeapiclient.mvp.BasePresenter
import omg.jd.tvmazeapiclient.mvp.BaseView
import omg.jd.tvmazeapiclient.recyclerview.TvShowViewHolder

interface MVPMain {
    interface Interactor {
        var sortBy: EntityUtils.SORT_BY
        val needToReload: Boolean

        fun loadFromDbShowList(): Observable<List<TvShow>>
        fun sortShowList(sortBy: EntityUtils.SORT_BY): List<TvShow>
        fun getShowList(): List<TvShow>
        fun updateShowList(showList: List<TvShow>): Observable<List<TvShow>>
        fun destroy()
    }

    interface Presenter : BasePresenter<View> {
        fun onResume()
        fun onMenuCreated()
        fun onStartSearchComponentClicked()
        fun onSortByClicked(sortBy: EntityUtils.SORT_BY)
        fun onItemClick(viewHolder: TvShowViewHolder)
        fun onSettingsClicked()
    }

    interface View : BaseView {
        fun setLoading()
        fun setEmpty()
        fun setShows(shows: List<TvShow>)
        fun startSearchComponent()
        fun checkSortBy(sortBy: EntityUtils.SORT_BY)
        fun showDetails(show: TvShow, transitedView: android.view.View)
        fun startSettingsComponent()
    }
}