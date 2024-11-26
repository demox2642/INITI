package com.example.data.service

import com.example.data.models.ServerResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestDBRipeService {
    @GET("search.json")
    suspend fun searchByIp(
        @Query("source") source: String = "ripe",
        @Query("query-string") queryString: String,
        @Query("type-filter") typeFilter: String = "inetnum",
        @Query("flags") flags: String = "no-referenced",
    ): ServerResponse

    @GET("search.json")
    suspend fun searchByOrganization(
        @Query("source") source: String = "ripe",
        @Query("query-string") queryString: String,
        @Query("type-filter") typeFilter: String = "organisation",
        @Query("flags") flags: String = "no-referenced",
    ): ServerResponse

    @GET("search.json")
    suspend fun searchByOrganizationId(
        @Query("inverse-attribute") inverseAttribute: String = "org",
        @Query("source") source: String = "ripe",
        @Query("query-string") queryString: String,
        @Query("type-filter") typeFilter: String = "inetnum",
        @Query("flags") flags: String = "no-referenced",
    ): ServerResponse

    @GET("https://rest.db.ripe.net/ripe/organisation/{id}.json")
    suspend fun getByOrganizationId(
        @Path("id") id: String,
    ): ServerResponse
}
