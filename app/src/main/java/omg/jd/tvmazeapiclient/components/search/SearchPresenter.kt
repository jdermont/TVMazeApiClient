package omg.jd.tvmazeapiclient.components.search

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import omg.jd.tvmazeapiclient.db.model.TvShow
import omg.jd.tvmazeapiclient.mvp.BaseView
import omg.jd.tvmazeapiclient.utils.convertToTvShow

class SearchPresenter(val interactor: MVPSearch.Interactor) : MVPSearch.Presenter {

    var view: MVPSearch.View? = null

    var obs: Single<MutableList<TvShow>>? = null

    override fun onSearch(input: String) {
        val text: String = input.trim()

        obs = interactor.searchShows(text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache()
                .flatMapIterable { it }
                .map { it.show.convertToTvShow() }
                .toList()

        obs?.subscribe {
            it -> this.view?.setShows(it)
        }
    }

    override fun onViewAttached(view: BaseView) {
        this.view = view as MVPSearch.View
        obs?.subscribe {
            it -> this.view?.setShows(it)
        }
    }

    override fun onViewDetached() {
        this.view = null
    }

    override fun onDestroyed() {
        this.view = null
    }

}