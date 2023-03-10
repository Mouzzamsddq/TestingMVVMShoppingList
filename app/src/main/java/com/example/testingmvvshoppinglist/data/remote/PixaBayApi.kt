package com.example.testingmvvshoppinglist.data.remote

import com.example.testingmvvshoppinglist.BuildConfig
import com.example.testingmvvshoppinglist.data.remote.responses.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixaBayApi {

    @GET("/api/")
    suspend fun searchForImage(
        @Query("q") searchQuery: String,
        @Query("key") key: String = BuildConfig.API_KEY
    ): Response<ImageResponse>
}