package com.example.calchw1.di

import android.content.Context
import com.example.calchw1.data.HistoryRepositoryImpl
import com.example.calchw1.domain.HistoryRepository

object HistoryRepositoryProvider {
    private var repository: HistoryRepository? = null

    fun get(context: Context): HistoryRepository =
          repository?: HistoryRepositoryImpl(DatabaseProvider.get(context).historyItemDao)
            .also { repository = it }

}