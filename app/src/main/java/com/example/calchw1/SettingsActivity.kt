package com.example.calchw1

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast

class SettingsActivity: BaseActivity() {
    companion object {
        const val SETTINGS_RESULT_KEY = "SETTINGS_RESULT_KEY"
        const val SETTINGS_RESULT_REQUEST_CODE = 9
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        val data = intent.getIntExtra(SETTINGS_RESULT_KEY, -1)

        Toast.makeText(this, data.toString(), Toast.LENGTH_SHORT).show()
        val back = findViewById<ImageView>(R.id.settings_back)
        back.setOnClickListener{
            setResult(RESULT_OK, Intent().putExtra(SETTINGS_RESULT_KEY, "результат"))
            finish()
        }

    }
}