package com.example.calchw1.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calchw1.domain.SettingsDao
import com.example.calchw1.domain.entity.ResultPanelType
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsDao: SettingsDao
) : ViewModel() {


    private val _resultPanelState = MutableLiveData<ResultPanelType>(ResultPanelType.LEFT)
    val resultPanelState: LiveData<ResultPanelType> = _resultPanelState

    private val _openResultPanelAction = MutableLiveData<ResultPanelType>()
    val openResultPanelAction = _openResultPanelAction

    private val _precisionResult = MutableLiveData<Int>()
    val precisionResult = _precisionResult

    private val _vibrationIntesiv = MutableLiveData<Int>()
    val vibrationIntesiv = _vibrationIntesiv

    init {
        viewModelScope.launch {
            with (settingsDao.getSettings()){
                _resultPanelState.value = resultPanelType
                _precisionResult.value = precision
                _vibrationIntesiv.value = vibrationIntesivity
            }
        }
    }

    fun onResultPanelTypeChanged(resultPanelType: ResultPanelType) {
        _resultPanelState.value = resultPanelType
        viewModelScope.launch {
            settingsDao.setResultPanelType(resultPanelType)
        }
    }

    fun onPrecisionChanged(precision: Int) {
        _precisionResult.value = precision
        viewModelScope.launch {
            settingsDao.setPrecision(precision)
        }
    }

    fun onVibrationChanged(vibration: Int) {
        _vibrationIntesiv.value = vibration
        viewModelScope.launch {
            settingsDao.setVibration(vibration)
        }
    }

    fun onResultPanelTypeClicked() {
        _openResultPanelAction.value = _resultPanelState.value
    }
}

