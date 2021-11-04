package com.example.calchw1

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract

class Result : ActivityResultContract<Int, String?>() {

    override fun createIntent(context: Context, input: Int?): Intent {
        Toast.makeText(context, "Настройки", Toast.LENGTH_SHORT).show()
        val intetn = Intent(context, SettingsActivity::class.java)
        intetn.putExtra(SettingsActivity.SETTINGS_RESULT_KEY, 10)
        return intetn
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        return intent?.getStringExtra(SettingsActivity.SETTINGS_RESULT_KEY)
    }

}