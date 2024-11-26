package com.example.domain.usecases

import com.example.domain.models.Inet
import com.example.domain.models.PresentationModel
import com.example.domain.models.ScreenState
import com.example.domain.repositories.InternetRepository
import com.example.domain.repositories.LocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SearchByIPUseCase(
    private val internetRepository: InternetRepository,
    private val localRepository: LocalRepository,
) {
    suspend fun searchIp(ip: String): Flow<PresentationModel<Inet>> =
        flow {
            var searchResult = PresentationModel<Inet>(screenState = ScreenState.LOADING)
            val localSearch = localRepository.searchIp(ip)
            searchResult =
                if (localSearch == null) {
                    internetRepository.searchIp(ip)
                } else {
                    PresentationModel<Inet>(data = localSearch, screenState = ScreenState.RESULT)
                }

            emit(searchResult)
        }.flowOn(Dispatchers.IO)

    suspend fun searchIpFromDB(ip: String): Flow<PresentationModel<Inet>> =
        flow {
            var searchResult = PresentationModel<Inet>(screenState = ScreenState.LOADING)
            val localSearch = localRepository.searchIp(ip)
            searchResult =
                if (localSearch == null) {
                    PresentationModel<Inet>(errorText = "IP $ip не найден. Проверьте интернет соединение и корректность данных.", screenState = ScreenState.RESULT)
                } else {
                    PresentationModel<Inet>(data = localSearch, screenState = ScreenState.RESULT)
                }

            emit(searchResult)
        }.flowOn(Dispatchers.IO)
}
