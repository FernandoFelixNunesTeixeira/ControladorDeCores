package com.example.colorsortingcontroller.data

import android.util.Log
import kotlinx.coroutines.flow.Flow

class ParametrosLocalSource(private val parametrosDao: ParametrosDao) {

    fun getAllParametros(): Flow<List<Parametros>> {
        return parametrosDao.getAllParametros()
    }

    suspend fun updatePosicaoServoPortaMin(posicaoServoPortaMin: Int) {
        parametrosDao.updatePosicaoServoPortaMin(
            newPosicaoServoPortaMin = posicaoServoPortaMin
        )
    }

    suspend fun updatePosicaoServoPortaMax(posicaoServoPortaMax: Int) {
        parametrosDao.updatePosicaoServoPortaMax(
            newPosicaoServoPortaMax = posicaoServoPortaMax
        )
    }

    suspend fun updatePosicaoServoDirecionadorEDMin(posicaoServoDirecionadorEDMin: Int) {
        parametrosDao.updatePosicaoServoDirecionadorEDMin(
            newPosicaoServoDirecionadorEDMin = posicaoServoDirecionadorEDMin
        )
    }

    suspend fun updatePosicaoServoDirecionadorEDMax(posicaoServoDirecionadorEDMax: Int) {
        parametrosDao.updatePosicaoServoDirecionadorEDMax(
            newPosicaoServoDirecionadorEDMax = posicaoServoDirecionadorEDMax
        )
    }

    suspend fun updatePosicaoServoDirecionador12Min(posicaoServoDirecionador12Min: Int) {
        parametrosDao.updatePosicaoServoDirecionador12Min(
            newPosicaoServoDirecionador12Min = posicaoServoDirecionador12Min
        )
    }

    suspend fun updatePosicaoServoDirecionador12Max(posicaoServoDirecionador12Max: Int) {
        parametrosDao.updatePosicaoServoDirecionador12Max(
            newPosicaoServoDirecionador12Max = posicaoServoDirecionador12Max
        )
    }

    suspend fun updatePosicaoServoDirecionador34Min(posicaoServoDirecionador34Min: Int) {
        parametrosDao.updatePosicaoServoDirecionador34Min(
            newPosicaoServoDirecionador34Min = posicaoServoDirecionador34Min
        )
    }

    suspend fun updatePosicaoServoDirecionador34Max(posicaoServoDirecionador34Max: Int) {
        parametrosDao.updatePosicaoServoDirecionador34Max(
            newPosicaoServoDirecionador34Max = posicaoServoDirecionador34Max
        )
    }

    suspend fun updateRValue(rValue: Int) {
        parametrosDao.updateRValue(
            newRValue = rValue
        )
    }

    suspend fun updateGValue(gValue: Int) {
        parametrosDao.updateGValue(
            newGValue = gValue
        )
    }

    suspend fun updateBValue(bValue: Int) {
        parametrosDao.updateBValue(
            newBValue = bValue
        )
    }

}