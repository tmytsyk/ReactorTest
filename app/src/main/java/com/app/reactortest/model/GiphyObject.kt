package com.app.reactortest.model

data class GiphyObject(
    val type: String,
    val id: String,
    val url: String,
    val username: String,
    val rating: String,
    val title: String,
    val images: ImagesInfo
)
