package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.contracts.SearchHistoryContracts
import com.example.data.database.models.SearchHistoryDB

@Dao
interface SearchHistoryDAO  {

    @Query("SELECT * FROM ${SearchHistoryContracts.TABLE_NAME} ")
    fun getHistory(): List<SearchHistoryDB>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addHistoryItem(searchText: SearchHistoryDB)

    @Query("DELETE FROM ${SearchHistoryContracts.TABLE_NAME}")
    fun deleteSearchHistory()
}
