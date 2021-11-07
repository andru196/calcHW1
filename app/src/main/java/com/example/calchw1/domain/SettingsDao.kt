package com.example.calchw1.domain

import com.example.calchw1.viewModels.ResultPanelType

interface SettingsDao {

    suspend fun getResultPanelType() : ResultPanelType
    suspend fun setResultPanelType(resultPanelType:ResultPanelType)

}