package com.app.reactortest.model

import com.google.gson.annotations.SerializedName

data class ImagesInfo(@SerializedName("fixed_height_still") val imagePreview: GiphyImage)
