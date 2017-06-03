package omg.jd.tvmazeapiclient.components.search

import io.reactivex.android.schedulers.AndroidSchedulers
import omg.jd.tvmazeapiclient.components.search.recyclerview.SearchItemViewHolder
import omg.jd.tvmazeapiclient.db.model.TvShow
import omg.jd.tvmazeapiclient.mvp.BaseView
import omg.jd.tvmazeapiclient.utils.convertToTvShow

class SearchPresenter(val interactor: MVPSearch.Interactor) : MVPSearch.Presenter {

    override var view: MVPSearch.View? = null

    override fun onSearch(input: String) {
        val searchQuery: String = input.trim()

        view?.setLoading()
        interactor.searchShows(searchQuery)
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapIterable { it }
                .map { it.show.convertToTvShow() }
                .toList()
                .subscribe {
                    it -> view?.setShows(it)
                }
    }

    override fun onItemClick(viewHolder: SearchItemViewHolder) {
        view?.showDetails(viewHolder.data as TvShow, viewHolder.transitedView)
    }


}
