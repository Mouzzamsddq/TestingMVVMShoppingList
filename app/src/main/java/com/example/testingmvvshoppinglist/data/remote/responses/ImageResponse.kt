package com.example.testingmvvshoppinglist.data.remote.responses

import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("hits")
    val hits: List<Hit>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("totalHits")
    val totalHits: Int
)
