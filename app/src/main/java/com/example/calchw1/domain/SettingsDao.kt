package com.example.calchw1.domain

import com.example.calchw1.domain.entity.ResultPanelType
import com.example.calchw1.domain.entity.SettingsBody

interface SettingsDao {

    suspend fun getResultPanelType() : ResultPanelType
    suspend fun setResultPanelType(resultPanelType: ResultPanelType)

    suspend fun getPrecision() : Int
    suspend fun setPrecision(precision:Int)

    suspend fun getVibration() : Int
    suspend fun setVibration(vibration:Int)

    suspend fun getSettings() : SettingsBody
}