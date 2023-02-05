package com.example.testingmvvshoppinglist.di

import android.content.Context
import android.database.DefaultDatabaseErrorHandler
import androidx.room.Room
import com.example.testingmvvshoppinglist.data.local.ShoppingDao
import com.example.testingmvvshoppinglist.data.local.ShoppingDatabase
import com.example.testingmvvshoppinglist.data.remote.PixaBayApi
import com.example.testingmvvshoppinglist.other.Constants.BASE_URL
import com.example.testingmvvshoppinglist.other.Constants.DATABASE_NAME
import com.example.testingmvvshoppinglist.repositories.DefaultShoppingRepository
import com.example.testingmvvshoppinglist.repositories.ShoppingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        ShoppingDatabase::class.java,
        DATABASE_NAME
    ).build()

    /**
     * ... We don't need to provide repository because dagger automatically creates repository instance because we
     * ... provided dao and api instances but in view model we are injecting shopping repo which is an interface so that
     * ... we have to provide repo
     */

    @Singleton
    @Provides
    fun provideDefaultShoppingRepository(
        dao: ShoppingDao,
        api: PixaBayApi
    ) = DefaultShoppingRepository(dao, api) as ShoppingRepository

    @Singleton
    @Provides
    fun provideShoppingDao(
        database: ShoppingDatabase
    ) = database.shoppingDao()

    @Singleton
    @Provides
    fun providePixaBayApi(): PixaBayApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PixaBayApi::class.java)
    }

}
