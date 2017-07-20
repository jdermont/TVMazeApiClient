package omg.jd.tvmazeapiclient.components.search

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import omg.jd.tvmazeapiclient.entity.TvShow
import omg.jd.tvmazeapiclient.recyclerview.TvShowViewHolder
import omg.jd.tvmazeapiclient.ws.model.convertToTvShowEntity

class SearchPresenter(val interactor: MVPSearch.Interactor) : MVPSearch.Presenter {

    override var view: MVPSearch.View? = null

    override fun onSearch(input: String) {
        val searchQuery: String = input.trim()

        view?.setLoading()
        interactor.searchShows(searchQuery)
                .map { it.map { it.show.convertToTvShowEntity() } }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { // onNext
                            view?.setShows(it)
                        },
                        { // onError
                            it.printStackTrace()
                            view?.errorOnGettingList()
                        },
                        { // onComplete
                        }
                )
    }

    override fun onItemClick(viewHolder: TvShowViewHolder) {
        view?.showDetails(viewHolder.data as TvShow, viewHolder.transitedView)
    }


}
