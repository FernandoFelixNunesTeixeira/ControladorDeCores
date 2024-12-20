package com.example.colorsortingcontroller.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.colorsortingcontroller.screen.EstatisticasViewModel
import com.example.colorsortingcontroller.screen.MonitoramentoViewModel
import com.example.colorsortingcontroller.screen.ParametrosViewModel
import com.example.colorsortingcontroller.ScaffoldApp
import com.example.colorsortingcontroller.screen.EstatisticasScreen
import com.example.colorsortingcontroller.screen.MonitoramentoScreen
import com.example.colorsortingcontroller.screen.ParametrosScreen
import com.example.colorsortingcontroller.screen.ScreenState

@Composable
fun AppNavigation(
    monitoramentoViewModel: MonitoramentoViewModel,
    parametrosViewModel: ParametrosViewModel,
    estatisticasViewModel: EstatisticasViewModel
) {
    var currentScreen by remember { mutableStateOf(ScreenState.monitoramento) }

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
