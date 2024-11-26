package com.example.initi_test_project.di

import com.example.domain.repositories.InternetRepository
import com.example.domain.repositories.LocalRepository
import com.example.domain.usecases.CleanSearchHistoryUseCase
import com.example.domain.usecases.GetDataForInetListUseCase
import com.example.domain.usecases.GetSearchHistoryUseCase
import com.example.domain.usecases.SearchByCompanyNameUseCase
import com.example.domain.usecases.SearchByIPUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {
    @Provides
    fun providesSearchByIPUseCase(
        internetRepository: InternetRepository,
        localRepository: LocalRepository,
    ): SearchByIPUseCase = SearchByIPUseCase(internetRepository, localRepository)

    @Provides
    fun providesSearchByCompanyNameUseCase(
        internetRepository: InternetRepository,
        localRepository: LocalRepository,
    ): SearchByCompanyNameUseCase = SearchByCompanyNameUseCase(internetRepository, localRepository)

    @Provides
    fun providesGetSearchHistoryUseCase(localRepository: LocalRepository): GetSearchHistoryUseCase =
        GetSearchHistoryUseCase(localRepository)

    @Provides
    fun providesCleanSearchHistoryUseCase(localRepository: LocalRepository): CleanSearchHistoryUseCase =
        CleanSearchHistoryUseCase(localRepository)

    @Provides
    fun providesGetDataForInetListUseCase(localRepository: LocalRepository): GetDataForInetListUseCase =
        GetDataForInetListUseCase(localRepository)
}
