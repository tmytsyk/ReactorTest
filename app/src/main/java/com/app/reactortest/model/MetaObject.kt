package com.app.reactortest.model

import com.google.gson.annotations.SerializedName


data class MetaObject(
    val msg: String,
    val status: Int,
    @SerializedName("response_id") val responseId: String
)
