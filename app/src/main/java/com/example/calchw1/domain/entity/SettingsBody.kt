package com.example.calchw1.domain.entity

data class SettingsBody(
    val resultPanelType:ResultPanelType,
    val vibrationIntesivity:Int,
    val precision:Int
)