package com.app.reactortest.networking

import com.app.reactortest.model.GiphyObject
import com.app.reactortest.model.MetaObject
import com.app.reactortest.model.PaginationObject
import com.app.reactortest.networking.response.BaseResponse
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

typealias SearchResponse = BaseResponse<GiphyObject, MetaObject, PaginationObject>

object GiphyApiClient {
    private const val BASE_URL = "https://api.giphy.com"
    private const val API_KEY = "TS8mMLxhB5ivDBUN7MvI3Kz6rgla2lS8"
    private const val DEFAULT_FORMAT = "json"
    private const val DEFAULT_LIMIT = 25
    private const val DEFAULT_LANG = "ru"
    private const val DEFAULT_RATING = "g"

    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

    val apiAppClient: AppApiInterface = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(AppApiInterface::class.java)

    interface AppApiInterface {
        @GET("/v1/gifs/search")
        fun searchGifs(
            @Query("api_key") apiKey: String = API_KEY,
            @Query("q") query: String,
            @Query("limit") limit: Int = DEFAULT_LIMIT,
            @Query("offset") offset: Int?,
            @Query("rating") rating: String = DEFAULT_RATING,
            @Query("lang") lang: String = DEFAULT_LANG,
            @Query("fmt") format: String = DEFAULT_FORMAT
        ): Observable<SearchResponse>
    }
}
