package com.example.calchw1.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calchw1.domain.CalculationExecutor
import com.example.calchw1.domain.HistoryRepository
import com.example.calchw1.domain.SettingsDao
import com.example.calchw1.domain.entity.HistoryItem
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(
    private val settingsDao: SettingsDao,
    private val historyRepository: HistoryRepository
): ViewModel() {

    private var _expession: String = ""
    private var _lastNumber: Double = .0

    private val _expressionState = MutableLiveData<String>()
    private val _stateResult = MutableLiveData<String>()
    val expressionState: LiveData<String> = _expressionState
    val resultState: LiveData<String> = _stateResult

    var sightsStack = Stack<Int>()

    private val _resultPanelState = MutableLiveData<ResultPanelType>(ResultPanelType.LEFT)
    val resultPanelState: LiveData<ResultPanelType> = _resultPanelState

    init {
        viewModelScope.launch {
            _resultPanelState.value = settingsDao.getResultPanelType()
        }
    }

    fun onNumberClick(num:Int) {
        _expession += num.toString()
        _expressionState.value = _expession
    }

    fun onSightClick(chr:Char) {
        if (chr != '.') {
            if (sightsStack.isNotEmpty())
                getResult()
            if (sightsStack.firstOrNull() != _expession.length)
                sightsStack.push(_expession.length)
            else
                _expession = _expession.dropLast(1)
        } else if (_expession.lastOrNull() == '.')
            _expession = _expession.dropLast(1)
        _expession += chr.toString()
        _expressionState.value = _expession
    }

    private fun putInSection(expression: String, put: String, section: Int) : String {
        return expression.dropLast(expression.length - section) + put + expression.drop(section)
    }


    fun onBackSpace() {
        if (_expession.isNotEmpty()) {
            _expession = _expession.dropLast(1)
            _expressionState.value = _expession
            if (_expession.length == sightsStack.firstOrNull())
            {
                sightsStack.pop()
            }
        }
    }

    fun onClearButton() {
        if (_expession.isNotEmpty()) {
            _expession = ""
            _expressionState.value = _expession
            sightsStack.clear()
        }
    }

    fun getResult() : String {
        val res = CalculationExecutor.computate(_expession, sightsStack)
        _stateResult.value = res
        return res
    }



    override fun onCleared() {
        super.onCleared()
        Log.d(MainViewModel::javaClass.name, "onCleared")
    }

    fun onStart() {
        viewModelScope.launch {
            _resultPanelState.value = settingsDao.getResultPanelType()
        }
    }

    fun onEqualClicked() {
        val result = getResult()
        viewModelScope.launch {
            historyRepository.add(HistoryItem(_expession, result))
        }
    }

    fun onHistoryResult(item: HistoryItem?) {
        item?.let {
            _expession = it.expression
            _expressionState.value = it.expression
            sightsStack.clear()
            sightsStack = CalculationExecutor.getSightsStack(it.expression)
            _stateResult.value = it.result
        }
    }
}