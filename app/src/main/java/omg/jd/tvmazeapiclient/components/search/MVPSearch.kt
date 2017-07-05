package omg.jd.tvmazeapiclient.components.search

import io.reactivex.Observable
import omg.jd.tvmazeapiclient.components.search.recyclerview.SearchItemViewHolder
import omg.jd.tvmazeapiclient.db.model.DbFlowTvShow
import omg.jd.tvmazeapiclient.entity.TvShow
import omg.jd.tvmazeapiclient.mvp.BasePresenter
import omg.jd.tvmazeapiclient.mvp.BaseView
import omg.jd.tvmazeapiclient.ws.model.WsTVShow

interface MVPSearch {
    interface Interactor {
        fun searchShows(input: String): Observable<List<WsTVShow>>
    }

    interface Presenter : BasePresenter<View> {
        fun onSearch(input: String)
        fun onItemClick(viewHolder: SearchItemViewHolder)
    }

    interface View : BaseView {
        fun setLoading()
        fun setEmpty()
        fun errorOnGettingList()
        fun setShows(shows: List<TvShow>)
        fun showDetails(show: TvShow, transitedView: android.view.View)
    }
}