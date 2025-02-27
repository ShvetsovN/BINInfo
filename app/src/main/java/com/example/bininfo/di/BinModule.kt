package com.example.bininfo.di

import android.content.Context
import androidx.room.Room
import com.example.bininfo.Constants
import com.example.bininfo.data.AppDatabase
import com.example.bininfo.data.BinApiService
import com.example.bininfo.data.BinDao
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BinModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "database-cards"
        ).build()

    @Provides
    @Singleton
    fun provideBinDao(appDatabase: AppDatabase): BinDao = appDatabase.binDao()

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val contentType = Constants.CONTENT_TYPE.toMediaType()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .client(okHttpClient)
            .build()
        return retrofit
    }

    @Provides
    @Singleton
    fun provideBinApiService(retrofit: Retrofit): BinApiService {
        return retrofit.create(BinApiService::class.java)
    }
}
