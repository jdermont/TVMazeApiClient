package omg.jd.tvmazeapiclient.components.search

import io.reactivex.android.schedulers.AndroidSchedulers
import omg.jd.tvmazeapiclient.mvp.BaseView
import omg.jd.tvmazeapiclient.utils.convertToTvShow

class SearchPresenter(val interactor: MVPSearch.Interactor) : MVPSearch.Presenter {

    var view: MVPSearch.View? = null

    var lastSearchText: String = ""

    override fun onSearch(input: String) {
        lastSearchText = input.trim()
        view?.setLoading()
        retrieveTvShows()
    }

    override fun onViewAttached(view: BaseView) {
        this.view = view as MVPSearch.View
        retrieveTvShows()
    }

    override fun onViewDetached() {
        this.view = null
    }

    override fun onDestroyed() {
        this.view = null
    }

    private fun retrieveTvShows() {
        interactor.searchShows(lastSearchText)
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapIterable { it }
                .map { it.show.convertToTvShow() }
                .toList()
                .subscribe {
                    it -> view?.setShows(it)
                }
    }

}