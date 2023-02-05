package com.example.testingmvvshoppinglist.repositories

import androidx.lifecycle.LiveData
import com.example.testingmvvshoppinglist.data.local.ShoppingDao
import com.example.testingmvvshoppinglist.data.local.ShoppingItem
import com.example.testingmvvshoppinglist.data.remote.PixaBayApi
import com.example.testingmvvshoppinglist.data.remote.responses.ImageResponse
import com.example.testingmvvshoppinglist.other.Resource
import javax.inject.Inject

class DefaultShoppingRepository @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val pixaBayApi: PixaBayApi
) : ShoppingRepository {
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insert(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.delete(shoppingItem)
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return shoppingDao.observerAllShoppingItems()
    }

    override fun observerTotalPrice(): LiveData<Float> {
        return shoppingDao.observeTotalPrice()
    }

    override suspend fun searchForImage(searchQuery: String): Resource<ImageResponse> {
        return try {
            val response = pixaBayApi.searchForImage(searchQuery)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An unknown error occurred", null)
            } else {
                Resource.error("An unknown error occurred", null)
            }
        } catch (e: Exception) {
            Resource.error("Could not reach the server. Check your internet connection", null)
        }
    }
}
