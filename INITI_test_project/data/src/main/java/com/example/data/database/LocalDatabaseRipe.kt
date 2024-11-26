package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.database.dao.InetDAO
import com.example.data.database.dao.OrganizationDAO
import com.example.data.database.dao.SearchHistoryDAO
import com.example.data.database.models.InetDB
import com.example.data.database.models.OrganizationDB
import com.example.data.database.models.SearchHistoryDB

@Database(
    entities = [

        InetDB::class,
        OrganizationDB::class,
        SearchHistoryDB::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class LocalDatabaseRipe : RoomDatabase() {
    abstract fun inetDao(): InetDAO

    abstract fun organizationDao(): OrganizationDAO

    abstract fun searchHistoryDao(): SearchHistoryDAO
}
