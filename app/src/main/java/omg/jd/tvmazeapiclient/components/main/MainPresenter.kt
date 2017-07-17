package omg.jd.tvmazeapiclient.components.main

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import omg.jd.tvmazeapiclient.entity.EntityUtils.SORT_BY

class MainPresenter(val interactor: MVPMain.Interactor) : MVPMain.Presenter {
    override var view: MVPMain.View? = null

    override fun onResume() {
        view?.setLoading()
        interactor.loadShowList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    view?.setShows(it)
                }
    }

    override fun onMenuCreated() {
        view?.checkSortBy(interactor.sortBy)
    }

    override fun sortBy(sortBy: SORT_BY) {
        view?.setShows(interactor.sortShowList(sortBy))
        view?.checkSortBy(sortBy)
    }
}