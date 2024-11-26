package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.contracts.OrganizationContracts
import com.example.data.database.models.OrganizationDB

@Dao
interface OrganizationDAO {
    @Query("SELECT * FROM ${OrganizationContracts.TABLE_NAME} WHERE ${OrganizationContracts.Colums.NAME} =:name ")
    fun getOrganization(name: String): OrganizationDB?

    @Query(
        "SELECT * FROM ${OrganizationContracts.TABLE_NAME} WHERE UPPER(${OrganizationContracts.Colums.NAME}) LIKE '%' || UPPER(:name) || '%' ",
    )
    fun getOrganizationList(name: String): List<OrganizationDB>

    @Query("SELECT * FROM ${OrganizationContracts.TABLE_NAME} WHERE ${OrganizationContracts.Colums.ID} = :id ")
    fun getOrganizationByID(id: String): OrganizationDB?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAllOrganization(newOrgList: List<OrganizationDB>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addOrganization(newOrg: OrganizationDB)

    @Query("DELETE FROM ${OrganizationContracts.TABLE_NAME}")
    fun deleteOrgTable()
}
