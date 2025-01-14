package com.example.colorsortingcontroller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.colorsortingcontroller.data.repository.EstatisticasRepository
import com.example.colorsortingcontroller.data.repository.MonitoramentoRepository
import com.example.colorsortingcontroller.data.repository.ParametrosRepository
import com.example.colorsortingcontroller.estatisticas.EstatisticasViewModel
import com.example.colorsortingcontroller.monitoramento.MonitoramentoViewModel
import com.example.colorsortingcontroller.parametros.ParametrosViewModel
import com.example.colorsortingcontroller.ui.home.HomeViewModel
import com.google.firebase.auth.FirebaseAuth

// Utilização de factory para a instanciação de ViewModels e injeção de dependências

class HomeViewModelFactory(private val auth: FirebaseAuth) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                HomeViewModel(auth) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}

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