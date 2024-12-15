package com.example.colorsortingcontroller.com.example.colorsortingcontroller.screen

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ScreenState(val title: String)

class MonitoramentoViewModel : ViewModel() {
    private val _state = MutableStateFlow(ScreenState("Monitoramento"))
    val state: StateFlow<ScreenState> = _state
}

class ParametrosViewModel : ViewModel() {
    private val _state = MutableStateFlow(ScreenState("Parâmetros"))
    val state: StateFlow<ScreenState> = _state
}

class EstatisticasViewModel : ViewModel() {
    private val _state = MutableStateFlow(ScreenState("Estatísticas"))
    val state: StateFlow<ScreenState> = _state
}