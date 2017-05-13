package omg.jd.tvmazeapiclient.components.search

import io.reactivex.Observable
import omg.jd.tvmazeapiclient.ws.model.TVShow

interface MVPSearch {
    interface Interactor {
        fun searchShows(input: String): Observable<List<TVShow>>
    }

    interface Presenter {
        fun onResume()
        fun onPause()
        fun onDestroy()

        fun onSearch(input: String)
    }

    interface View {
        fun setShows(shows: List<TVShow>)
    }
}