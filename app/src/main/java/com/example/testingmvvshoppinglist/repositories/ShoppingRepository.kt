package com.example.testingmvvshoppinglist.repositories

import androidx.lifecycle.LiveData
import com.example.testingmvvshoppinglist.data.local.ShoppingItem
import com.example.testingmvvshoppinglist.data.remote.responses.ImageResponse
import com.example.testingmvvshoppinglist.other.Resource

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    suspend fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>

    fun observerTotalPrice(): LiveData<Float>

    suspend fun searchForImage(searchQuery: String): Resource<ImageResponse>
}
