package com.example.domain.usecases

import com.example.domain.repositories.LocalRepository

class CleanSearchHistoryUseCase(
    private val localRepository: LocalRepository,
) {
    suspend fun cleanSearchHistory() = localRepository.cleanSearchHistory()
}
