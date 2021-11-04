package com.example.calchw1.presentation

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.calchw1.R
import com.example.calchw1.common.BaseActivity
import com.example.calchw1.common.Result
import com.example.calchw1.databinding.MainActivityBinding
import com.example.calchw1.viewModels.MainViewModel

class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val viewBinding by viewBinding(MainActivityBinding::bind)

    private val getResult: ActivityResultLauncher<Int> =
        registerForActivityResult(Result()) { result ->
        Toast.makeText(this, "$result", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        viewBinding.mainSettings.setOnClickListener {
            openSettings()
        }

        viewBinding.inputEt.apply {
            showSoftInputOnFocus = false
        }


        listOf(viewBinding.mainZero,
            viewBinding.mainOne,
            viewBinding.mainTwo,
            viewBinding.mainThree,
            viewBinding.mainFour,
            viewBinding.mainFive,
            viewBinding.mainSix,
            viewBinding.mainSeven,
            viewBinding.mainEight,
            viewBinding.mainNine)
            .forEachIndexed { index, textView ->
                textView.setOnClickListener { viewModel.onNumberClick(index) }
            }

        listOf(
            viewBinding.mainMinus,
            viewBinding.mainMulty,
            viewBinding.mainPoint,
            viewBinding.mainPlus,
            viewBinding.mainDev)
            .forEach { textView ->
                textView.setOnClickListener { viewModel.onSightClick(textView.text[0]) }
            }

        viewBinding.mainClear.setOnClickListener{
            viewModel.onClearButton()
        }

        viewBinding.mainBackspace.setOnClickListener{
            viewModel.onBackSpace()
        }
        viewBinding.mainEqual.setOnClickListener{
            viewModel.getResult()
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

    private fun openSettings() {
        getResult.launch(10)
    }

}

