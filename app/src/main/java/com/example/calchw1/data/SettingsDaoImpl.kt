package com.example.calchw1.data

import android.content.SharedPreferences
import com.example.calchw1.domain.SettingsDao
import com.example.calchw1.domain.entity.ResultPanelType
import com.example.calchw1.domain.entity.SettingsBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SettingsDaoImpl(
    private val preferences: SharedPreferences
) : SettingsDao {
    override suspend fun getResultPanelType(): ResultPanelType = withContext(Dispatchers.IO) {
        preferences.getString(RESULT_PANEL_TYPE_KEY, null)
            ?.let { ResultPanelType.valueOf(it) } ?: ResultPanelType.LEFT
    }

    override suspend fun setResultPanelType(resultPanelType: ResultPanelType) =
        withContext(Dispatchers.IO) {
            preferences.edit().putString(RESULT_PANEL_TYPE_KEY, resultPanelType.name).apply()
        }

    override suspend fun getPrecision(): Int = withContext(Dispatchers.IO)
        { preferences.getInt(PRECISION_KEY, 0) }

    override suspend fun setPrecision(precision: Int) =
        withContext(Dispatchers.IO) {
            preferences.edit().putInt(PRECISION_KEY, precision).apply()
        }

    override suspend fun getVibration(): Int = withContext(Dispatchers.IO)
    { preferences.getInt(VIBRATION_KEY, 1) }

    override suspend fun setVibration(vibration: Int) =
        withContext(Dispatchers.IO) {
            preferences.edit().putInt(VIBRATION_KEY, vibration).apply()
        }

    override suspend fun getSettings(): SettingsBody = withContext(Dispatchers.IO)
    {
        SettingsBody(getResultPanelType(),
        getVibration(),
        getPrecision())
    }

    companion object {
        private const val RESULT_PANEL_TYPE_KEY = "RESULT_PANEL_TYPE_KEY"
        private const val VIBRATION_KEY = "VIBRATION_KEY"
        private const val PRECISION_KEY = "PRECISION_KEY"
    }
}