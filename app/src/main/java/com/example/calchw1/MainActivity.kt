package com.example.calchw1

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher

class MainActivity : BaseActivity() {

    private val getResult: ActivityResultLauncher<Int> =
        registerForActivityResult(Result()) { result ->
        Toast.makeText(this, "$result", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val button = findViewById<Button>(R.id.main_settings)

        button.setOnClickListener {
            openSettings()
        }
    }

    private fun openSettings() {
        getResult.launch(10)
    }

}

