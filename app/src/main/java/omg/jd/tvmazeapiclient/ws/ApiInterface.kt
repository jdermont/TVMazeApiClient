package omg.jd.tvmazeapiclient.ws

import io.reactivex.Observable
import omg.jd.tvmazeapiclient.ws.model.WsShow
import omg.jd.tvmazeapiclient.ws.model.WsTVShow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("search/shows")
    fun getTVShows(@Query("q") shows: String): Observable<List<WsTVShow>>

    @GET("shows/{id}?embed=episodes")
    fun getTVShow(@Path("id") id: String): Observable<WsShow>
}
