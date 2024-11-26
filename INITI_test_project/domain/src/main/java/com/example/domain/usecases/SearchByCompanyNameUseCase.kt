package com.example.domain.usecases

import com.example.domain.models.Organization
import com.example.domain.models.PresentationModel
import com.example.domain.models.ScreenState
import com.example.domain.repositories.InternetRepository
import com.example.domain.repositories.LocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SearchByCompanyNameUseCase(
    private val internetRepository: InternetRepository,
    private val localRepository: LocalRepository,
) {
    suspend fun searchByOrganization(orgName: String) =
        flow {
            var searchResult = PresentationModel<List<Organization>>(screenState = ScreenState.LOADING)
            val localSearch = localRepository.searchByOrganization(orgName)

            searchResult =
                if (localSearch.isEmpty()) {
                    internetRepository.searchByOrganization(orgName)
                } else {
                    PresentationModel<List<Organization>>(data = localSearch, screenState = ScreenState.RESULT)
                }

            emit(searchResult)
        }.flowOn(Dispatchers.IO)

    suspend fun searchByOrganizationFromDB(orgName: String) =
        flow {
            var searchResult = PresentationModel<List<Organization>>(screenState = ScreenState.LOADING)
            val localSearch = localRepository.searchByOrganization(orgName)

            searchResult =
                if (localSearch.isEmpty()) {
                    PresentationModel<List<Organization>>(
                        errorText = "Организации с названием=$orgName не найдена. Проверьте интернет соединение и корректность данных.",
                        screenState = ScreenState.ERROR)
                } else {
                    PresentationModel<List<Organization>>(data = localSearch, screenState = ScreenState.RESULT)
                }

            emit(searchResult)
        }.flowOn(Dispatchers.IO)
}
