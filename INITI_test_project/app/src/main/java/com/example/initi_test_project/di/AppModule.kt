package com.example.initi_test_project.di

import android.content.Context
import com.example.initi_test_project.di.ConnectionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideConnectionManager(
        @ApplicationContext appContext: Context,
    ) = ConnectionManager(appContext)
}
