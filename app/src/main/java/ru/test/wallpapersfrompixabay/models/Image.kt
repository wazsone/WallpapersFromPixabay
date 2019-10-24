package ru.test.wallpapersfrompixabay.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Image(
    @Expose
    @SerializedName("id")
    val id: Int? = null,

    @Expose
    @SerializedName("previewURL")
    val previewURL: String? = null,

    @Expose
    @SerializedName("largeImageURL")
    val largeImageURL: String? = null
) {
    override fun toString(): String {
        return "Image(id=${id}, previewURL=${previewURL}, largeImageURL=${largeImageURL})"
    }
}