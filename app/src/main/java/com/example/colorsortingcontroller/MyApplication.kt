package com.example.colorsortingcontroller

import android.app.Application
import com.example.colorsortingcontroller.data.AppDatabase
import com.example.colorsortingcontroller.data.ParametrosLocalSource
import com.example.colorsortingcontroller.data.ParametrosRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MyApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    private val dataBase by lazy { AppDatabase.getDatabase(this, applicationScope) }
    private val movieLocalSource by lazy { ParametrosLocalSource(dataBase.parametrosDao()) }
    val parametrosRepository by lazy { ParametrosRepository(movieLocalSource) }
}