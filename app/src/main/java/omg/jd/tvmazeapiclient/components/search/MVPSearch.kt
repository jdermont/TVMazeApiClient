package omg.jd.tvmazeapiclient.components.search

import io.reactivex.Observable
import omg.jd.tvmazeapiclient.db.model.TvShow
import omg.jd.tvmazeapiclient.mvp.BasePresenter
import omg.jd.tvmazeapiclient.mvp.BaseView
import omg.jd.tvmazeapiclient.ws.model.WsTVShow

interface MVPSearch {
    interface Interactor {
        fun searchShows(input: String): Observable<List<WsTVShow>>
    }

    interface Presenter : BasePresenter<View> {
        fun onSearch(input: String)
    }

    interface View : BaseView {
        fun setLoading()
        fun setEmpty()
        fun setShows(shows: List<TvShow>)
    }
}