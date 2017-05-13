package omg.jd.tvmazeapiclient.components.search

import io.reactivex.Observable
import omg.jd.tvmazeapiclient.ws.ApiClient
import omg.jd.tvmazeapiclient.ws.model.TVShow

class SearchInteractor : MVPSearch.Interactor {

    override fun searchShows(input: String): Observable<List<TVShow>> {
        return ApiClient.retrieveTVShows(input)
    }
}