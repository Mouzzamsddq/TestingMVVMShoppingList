package com.example.testingmvvshoppinglist.di

import android.content.Context
import androidx.room.Room
import com.example.testingmvvshoppinglist.data.local.ShoppingDatabase
import com.example.testingmvvshoppinglist.data.remote.responses.PixaBayApi
import com.example.testingmvvshoppinglist.other.Constants.BASE_URL
import com.example.testingmvvshoppinglist.other.Constants.DATABASE_NAME
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
