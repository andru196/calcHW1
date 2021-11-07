package com.example.calchw1.presentation

import android.app.AlertDialog
import android.os.Bundle
import android.widget.SeekBar
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.calchw1.R
import com.example.calchw1.common.BaseActivity
import com.example.calchw1.databinding.SettingsActivityBinding
import com.example.calchw1.di.SettingsDaoProvider
import com.example.calchw1.domain.entity.ResultPanelType
import com.example.calchw1.viewModels.SettingsViewModel

class SettingsActivity: BaseActivity() {
    private val viewBinding by viewBinding(SettingsActivityBinding::bind)
    private val viewModel by viewModels<SettingsViewModel>{
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SettingsViewModel(SettingsDaoProvider.getDao(this@SettingsActivity))
                        as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        viewBinding.settingsBack.setOnClickListener{
            finish()
        }

        viewBinding.resultPanelContainer.setOnClickListener{
            viewModel.onResultPanelTypeClicked()
        }

        viewBinding.precisionValue.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                viewBinding.precisionValueText.text = seekBar.progress.toString()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                viewModel.onPrecisionChanged(seekBar.progress)
            }
        })

        viewBinding.vibrationValue.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                viewBinding.vibrationValueText.text = seekBar.progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                viewModel.onVibrationChanged(seekBar.progress)
            }
        })


        viewModel.resultPanelState.observe(this) {state ->
            viewBinding.resultPanelDescription.text =
                resources.getStringArray(R.array.result_pnl_types)[state.ordinal]
        }

        viewModel.precisionResult.observe(this) {
            viewBinding.precisionValue.progress = it
        }

        viewModel.vibrationIntesiv.observe(this) {
            viewBinding.vibrationValue.progress = it
        }


        viewModel.openResultPanelAction.observe(this) { type ->
            showDialog(type)
        }
    }

    private fun showDialog(type: ResultPanelType) {
        var result: Int? = null
        AlertDialog.Builder(this)
            .setTitle("Title")
            .setPositiveButton("Ok") {_, _ ->
                result?.let { viewModel.onResultPanelTypeChanged(ResultPanelType.values()[it]) }
            }
            .setNegativeButton("Cncl") { _, _ ->
                result = null
            }
            .setSingleChoiceItems(R.array.result_pnl_types, type.ordinal){_, id ->
                result = id
            }
            .show()
    }
}