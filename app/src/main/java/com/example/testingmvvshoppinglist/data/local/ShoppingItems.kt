package com.example.testingmvvshoppinglist.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("shopping_items")
data class ShoppingItems(
    var name: String,
    var amount: Int,
    var price: Float,
    var image: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)