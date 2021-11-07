package com.example.calchw1.di

import android.content.Context
import com.example.calchw1.data.db.MainDatabase

object DatabaseProvider {
    private var db: MainDatabase? = null

    fun get(context: Context): MainDatabase {
        return  db?: MainDatabase.create(context).also { db=it }
    }
}

