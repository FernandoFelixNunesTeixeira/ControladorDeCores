package com.example.colorsortingcontroller.data

import kotlinx.coroutines.flow.Flow


class ParametrosRepository(private val parametrosSource: ParametrosLocalSource) {
    val allParametros: Flow<List<Parametros>> = parametrosSource.getAllParametros()

    //Inserir parametros no banco de dados
    suspend fun insertParametros(
        posicaoServoPortaMin: Int,
        posicaoServoPortaMax: Int,
        posicaoServoDirecionadorEDMin: Int,
        posicaoServoDirecionadorEDMax: Int,
        posicaoServoDirecionador12Min: Int,
        posicaoServoDirecionador12Max: Int,
        posicaoServoDirecionador34Min: Int,
        posicaoServoDirecionador34Max: Int,
        cor: String,
        rValue: Int,
        gValue: Int,
        bValue: Int
    ){ parametrosSource.insert(
            posicaoServoPortaMin,
            posicaoServoPortaMax,
            posicaoServoDirecionadorEDMin,
            posicaoServoDirecionadorEDMax,
            posicaoServoDirecionador12Min,
            posicaoServoDirecionador12Max,
            posicaoServoDirecionador34Min,
            posicaoServoDirecionador34Max,
            cor,
            rValue,
            gValue,
            bValue
        )
    }

    suspend fun deleteParametros(id: Int){
        parametrosSource.delete(id)
    }


    // Cor RGB
    suspend fun updateRGBValues(rValue: Int, gValue: Int, bValue: Int) {
        parametrosSource.updateRValue(
            rValue = rValue
        )
        parametrosSource.updateGValue(
            gValue = gValue
        )
        parametrosSource.updateBValue(
            bValue = bValue
        )
    }

    suspend fun updateCor(cor: String){
        parametrosSource.updateCor(
            cor = cor
        )
    }

    // Motor Porta
    suspend fun updateDirecionadorPorta(posicaoServoPortaMin: Int, posicaoServoPortaMax: Int) {
        parametrosSource.updatePosicaoServoPortaMin(
            posicaoServoPortaMin = posicaoServoPortaMin
        )
        parametrosSource.updatePosicaoServoPortaMax(
            posicaoServoPortaMax = posicaoServoPortaMax
        )
    }

    // Motor ED
    suspend fun updateDirecionadorED(posicaoServoDirecionadorEDMin: Int, posicaoServoDirecionadorEDMax: Int) {
        parametrosSource.updatePosicaoServoDirecionadorEDMin(
            posicaoServoDirecionadorEDMin = posicaoServoDirecionadorEDMin
        )
        parametrosSource.updatePosicaoServoDirecionadorEDMax(
            posicaoServoDirecionadorEDMax = posicaoServoDirecionadorEDMax
        )
    }

    // Motor 12
    suspend fun updateDirecionador12(posicaoServoDirecionador12Min: Int, posicaoServoDirecionador12Max: Int) {
        parametrosSource.updatePosicaoServoDirecionador12Min(
            posicaoServoDirecionador12Min = posicaoServoDirecionador12Min
        )
        parametrosSource.updatePosicaoServoDirecionador12Max(
            posicaoServoDirecionador12Max = posicaoServoDirecionador12Max
        )
    }

    // Motor 34
    suspend fun updateDirecionador34(posicaoServoDirecionador34Min: Int, posicaoServoDirecionador34Max: Int) {
        parametrosSource.updatePosicaoServoDirecionador34Min(
            posicaoServoDirecionador34Min = posicaoServoDirecionador34Min
        )
        parametrosSource.updatePosicaoServoDirecionador34Max(
            posicaoServoDirecionador34Max = posicaoServoDirecionador34Max
        )
    }
}

