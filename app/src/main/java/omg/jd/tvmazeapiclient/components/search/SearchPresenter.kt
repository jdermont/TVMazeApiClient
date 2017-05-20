package omg.jd.tvmazeapiclient.components.search

import io.reactivex.android.schedulers.AndroidSchedulers
import omg.jd.tvmazeapiclient.mvp.BaseView
import omg.jd.tvmazeapiclient.utils.convertToTvShow

class SearchPresenter(val interactor: MVPSearch.Interactor) : MVPSearch.Presenter {

    var view: MVPSearch.View? = null

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

    override fun onViewAttached(view: BaseView) {
        this.view = view as MVPSearch.View
    }

    override fun onViewDetached() {
        this.view = null
    }

    override fun onDestroyed() {
        this.view = null
    }
}
