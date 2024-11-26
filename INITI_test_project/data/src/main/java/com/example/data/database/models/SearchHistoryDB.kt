package com.example.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.database.contracts.SearchHistoryContracts

@Entity(
    tableName = SearchHistoryContracts.TABLE_NAME,
)
data class SearchHistoryDB(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = SearchHistoryContracts.Colums.TEXT)
    val searchText: String,
)
