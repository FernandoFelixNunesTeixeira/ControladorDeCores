package com.example.colorsortingcontroller.screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class EstatisticasViewModel : ViewModel() {
    private val _state = MutableStateFlow(ScreenState.estatisticas)
    val state: StateFlow<ScreenState> = _state
}