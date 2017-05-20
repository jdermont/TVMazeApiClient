package omg.jd.tvmazeapiclient.components.search

import android.util.LruCache
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import omg.jd.tvmazeapiclient.ws.ApiClient
import omg.jd.tvmazeapiclient.ws.model.WsTVShow

class SearchInteractor : MVPSearch.Interactor {
    companion object {
        const val MAX_ENTRIES: Int = 20
    }

    val searchCache: LruCache<String, List<WsTVShow>> = LruCache(MAX_ENTRIES)

    override fun searchShows(input: String): Observable<List<WsTVShow>> {
        val tvShows: List<WsTVShow>? = searchCache.get(input)

        if (tvShows == null) {
            return ApiClient.retrieveTVShows(input)
                    .subscribeOn(Schedulers.io())
                    .doOnNext { searchCache.put(input,it) }
        }

        return Observable.fromArray(tvShows)
    }
}
