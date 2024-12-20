package com.example.colorsortingcontroller.screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.colorsortingcontroller.data.Parametros
import com.example.colorsortingcontroller.data.ParametrosRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ParametrosViewModel(private val parametrosRepository: ParametrosRepository) : ViewModel() {
    private val _state = MutableStateFlow(ScreenState.parametros)
    val state: StateFlow<ScreenState> = _state

    // Recebe todos os valores do repositório
    val allParametros: LiveData<List<Parametros>> = parametrosRepository.allParametros.asLiveData()

    fun logAllParametros() = viewModelScope.launch {
        allParametros.value?.forEach { parametro ->
            Log.d("ParametrosLog", """
            ID: ${parametro.id}
            posicaoServoPortaMin: ${parametro.posicaoServoPortaMin}
            posicaoServoPortaMax: ${parametro.posicaoServoPortaMax}
            posicaoServoDirecionadorEDMin: ${parametro.posicaoServoDirecionadorEDMin}
            posicaoServoDirecionadorEDMax: ${parametro.posicaoServoDirecionadorEDMax}
            posicaoServoDirecionador12Min: ${parametro.posicaoServoDirecionador12Min}
            posicaoServoDirecionador12Max: ${parametro.posicaoServoDirecionador12Max}
            posicaoServoDirecionador34Min: ${parametro.posicaoServoDirecionador34Min}
            posicaoServoDirecionador34Max: ${parametro.posicaoServoDirecionador34Max}
            rValue: ${parametro.rValue}
            gValue: ${parametro.gValue}
            bValue: ${parametro.bValue}
        """.trimIndent())
        } ?: Log.d("ParametrosLog", "Nenhum parâmetro encontrado em allParametros.")
    }

    // Envia a lista de parametros para a UI
    val parametros = parametrosRepository.allParametros.map { list ->
        list.firstOrNull()
    }

    // Função da ViewModel para atualizar os dados
    fun update(
        posicaoServoPortaMin: Int,
        posicaoServoPortaMax: Int,
        posicaoServoDirecionadorEDMin: Int,
        posicaoServoDirecionadorEDMax: Int,
        posicaoServoDirecionador12Min: Int,
        posicaoServoDirecionador12Max: Int,
        posicaoServoDirecionador34Min: Int,
        posicaoServoDirecionador34Max: Int,

        rValue: Int,
        gValue: Int,
        bValue: Int
    ) = viewModelScope.launch {
        withContext(Dispatchers.IO)
        {
            parametrosRepository.updateRGBValues(
                rValue = rValue,
                gValue = gValue,
                bValue = bValue
            )

            parametrosRepository.updateDirecionadorPorta(
                posicaoServoPortaMin = posicaoServoPortaMin,
                posicaoServoPortaMax = posicaoServoPortaMax
            )

            parametrosRepository.updateDirecionadorED(
                posicaoServoDirecionadorEDMin = posicaoServoDirecionadorEDMin,
                posicaoServoDirecionadorEDMax = posicaoServoDirecionadorEDMax
            )

            parametrosRepository.updateDirecionador12(
                posicaoServoDirecionador12Min = posicaoServoDirecionador12Min,
                posicaoServoDirecionador12Max = posicaoServoDirecionador12Max,
            )

            parametrosRepository.updateDirecionador34(
                posicaoServoDirecionador34Min = posicaoServoDirecionador34Min,
                posicaoServoDirecionador34Max = posicaoServoDirecionador34Max
            )

        }
    }
}
