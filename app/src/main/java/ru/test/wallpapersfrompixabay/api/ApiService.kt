package ru.test.wallpapersfrompixabay.api

import retrofit2.http.GET
import retrofit2.http.QueryMap
import ru.test.wallpapersfrompixabay.models.ImageHits

interface ApiService {
    @GET("api/?key=9253806-7d4c68d50f15b93f570fbed7b")
    suspend fun getImages(
        @QueryMap options: Map<String, String>
    ): ImageHits

//    @GET("api/?key=9253806-7d4c68d50f15b93f570fbed7b")
//    suspend fun getImagesByCategory(
//        @Query("category") category: String
//    ): ImageHits
//
//    @GET("api/?key=9253806-7d4c68d50f15b93f570fbed7b")
//    suspend fun getImagesByQuery(
//        @Query("q") q: String
//    ): ImageHits

}