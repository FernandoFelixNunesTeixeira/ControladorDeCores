package com.example.colorsortingcontroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.colorsortingcontroller.com.example.colorsortingcontroller.navigation.AppNavigation
import com.example.colorsortingcontroller.com.example.colorsortingcontroller.screen.EstatisticasViewModel
import com.example.colorsortingcontroller.com.example.colorsortingcontroller.screen.MonitoramentoViewModel
import com.example.colorsortingcontroller.com.example.colorsortingcontroller.screen.ParametrosViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val monitoramentoViewModel: MonitoramentoViewModel = viewModel()
                val parametrosViewModel: ParametrosViewModel = viewModel()
                val estatisticasViewModel: EstatisticasViewModel = viewModel()

                // Chama a função que cuida da troca de telas
                AppNavigation(
                    monitoramentoViewModel = monitoramentoViewModel,
                    parametrosViewModel = parametrosViewModel,
                    estatisticasViewModel = estatisticasViewModel
                )
            }
        }
    }
}