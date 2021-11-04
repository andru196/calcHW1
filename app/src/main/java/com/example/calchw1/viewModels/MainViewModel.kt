package com.example.calchw1.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class MainViewModel: ViewModel() {

    private var _expession: String = ""
    private var _lastNumber: Double = .0

    private val _expressionState = MutableLiveData<String>()
    private val _stateResult = MutableLiveData<String>()
    val expressionState: LiveData<String> = _expressionState
    val resultState: LiveData<String> = _stateResult

    val sightsStack = Stack<Int>()

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


    fun getResult() {
        var prev = 0
        var rez = .0
        if (sightsStack.isNotEmpty())
        {
            val nums = _expession.split("/","-","+","*").filter { it.isNotEmpty() }.map { it.toDouble() }
            var i = 0
            while (i < nums.size - 1) {
                if (i == 0)
                    rez = nums[i]
                rez = when (_expession[sightsStack[i]]) {
                    '*' -> rez * nums[i + 1]
                    '-' -> rez - nums[i + 1]
                    '+' -> rez + nums[i + 1]
                    else -> rez / nums[i + 1]
                }
                i++;
            }
        }
        else
            rez = _expession.toDouble()
        _stateResult.value = rez.toString()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(MainViewModel::javaClass.name, "onCleared")
    }
}