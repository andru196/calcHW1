package com.example.calchw1.data.db.history

import androidx.room.*

@Dao
interface HistoryItemDao {
    @Insert
    suspend fun insert(historyItemEntity: HistoryItemEntity)

    @Delete
    suspend fun delete(historyItemEntity: HistoryItemEntity)

    @Update
    suspend fun update(historyItemEntity: HistoryItemEntity)

    @Query("SELECT * FROM history_item_entity")
    suspend fun getAll(): List<HistoryItemEntity>
}