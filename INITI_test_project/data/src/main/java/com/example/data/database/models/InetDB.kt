package com.example.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.database.contracts.InetContracts

@Entity(
    tableName = InetContracts.TABLE_NAME,
)
data class InetDB(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = InetContracts.Colums.ID)
    val id: Long = 0L,
    @ColumnInfo(name = InetContracts.Colums.NUM)
    val num: String,
    @ColumnInfo(name = InetContracts.Colums.NAME)
    val name: String,
    @ColumnInfo(name = InetContracts.Colums.DESCRIPTION)
    val description: String,
    @ColumnInfo(name = InetContracts.Colums.COUNTRY)
    val country: String?,
    @ColumnInfo(name = InetContracts.Colums.ORG_ID)
    val orgId: String,
    @ColumnInfo(name = InetContracts.Colums.IP_MASK)
    val ipMask: String,
    @ColumnInfo(name = InetContracts.Colums.IP_MIN)
    val ipMin: Int,
    @ColumnInfo(name = InetContracts.Colums.IP_MAX)
    val ipMax: Int,
)
