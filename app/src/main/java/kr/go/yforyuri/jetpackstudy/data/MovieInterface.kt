package kr.go.yforyuri.jetpackstudy.network

import com.google.gson.GsonBuilder
import kr.go.yforyuri.jetpackstudy.data.MovieResponse
import kr.go.yforyuri.jetpackstudy.util.API_KEY
import kr.go.yforyuri.jetpackstudy.util.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface MovieInterface {

    @GET("/")
    suspend fun getAllMovies(
        @Query("s") s: String,
        @Query("page") page: Int
    ) : Response<MovieResponse>

    companion object {

        private var _instance : MovieInterface? = null
        private val instance get() = _instance!!

        operator fun invoke() : MovieInterface {
            if(_instance == null) {
                val requestInterceptor = Interceptor {
                    val url = it.request()
                        .url()
                        .newBuilder()
                        .addQueryParameter("apikey", API_KEY)
                        .build()
                    val request = it.request()
                        .newBuilder()
                        .url(url)
                        .build()
                    return@Interceptor it.proceed(request)
                }
                val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(requestInterceptor)
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(5, TimeUnit.SECONDS)
                    .build()
                val gson = GsonBuilder()
                    .setLenient()
                    .create()
                _instance = Retrofit.Builder()
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(BASE_URL)
                    .build()
                    .create(MovieInterface::class.java)
            }
            return instance
        }
    }

}