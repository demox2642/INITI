package com.example.domain.repositories

import com.example.domain.models.Inet
import com.example.domain.models.Organization

interface LocalRepository {
    suspend fun searchIp(ip: String): Inet?

    suspend fun searchByOrganization(orgName: String): List<Organization>

    suspend fun getSearchHistory(): List<String>

    suspend fun cleanSearchHistory()

    suspend fun getOrgByID(orgId: String): Organization?

    suspend fun getInetListByOrgId(orgId: String): List<Inet>
}
