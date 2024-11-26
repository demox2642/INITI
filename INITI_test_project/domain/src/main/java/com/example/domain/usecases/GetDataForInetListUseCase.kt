package com.example.domain.usecases

import com.example.domain.models.Inet
import com.example.domain.models.Organization
import com.example.domain.models.OrganizationInetList
import com.example.domain.models.PresentationModel
import com.example.domain.models.ScreenState
import com.example.domain.repositories.LocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetDataForInetListUseCase(
    private val localRepository: LocalRepository,
) {

    suspend fun getDataForInetList(orgId: String):Flow<PresentationModel<OrganizationInetList>> =
        flow {
            var presentationModel = PresentationModel<OrganizationInetList>(screenState = ScreenState.LOADING)
            val organization = localRepository.getOrgByID(orgId)
            val inetList = localRepository.getInetListByOrgId(orgId)
            if (organization == null){
                presentationModel =
                    PresentationModel<OrganizationInetList>(
                        errorText = "Организации с id=$orgId не найдена. Проверьте интернет соединение и корректность данных.",
                        screenState = ScreenState.ERROR,
                    )
            } else{
                if (inetList == null){
                    presentationModel =
                        PresentationModel<OrganizationInetList>(
                            errorText = "Организации с id=$orgId не найдена. Проверьте интернет соединение и корректность данных.",
                            screenState = ScreenState.ERROR,
                        )
                }else{
                    presentationModel =
                        PresentationModel<OrganizationInetList>(
                            data = OrganizationInetList(organization, inetList),
                            screenState = ScreenState.RESULT,
                        )
                }
            }

                emit(presentationModel)
        }.flowOn(Dispatchers.IO)

}
