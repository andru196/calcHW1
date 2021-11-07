package com.example.calchw1.domain

import com.example.calchw1.domain.entity.HistoryItem

interface HistoryRepository {
    suspend fun add(historyItem: HistoryItem)

    suspend fun getAll(): List<HistoryItem>
}