package com.example.calchw1.presentation

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.Gravity
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.calchw1.R
import com.example.calchw1.common.BaseActivity
import com.example.calchw1.databinding.MainActivityBinding
import com.example.calchw1.di.HistoryRepositoryProvider
import com.example.calchw1.di.SettingsDaoProvider
import com.example.calchw1.domain.CalculationExecutor
import com.example.calchw1.domain.entity.HistoryResult
import com.example.calchw1.domain.entity.ResultPanelType
import com.example.calchw1.viewModels.MainViewModel

class MainActivity : BaseActivity() {

    private val viewBinding by viewBinding(MainActivityBinding::bind)
    private val viewModel by viewModels<MainViewModel>{
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel((SettingsDaoProvider.getDao(this@MainActivity)),
                HistoryRepositoryProvider.get(this@MainActivity))
                        as T
            }
        }
    }

    private val resultLauncher = registerForActivityResult(HistoryResult()) {item ->
        viewModel.onHistoryResult(item)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        viewBinding.mainSettings.setOnClickListener {
            openSettings()
        }

        viewBinding.mainHistory.setOnClickListener {
            openHistory()
        }

        viewBinding.inputEt.apply {
            showSoftInputOnFocus = false
        }

        viewModel.resultPanelState.observe(this) {
            with (viewBinding.resultTv) {
                gravity = when (it) {
                    ResultPanelType.LEFT -> Gravity.START.or(Gravity.CENTER_VERTICAL)
                    ResultPanelType.RIGHT -> Gravity.END.or(Gravity.CENTER_VERTICAL)
                    ResultPanelType.HIDE -> {gravity}
                }
                isVisible = it != ResultPanelType.HIDE
            }
        }

        viewModel.precisionResult.observe(this) {
            CalculationExecutor.precition = it
            viewModel.getResult()
        }

        val numsButtons = listOf(viewBinding.mainZero,
            viewBinding.mainOne,
            viewBinding.mainTwo,
            viewBinding.mainThree,
            viewBinding.mainFour,
            viewBinding.mainFive,
            viewBinding.mainSix,
            viewBinding.mainSeven,
            viewBinding.mainEight,
            viewBinding.mainNine)
        numsButtons.forEachIndexed { index, textView ->
                textView.setOnClickListener {
                    viewModel.onNumberClick(index)
                    vibratePhone()
                }
            }

        val actionButtons = listOf(
            viewBinding.mainMinus,
            viewBinding.mainMulty,
            viewBinding.mainPoint,
            viewBinding.mainPlus,
            viewBinding.mainDev,
            viewBinding.mainSqrt,
            viewBinding.mainPow,)
        actionButtons.forEach { textView ->
                textView.setOnClickListener {
                    viewModel.onSightClick(textView.text[0])
                    vibratePhone()
                }
        }

        viewBinding.mainClear.setOnClickListener{
            viewModel.onClearButton()
        }

        viewBinding.mainBackspace.setOnClickListener{
            viewModel.onBackSpace()
        }

        viewBinding.mainEqual.setOnClickListener{
            viewModel.onEqualClicked()
        }


        viewBinding.resultTv.setOnClickListener {
            val clipboard =  getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("result of computation", viewModel.resultState.value);
            clipboard.setPrimaryClip(clip)
        }

        viewModel.expressionState.observe(this) {state ->
            viewBinding.inputEt.setText(state)
        }

        viewModel.resultState.observe(this) { state ->
            viewBinding.resultTv.setText(state)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }

    private fun vibratePhone() {
        if (viewModel.vibrationIntesiv.value ?: 0 > 0) {
            Log.d("MainActivity", "I'm vibrate... so strong: ${viewModel.vibrationIntesiv.value}, baby")
            val vibrator = this?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= 26) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        200,
                        viewModel.vibrationIntesiv.value ?: 1
                    )
                )
            } else {
                vibrator.vibrate(200)
            }
        }
        else
            Log.d("MainActivity", "Not vibrate")
    }


    private fun openSettings() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    private fun openHistory() {
        resultLauncher.launch()
    }

}

