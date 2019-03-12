package com.app.reactortest.api

import com.app.reactortest.api.ApiSettings.API_KEY
import com.app.reactortest.api.ApiSettings.DEFAULT_FORMAT
import com.app.reactortest.api.ApiSettings.DEFAULT_LANG
import com.app.reactortest.api.ApiSettings.DEFAULT_LIMIT
import com.app.reactortest.api.ApiSettings.DEFAULT_RATING
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyService {
    @GET("/v1/gifs/search")
    fun searchGifRequest(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("q") query: String,
        @Query("limit") limit: Int = DEFAULT_LIMIT,
        @Query("offset") offset: Int?,
        @Query("rating") rating: String = DEFAULT_RATING,
        @Query("lang") lang: String = DEFAULT_LANG,
        @Query("fmt") format: String = DEFAULT_FORMAT
    ): Observable<SearchResponse>
}
