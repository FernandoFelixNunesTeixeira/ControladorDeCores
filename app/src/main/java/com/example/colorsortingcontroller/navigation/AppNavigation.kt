package com.example.colorsortingcontroller.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.colorsortingcontroller.estatisticas.EstatisticasViewModel
import com.example.colorsortingcontroller.monitoramento.MonitoramentoViewModel
import com.example.colorsortingcontroller.parametros.ParametrosViewModel
import com.example.colorsortingcontroller.estatisticas.EstatisticasScreen
import com.example.colorsortingcontroller.monitoramento.MonitoramentoScreen
import com.example.colorsortingcontroller.parametros.ParametrosScreen
import com.example.colorsortingcontroller.ScreenState
import com.example.colorsortingcontroller.ui.ScaffoldApp

@Composable
fun AppNavigation(
    monitoramentoViewModel: MonitoramentoViewModel,
    parametrosViewModel: ParametrosViewModel,
    estatisticasViewModel: EstatisticasViewModel
) {
    var currentScreen by rememberSaveable { mutableStateOf(ScreenState.monitoramento) }

    // Troca a tela com base na tela atual
    when (currentScreen) {

        ScreenState.monitoramento -> ScaffoldApp(
            state = monitoramentoViewModel.state.collectAsState().value,
            onScreenChange = { currentScreen = it }
        ) {
            MonitoramentoScreen()
        }

        ScreenState.parametros -> ScaffoldApp(
            state = parametrosViewModel.state.collectAsState().value,
            onScreenChange = { currentScreen = it }
        ) {
            ParametrosScreen()
        }

        ScreenState.estatisticas -> ScaffoldApp(
            state = estatisticasViewModel.state.collectAsState().value,
            onScreenChange = { currentScreen = it }
        ) {
            EstatisticasScreen()
        }
    }
}
