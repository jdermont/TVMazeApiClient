package omg.jd.tvmazeapiclient.ws

import com.google.gson.GsonBuilder
import io.reactivex.Observable
import omg.jd.tvmazeapiclient.ws.model.WsLinks
import omg.jd.tvmazeapiclient.ws.model.WsTVShow
import omg.jd.tvmazeapiclient.ws.model.deserializers.LinksDeserializer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "http://api.tvmaze.com"

    private val apiInterface : ApiInterface

    init {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(WsLinks::class.java, LinksDeserializer())

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(HttpClientBuilder.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                .build()

        apiInterface = retrofit.create(ApiInterface::class.java)
    }

    fun retrieveTVShows(shows : String) : Observable<List<WsTVShow>> {
        return apiInterface.getTVShows(shows)
    }
}