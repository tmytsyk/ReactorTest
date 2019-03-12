package com.app.reactortest.model

import com.google.gson.annotations.SerializedName


data class PaginationObject(
    val offset: Int,
    @SerializedName("total_count") val totalCount: Int,
    val count: Int
)
