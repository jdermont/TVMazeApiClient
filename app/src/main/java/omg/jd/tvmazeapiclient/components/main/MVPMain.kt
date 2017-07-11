package omg.jd.tvmazeapiclient.components.main

import io.reactivex.Observable
import omg.jd.tvmazeapiclient.entity.TvShow
import omg.jd.tvmazeapiclient.mvp.BasePresenter
import omg.jd.tvmazeapiclient.mvp.BaseView

interface MVPMain {
    interface Interactor {
        enum class SORT_BY(val comparator: (o1: TvShow,o2: TvShow) -> Int) {
            DEFAULT({
                o1, o2 -> o1.compareTo(o2)
            }),
            PREMIERED({
                o1, o2 ->
                if (o1.premiered == null) {
                    if (o2.premiered == null) 0 else 1
                } else if (o2.premiered == null) {
                    -1
                } else {
                    o1.premiered.compareTo(o2.premiered)
                }
            }),
            NEXT_EPISODE({
                o1, o2 ->
                if (o1.episodes.isEmpty()) {
                    if (o2.episodes.isEmpty()) 0 else 1
                } else if (o2.episodes.isEmpty()) {
                    -1
                } else {
                    val e1 = o1.episodes.last()
                    val e2 = o2.episodes.last()
                    e1.compareTo(e2)
                }
            })
        }

        var sortBy: SORT_BY

        fun loadShowList(): Observable<List<TvShow>>
        fun sortShowList(sortBy: SORT_BY): List<TvShow>
    }

    interface Presenter : BasePresenter<View> {
        fun onResume()
        fun onMenuCreated()
        fun sortBy(sortBy: Interactor.SORT_BY)
    }

    interface View : BaseView {
        fun setLoading()
        fun setEmpty()
        fun setShows(shows: List<TvShow>)
        fun checkSortBy(sortBy: Interactor.SORT_BY)
    }
}