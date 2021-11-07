package com.example.calchw1.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calchw1.domain.SettingsDao
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsDao: SettingsDao
) : ViewModel() {


    private val _resultPanelState = MutableLiveData<ResultPanelType>(ResultPanelType.LEFT)
    val resultPanelState: LiveData<ResultPanelType> = _resultPanelState

    private val _openResultPanelAction = MutableLiveData<ResultPanelType>()
    val openResultPanelAction = _openResultPanelAction

    init {
        viewModelScope.launch {
            _resultPanelState.value = settingsDao.getResultPanelType()
        }
    }

    fun onResultPanelTypeChanged(resultPanelType: ResultPanelType) {
        _resultPanelState.value = resultPanelType
        viewModelScope.launch {
            settingsDao.setResultPanelType(resultPanelType)
        }
    }

    fun onResultPanelTypeClicked() {
        _openResultPanelAction.value = _resultPanelState.value
    }
}

enum class ResultPanelType {
    LEFT, RIGHT, HIDE
}