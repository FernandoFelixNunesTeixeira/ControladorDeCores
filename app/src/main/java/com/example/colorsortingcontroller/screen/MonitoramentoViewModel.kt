package com.example.colorsortingcontroller.screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MonitoramentoViewModel : ViewModel() {
    private val _state = MutableStateFlow(ScreenState.monitoramento)
    val state: StateFlow<ScreenState> = _state
}