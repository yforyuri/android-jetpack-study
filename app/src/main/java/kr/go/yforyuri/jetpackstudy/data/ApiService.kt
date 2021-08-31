package kr.go.yforyuri.jetpackstudy.data

import com.google.gson.GsonBuilder
import io.reactivex.Single
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


const val BASE_URL ="http://api.themoviedb.org/3/"
const val API_KEY ="dec8c36f518a5ef958043cf9c6027b80"

interface ApiService {

//   일반 요청 - fun, Call
//   RxJava - fun, Single...
//   코루틴 - suspend fun, Response
    @GET("movie/popular")
    suspend fun getPopularMovie(
//        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<MovieRes>

    companion object{

        private var _apiService: ApiService? = null
        private val apiService: ApiService get() = _apiService!!

        operator fun invoke() : ApiService{
            if(_apiService == null) {
                val requestInterceptor = Interceptor {
                    val url:HttpUrl = it.request()
                        .url()
                        .newBuilder()
                        .addQueryParameter("api_key", API_KEY)
                        .build()
                    val request: Request = it.request()
                        .newBuilder()
                        .url(url)
                        .build()
                    return@Interceptor it.proceed(request)
                }
                val okHttpClient:OkHttpClient = OkHttpClient.Builder()
                    .addInterceptor(requestInterceptor)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .build()
                val gson = GsonBuilder()
                    .setLenient()
                    .create()
                _apiService = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .build()
                    .create(ApiService::class.java)
            }
            return apiService
        }
    }
}