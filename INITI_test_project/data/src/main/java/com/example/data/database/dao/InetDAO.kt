package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.contracts.InetContracts
import com.example.data.database.models.InetDB

@Dao
interface InetDAO {
    @Query(
        "SELECT * FROM ${InetContracts.TABLE_NAME} WHERE ${InetContracts.Colums.IP_MASK} =:mask AND" +
            " :lastMaskIndex BETWEEN ${InetContracts.Colums.IP_MIN} AND ${InetContracts.Colums.IP_MAX}",
    )
    fun getInet(
        mask: String,
        lastMaskIndex: Int,
    ): InetDB?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAllInet(newInetList: List<InetDB>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addInet(newInet: InetDB)

    @Query("DELETE FROM ${InetContracts.TABLE_NAME}")
    fun deleteInet()

    @Query("SELECT * FROM ${InetContracts.TABLE_NAME} WHERE ${InetContracts.Colums.ORG_ID} =:orgId")
    fun getInetListByOrgId(orgId: String): List<InetDB>
}
