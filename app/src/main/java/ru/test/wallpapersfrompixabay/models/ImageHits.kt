package ru.test.wallpapersfrompixabay.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ImageHits(
    @Expose
    @SerializedName("hits")
    val hits: List<Image>? = null
)