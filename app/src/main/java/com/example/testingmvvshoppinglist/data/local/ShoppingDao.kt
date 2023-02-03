package com.example.testingmvvshoppinglist.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(shoppingItems: ShoppingItems)

    @Delete
    suspend fun delete(shoppingItems: ShoppingItems)

    @Query("SELECT * FROM shopping_items")
    fun observerAllShoppingItems(): LiveData<List<ShoppingItems>>

    @Query("SELECT SUM(amount * price) FROM shopping_items")
    fun observeTotalPrice(): LiveData<Float>
}