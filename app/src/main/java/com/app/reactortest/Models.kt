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
    val url: String,         // The publicly-accessible direct URL for this GIF.
    val size: String?,        // The size of this GIF in bytes.
    val width: String,
    val height: String
)

data class PaginationObject(
    val offset: Int,        // position in pagination
    @SerializedName("total_count") val totalCount: Int,    // total number of item available
    val count: Int          // total number of item returned
)

data class MetaObject(
    val msg: String,        // response message
    val status: Int,        // response status code
    @SerializedName("response_id") val responseId: String  // unique response id
)
