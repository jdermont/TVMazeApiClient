package omg.jd.tvmazeapiclient.ws

import io.reactivex.Observable
import omg.jd.tvmazeapiclient.ws.model.TVShow
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("search/shows")
    fun getTVShows(@Query("q") shows: String): Observable<List<TVShow>>
}
