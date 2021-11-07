package com.example.calchw1.di

import android.content.Context
import com.example.calchw1.data.SettingsDaoImpl
import com.example.calchw1.domain.SettingsDao

object SettingsDaoProvider {
    private var dao: SettingsDao? = null
    fun getDao(context: Context): SettingsDao {
        return  dao ?:
            SettingsDaoImpl(context.getSharedPreferences("settings", Context.MODE_PRIVATE))
            .also { dao = it }
    }
}