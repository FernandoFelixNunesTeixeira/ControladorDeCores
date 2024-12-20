package com.example.colorsortingcontroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.colorsortingcontroller.data.AppDatabase
import com.example.colorsortingcontroller.data.Parametros
import com.example.colorsortingcontroller.data.ParametrosDao
import com.example.colorsortingcontroller.data.ParametrosLocalSource
import com.example.colorsortingcontroller.data.ParametrosRepository
import com.example.colorsortingcontroller.navigation.AppNavigation
import com.example.colorsortingcontroller.screen.EstatisticasViewModel
import com.example.colorsortingcontroller.screen.MonitoramentoViewModel
import com.example.colorsortingcontroller.screen.ParametrosViewModel
import com.example.colorsortingcontroller.screen.ParametrosViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()

        val parametrosDao: ParametrosDao = database.parametrosDao()
        val parametrosSource = ParametrosLocalSource(parametrosDao)
        val parametrosRepository = ParametrosRepository(parametrosSource)

        setContent {
            MaterialTheme {

                val parametrosViewModel: ParametrosViewModel = remember {
                    ViewModelProvider(
                        this,
                        ParametrosViewModelFactory(parametrosRepository)
                    ).get(ParametrosViewModel::class.java)
                }

                val monitoramentoViewModel: MonitoramentoViewModel = viewModel()
                val estatisticasViewModel: EstatisticasViewModel = viewModel()

                AppNavigation(
                    monitoramentoViewModel = monitoramentoViewModel,
                    parametrosViewModel = parametrosViewModel,
                    estatisticasViewModel = estatisticasViewModel
                )
            }
        }
    }
}