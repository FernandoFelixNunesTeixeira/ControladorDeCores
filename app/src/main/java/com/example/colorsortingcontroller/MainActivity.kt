package com.example.colorsortingcontroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.colorsortingcontroller.data.AppDatabase
import com.example.colorsortingcontroller.data.AppDao
import com.example.colorsortingcontroller.data.EstatisticasLocalSource
import com.example.colorsortingcontroller.data.EstatisticasRepository
import com.example.colorsortingcontroller.data.MonitoramentoLocalSource
import com.example.colorsortingcontroller.data.MonitoramentoRepository
import com.example.colorsortingcontroller.data.ParametrosLocalSource
import com.example.colorsortingcontroller.data.ParametrosRepository
import com.example.colorsortingcontroller.navigation.AppNavigation
import com.example.colorsortingcontroller.screen.EstatisticasViewModel
import com.example.colorsortingcontroller.screen.EstatisticasViewModelFactory
import com.example.colorsortingcontroller.screen.MonitoramentoViewModel
import com.example.colorsortingcontroller.screen.MonitoramentoViewModelFactory
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

        val appDao: AppDao = database.appDao()

        val monitoramentoSource = MonitoramentoLocalSource(appDao)
        val parametrosSource = ParametrosLocalSource(appDao)
        val estatisticasSource = EstatisticasLocalSource(appDao)

        val monitoramentoRepository = MonitoramentoRepository(monitoramentoSource)
        val parametrosRepository = ParametrosRepository(parametrosSource)
        val estatisticasRepository = EstatisticasRepository(estatisticasSource)

        setContent {
            MaterialTheme {
                val parametrosViewModel: ParametrosViewModel = remember {
                    ViewModelProvider(
                        this,
                        ParametrosViewModelFactory(parametrosRepository)
                    ).get(ParametrosViewModel::class.java)
                }

                val monitoramentoViewModel: MonitoramentoViewModel = remember {
                    ViewModelProvider(
                        this,
                        MonitoramentoViewModelFactory(monitoramentoRepository)
                    ).get(MonitoramentoViewModel::class.java)
                }

                val estatisticasViewModel: EstatisticasViewModel = remember {
                    ViewModelProvider(
                        this,
                        EstatisticasViewModelFactory(estatisticasRepository)
                    ).get(EstatisticasViewModel::class.java)
                }

                AppNavigation(
                    monitoramentoViewModel = monitoramentoViewModel,
                    parametrosViewModel = parametrosViewModel,
                    estatisticasViewModel = estatisticasViewModel
                )
            }
        }
    }
}