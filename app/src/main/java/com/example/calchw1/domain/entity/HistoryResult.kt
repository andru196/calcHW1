package com.example.calchw1.domain.entity

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.example.calchw1.presentation.HistoryActivity

class HistoryResult : ActivityResultContract<Unit, HistoryItem?>() {

    override fun createIntent(context: Context, input: Unit?): Intent {
        return Intent(context, HistoryActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): HistoryItem? {
        return intent?.getParcelableExtra(HistoryActivity.HISTORY_ACTIVITY_KEY)
    }

}