package com.example.colorsortingcontroller.screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

enum class ScreenState(val title: String) {
    monitoramento("Monitoramento"),
    parametros("Parâmetros"),
    estatisticas("Estatísticas")
}

class MonitoramentoViewModel : ViewModel() {
    private val _state = MutableStateFlow(ScreenState.monitoramento)
    val state: StateFlow<ScreenState> = _state
}

class ParametrosViewModel : ViewModel() {
    private val _state = MutableStateFlow(ScreenState.parametros)
    val state: StateFlow<ScreenState> = _state
}

class EstatisticasViewModel : ViewModel() {
    private val _state = MutableStateFlow(ScreenState.estatisticas)
    val state: StateFlow<ScreenState> = _state
}