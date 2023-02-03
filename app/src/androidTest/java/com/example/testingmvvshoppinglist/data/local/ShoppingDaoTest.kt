package com.example.testingmvvshoppinglist.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.testingmvvshoppinglist.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var shoppingDatabase: ShoppingDatabase
    private lateinit var shoppingDao: ShoppingDao


    @Before
    fun setup() {
        shoppingDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingDatabase::class.java
        ).allowMainThreadQueries()
            .build()
        shoppingDao = shoppingDatabase.shoppingDao()
    }

    @After
    fun tearDown() {
        shoppingDatabase.close()
    }

    /**
     * ... We use runTest to run the test cases on main train and at the
     * ... same time execute the suspend method.
     */

    @Test
    fun insertShoppingItem() = runTest {
        val shoppingItem = ShoppingItems(
            name = "name",
            amount = 1,
            price = 1f,
            image = "url",
            id = 1
        )
        shoppingDao.insert(shoppingItem)
        /**
         * ...We are using one class provided by google to get the live data synchronously because live
         * ... data is working asynchronously and we don't want that live data
         */
        val allShoppingItem = shoppingDao.observerAllShoppingItems().getOrAwaitValue()
        assertThat(allShoppingItem).contains(shoppingItem)
    }

    @Test
    fun deleteShoppingItem() = runTest {
        val shoppingItem = ShoppingItems(
            name = "name",
            amount = 1,
            price = 1f,
            image = "url",
            id = 1
        )
        shoppingDao.delete(shoppingItem)
        val allShoppingItem = shoppingDao.observerAllShoppingItems().getOrAwaitValue()
        assertThat(allShoppingItem).doesNotContain(shoppingItem)
    }

    @Test
    fun observerTotalPriceSum() = runTest {
        val shoppingItem1 = ShoppingItems(
            name = "name",
            amount = 100,
            price = 0.5f,
            image = "url",
            id = 1
        )
        val shoppingItem2 = ShoppingItems(
            name = "name",
            amount = 3,
            price = 5.5f,
            image = "url",
            id = 2
        )
        val shoppingItem3 = ShoppingItems(
            name = "name",
            amount = 0,
            price = 100f,
            image = "url",
            id = 3
        )
        shoppingDao.insert(shoppingItem1)
        shoppingDao.insert(shoppingItem2)
        shoppingDao.insert(shoppingItem3)

        val totalAmount = shoppingDao.observeTotalPrice().getOrAwaitValue()
        assertThat(totalAmount).isEqualTo(3 * 5.5f + 100 * 0.5f)
    }
}