package com.example.initi_test_project.di

import com.example.data.database.LocalDatabaseRipe
import com.example.data.repositories.InternetRepositoryImpl
import com.example.data.repositories.LocalRepositoryImpl
import com.example.data.service.RestDBRipeService
import com.example.domain.repositories.InternetRepository
import com.example.domain.repositories.LocalRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    fun provideUrl(): String = "https://rest.db.ripe.net/"

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient
            .Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        baseUrl: String,
        client: OkHttpClient,
    ): Retrofit.Builder {
        val gson =
            GsonBuilder()
                .setLenient()
                .create()

        return Retrofit
            .Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideRestDBRipeService(retrofit: Retrofit.Builder): RestDBRipeService =
        retrofit
            .build()
            .create(RestDBRipeService::class.java)

    @Singleton
    @Provides
    fun provideSearchRepository(
        service: RestDBRipeService,
        databaseRipe: LocalDatabaseRipe,
    ): InternetRepository = InternetRepositoryImpl(service, databaseRipe)

    @Singleton
    @Provides
    fun provideLocalRepository(databaseRipe: LocalDatabaseRipe): LocalRepository = LocalRepositoryImpl(databaseRipe)
}
