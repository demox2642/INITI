package com.example.data.repositories

import com.example.data.database.LocalDatabaseRipe
import com.example.data.database.models.SearchHistoryDB
import com.example.data.models.toInet
import com.example.data.models.toOrganization
import com.example.domain.models.Inet
import com.example.domain.models.Organization
import com.example.domain.repositories.LocalRepository
import javax.inject.Inject

class LocalRepositoryImpl
    @Inject
    constructor(
        private val databaseRipe: LocalDatabaseRipe,
    ) : LocalRepository {
        override suspend fun searchIp(ip: String): Inet? {
            val parseIp = ip.replace(" ", "").split(".")
            val searchResult = databaseRipe.inetDao().getInet(mask = "${parseIp[0]}.${parseIp[1]}", lastMaskIndex = parseIp[2].toInt())
            if (searchResult != null) {
                databaseRipe.searchHistoryDao().addHistoryItem(SearchHistoryDB(searchText = ip))
            }
            return searchResult?.toInet()
        }

        override suspend fun searchByOrganization(orgName: String): List<Organization> {
            val searchResult = databaseRipe.organizationDao().getOrganizationList(orgName)
            if (searchResult.isNotEmpty()) {
                databaseRipe.searchHistoryDao().addHistoryItem(SearchHistoryDB(searchText = orgName))
            }
            return searchResult.map { it.toOrganization() }
        }

        override suspend fun getSearchHistory(): List<String> = databaseRipe.searchHistoryDao().getHistory().map { it.searchText }

        override suspend fun cleanSearchHistory() = databaseRipe.searchHistoryDao().deleteSearchHistory()

        override suspend fun getOrgByID(orgId: String): Organization? =
            databaseRipe.organizationDao().getOrganizationByID(orgId)?.toOrganization()

        override suspend fun getInetListByOrgId(orgId: String): List<Inet> =
            databaseRipe.inetDao().getInetListByOrgId(orgId).map { it.toInet() }
    }
