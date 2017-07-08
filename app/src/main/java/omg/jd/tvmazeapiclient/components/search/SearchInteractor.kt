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

    private val searchCache: LruCache<String, List<WsTVShow>> by lazy {
        val cache = LruCache<String, List<WsTVShow>>(MAX_ENTRIES)
        cache.put("",listOf())
        cache
    }

    override fun searchShows(input: String): Observable<List<WsTVShow>> {
        val tvShows: List<WsTVShow>? = searchCache.get(input)

        if (tvShows == null) {
            return ApiClient.retrieveTVShows(input)
                    .doOnNext { searchCache.put(input,it) }
        }

        return Observable.fromCallable { tvShows }
    }
}
