package ru.test.wallpapersfrompixabay.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val BASE_URL = "https://pixabay.com/"
    private val logging = HttpLoggingInterceptor()
    private val httpClient = OkHttpClient.Builder()

    private val retrofitBuilder: Retrofit.Builder by lazy {
        // set your desired log level
        logging.apply { logging.level = HttpLoggingInterceptor.Level.BODY }
        // add your other interceptors …
        // add logging as last interceptor
        httpClient.addInterceptor(logging)

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
    }

    val apiService: ApiService by lazy {
        retrofitBuilder
            .build()
            .create(ApiService::class.java)
    }
}