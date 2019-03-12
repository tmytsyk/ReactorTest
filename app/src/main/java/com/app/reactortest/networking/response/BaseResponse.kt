package com.app.reactortest.networking.response

import com.google.gson.annotations.SerializedName


data class BaseResponse<A, B, C>(
    @SerializedName("data") val data: List<A>,
    @SerializedName("meta") val meta: B,
    @SerializedName("pagination") val pagination: C
)

