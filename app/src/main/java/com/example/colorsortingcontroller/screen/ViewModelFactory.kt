package com.example.colorsortingcontroller.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.colorsortingcontroller.data.EstatisticasRepository
import com.example.colorsortingcontroller.data.MonitoramentoRepository
import com.example.colorsortingcontroller.data.ParametrosRepository

class ParametrosViewModelFactory(
    private val parametrosRepository: ParametrosRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ParametrosViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                ParametrosViewModel(parametrosRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}

class MonitoramentoViewModelFactory(
    private val monitoramentoRepository: MonitoramentoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MonitoramentoViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                MonitoramentoViewModel(monitoramentoRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}

class EstatisticasViewModelFactory(
    private val estatisticasRepository: EstatisticasRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(EstatisticasViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                EstatisticasViewModel(estatisticasRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}