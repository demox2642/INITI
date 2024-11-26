package com.example.data.repositories

import androidx.room.withTransaction
import com.example.data.database.LocalDatabaseRipe
import com.example.data.database.models.SearchHistoryDB
import com.example.data.models.toErrorText
import com.example.data.models.toInet
import com.example.data.models.toInetDB
import com.example.data.models.toInetList
import com.example.data.models.toOrganization
import com.example.data.models.toOrganizationDB
import com.example.data.service.RestDBRipeService
import com.example.domain.models.Inet
import com.example.domain.models.Organization
import com.example.domain.models.PresentationModel
import com.example.domain.models.ScreenState
import com.example.domain.repositories.InternetRepository
import retrofit2.HttpException
import javax.inject.Inject

class InternetRepositoryImpl
    @Inject
    constructor(
        private val service: RestDBRipeService,
        private val databaseRipe: LocalDatabaseRipe,
    ) : InternetRepository {
        override suspend fun searchIp(ip: String): PresentationModel<Inet> =
            try {
                val response = service.searchByIp(queryString = ip)
                response.toInetList().forEach { inet ->
                    val responseOrg = service.getByOrganizationId(inet.orgId).toOrganization()
                    saveOrganizations(responseOrg)
                }
                getInetList(response.toInet().orgId)
                saveInetToDataBase(response.toInet())
                saveSearchHistory(ip)
                PresentationModel<Inet>(screenState = ScreenState.RESULT, data = response.toInet())
            } catch (e: HttpException) {
                PresentationModel<Inet>(
                    screenState = ScreenState.ERROR,
                    errorText = e.toErrorText(ip),
                )
            }

        override suspend fun searchByOrganization(orgName: String): PresentationModel<List<Organization>> =

            try {
                val response = service.searchByOrganization(queryString = orgName)
                saveOrganizations(response.toOrganization())
                response.toOrganization().forEach { org ->
                    getInetList(org.orgId)
                }
                saveSearchHistory(orgName)
                PresentationModel<List<Organization>>(
                    screenState = ScreenState.RESULT,
                    data = response.toOrganization(),
                )
            } catch (e: HttpException) {
                PresentationModel<List<Organization>>(
                    screenState = ScreenState.ERROR,
                    errorText = e.toErrorText(orgName),
                )
            }

        override suspend fun getInetList(orgId: String): PresentationModel<List<Inet>> =
            try {
                val response = service.searchByOrganizationId(queryString = orgId)
                val orgResponse = service.getByOrganizationId(orgId).toOrganization()
                response.toInetList().forEach {
                    saveInetToDataBase(it)
                }
                saveOrganizations(orgResponse)

                PresentationModel<List<Inet>>(screenState = ScreenState.RESULT, data = response.toInetList())
            } catch (e: HttpException) {
                PresentationModel<List<Inet>>(
                    screenState = ScreenState.ERROR,
                    errorText = e.toErrorText(orgId),
                )
            }

        private suspend fun saveInetToDataBase(inet: Inet) {
            databaseRipe.withTransaction {
                databaseRipe.inetDao().addInet(newInet = inet.toInetDB())
            }
        }

        private suspend fun saveOrganizations(orgs: List<Organization>) {
            databaseRipe.withTransaction {
                databaseRipe.organizationDao().addAllOrganization(
                    newOrgList = orgs.map { it.toOrganizationDB() },
                )
            }
        }

        private suspend fun saveSearchHistory(searchText: String) {
            databaseRipe.withTransaction {
                databaseRipe.searchHistoryDao().addHistoryItem(searchText = SearchHistoryDB(searchText = searchText))
            }
        }
    }
