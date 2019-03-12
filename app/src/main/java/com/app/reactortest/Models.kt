package com.app.reactortest

import com.google.gson.annotations.SerializedName


data class GiphyObject(
    val type: String,
    val id: String,
    val url: String,
    val username: String,
    val rating: String,
    val title: String,
    val images: ImagesInfo
)

data class ImagesInfo(
    @SerializedName("fixed_height_still") val imagePreview: GiphyImage
)

data class GiphyImage(
    val url: String,
    val size: String?,
    val width: String,
    val height: String
)

data class PaginationObject(
    val offset: Int,
    @SerializedName("total_count") val totalCount: Int,
    val count: Int
)

data class MetaObject(
    val msg: String,
    val status: Int,
    @SerializedName("response_id") val responseId: String
)
