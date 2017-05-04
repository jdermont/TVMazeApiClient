package omg.jd.tvmazeapiclient.ws

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import omg.jd.tvmazeapiclient.BuildConfig
import java.util.concurrent.TimeUnit

object HttpClientBuilder {

    fun create(): OkHttpClient {
        val client = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BASIC
            client.addInterceptor(interceptor)
        }

        return client.build()
    }
}
