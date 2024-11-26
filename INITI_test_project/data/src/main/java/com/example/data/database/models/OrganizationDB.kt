package com.example.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.database.contracts.OrganizationContracts

@Entity(
    tableName = OrganizationContracts.TABLE_NAME,
)
data class OrganizationDB(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = OrganizationContracts.Colums.ID)
    val id: String,
    @ColumnInfo(name = OrganizationContracts.Colums.NAME)
    val name: String,
    @ColumnInfo(name = OrganizationContracts.Colums.COUNTRY)
    val country: String,
    @ColumnInfo(name = OrganizationContracts.Colums.ADDRESS)
    val address: String?,
)
