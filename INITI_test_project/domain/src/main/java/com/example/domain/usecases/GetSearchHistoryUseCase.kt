package com.example.domain.usecases

import com.example.domain.repositories.LocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetSearchHistoryUseCase(
    private val localRepository: LocalRepository,
) {
    suspend fun getSearchHistory() =
        flow {
            emit(localRepository.getSearchHistory())
        }.flowOn(Dispatchers.IO)
}
