package com.app.reactortest.api

import com.app.reactortest.api.response.BaseResponse
import com.app.reactortest.model.GiphyObject
import com.app.reactortest.model.MetaObject
import com.app.reactortest.model.PaginationObject

object ApiSettings {
    const val API_KEY = "TS8mMLxhB5ivDBUN7MvI3Kz6rgla2lS8"
    const val DEFAULT_FORMAT = "json"
    const val DEFAULT_LIMIT = 25
    const val DEFAULT_LANG = "ru"
    const val DEFAULT_RATING = "g"
}
typealias SearchResponse = BaseResponse<GiphyObject, MetaObject, PaginationObject>