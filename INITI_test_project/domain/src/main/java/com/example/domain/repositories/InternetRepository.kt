package com.example.domain.repositories

import com.example.domain.models.Inet
import com.example.domain.models.Organization
import com.example.domain.models.PresentationModel

interface InternetRepository {
    suspend fun searchIp(ip: String): PresentationModel<Inet>

    suspend fun searchByOrganization(orgName: String): PresentationModel<List<Organization>>

    suspend fun getInetList(orgId: String): PresentationModel<List<Inet>>
}
