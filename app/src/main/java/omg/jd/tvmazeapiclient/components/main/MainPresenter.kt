package omg.jd.tvmazeapiclient.components.main

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import omg.jd.tvmazeapiclient.entity.EntityUtils.SORT_BY
import omg.jd.tvmazeapiclient.entity.TvShow
import omg.jd.tvmazeapiclient.recyclerview.TvShowViewHolder

class MainPresenter(val interactor: MVPMain.Interactor) : MVPMain.Presenter {
    override var view: MVPMain.View? = null

    override fun onResume() {
        if (interactor.needToReload) {
            view?.setLoading()
            interactor.loadShowList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        view?.setShows(it)
                    }
        }
    }

    override fun onMenuCreated() {
        view?.checkSortBy(interactor.sortBy)
    }

    override fun sortBy(sortBy: SORT_BY) {
        view?.setShows(interactor.sortShowList(sortBy))
        view?.checkSortBy(interactor.sortBy)
    }

    override fun onItemClick(viewHolder: TvShowViewHolder) {
        view?.showDetails(viewHolder.data as TvShow, viewHolder.transitedView)
    }
}