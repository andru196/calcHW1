package com.example.calchw1.presentation

import android.app.AlertDialog
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.calchw1.R
import com.example.calchw1.common.BaseActivity
import com.example.calchw1.databinding.SettingsActivityBinding
import com.example.calchw1.di.SettingsDaoProvider
import com.example.calchw1.viewModels.ResultPanelType
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

        viewModel.resultPanelState.observe(this) {state ->
            viewBinding.resultPanelDescription.text =
                resources.getStringArray(R.array.result_pnl_types)[state.ordinal]
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