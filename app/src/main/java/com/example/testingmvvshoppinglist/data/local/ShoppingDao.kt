package com.example.testingmvvshoppinglist.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(shoppingItem: ShoppingItem)

    @Delete
    suspend fun delete(shoppingItem: ShoppingItem)

    @Query("SELECT * FROM shopping_items")
    fun observerAllShoppingItems(): LiveData<List<ShoppingItem>>

    @Query("SELECT SUM(amount * price) FROM shopping_items")
    fun observeTotalPrice(): LiveData<Float>
}