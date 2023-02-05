package com.example.testingmvvshoppinglist.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testingmvvshoppinglist.data.local.ShoppingItem
import com.example.testingmvvshoppinglist.data.remote.responses.ImageResponse
import com.example.testingmvvshoppinglist.other.Resource

/**
 * ... Why we are writing repository class in test folder not in android test because our view model
 * ... does not require any android component
 */

/**
 * ... We are writing fake repository class that actually simulates the behaviour of our real repository
 * ... Why we are doing this because we want that our test cases really quick. we don't want to hit the api and wait.
 */

class FakeShoppingRepository : ShoppingRepository {

    private val shoppingItems = mutableListOf<ShoppingItem>()

    private val observableShoppingItem = MutableLiveData<List<ShoppingItem>>(shoppingItems)
    private val observableTotalPrice = MutableLiveData<Float>()

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    private fun refreshLiveData() {
        observableShoppingItem.postValue(shoppingItems)
        observableTotalPrice.postValue(getTotalPrice())
    }

    private fun getTotalPrice(): Float {
        return shoppingItems.sumOf { it.price.toDouble() }.toFloat()
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.add(shoppingItem)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.remove(shoppingItem)
        refreshLiveData()
    }

    override suspend fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return observableShoppingItem
    }

    override fun observerTotalPrice(): LiveData<Float> {
        return observableTotalPrice
    }

    override suspend fun searchForImage(searchQuery: String): Resource<ImageResponse> {
        return if (shouldReturnNetworkError) {
            Resource.error("Error", null)
        } else {
            Resource.success(ImageResponse(listOf(), 0, 0))
        }
    }
}
