package com.example.colorsortingcontroller.data

import kotlinx.coroutines.flow.Flow



class ParametrosLocalSource(private val appDao: AppDao) {
    fun getAllParametros(): Flow<List<Parametros>> {
        return appDao.getAllParametros()
    }

    suspend fun insert(
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
    ){
        appDao.insert(
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

    suspend fun delete(id: Int){
        appDao.delete(id)
    }

    suspend fun updatePosicaoServoPortaMin(posicaoServoPortaMin: Int) {
        appDao.updatePosicaoServoPortaMin(
            newPosicaoServoPortaMin = posicaoServoPortaMin
        )
    }

    suspend fun updatePosicaoServoPortaMax(posicaoServoPortaMax: Int) {
        appDao.updatePosicaoServoPortaMax(
            newPosicaoServoPortaMax = posicaoServoPortaMax
        )
    }

    suspend fun updatePosicaoServoDirecionadorEDMin(posicaoServoDirecionadorEDMin: Int) {
        appDao.updatePosicaoServoDirecionadorEDMin(
            newPosicaoServoDirecionadorEDMin = posicaoServoDirecionadorEDMin
        )
    }

    suspend fun updatePosicaoServoDirecionadorEDMax(posicaoServoDirecionadorEDMax: Int) {
        appDao.updatePosicaoServoDirecionadorEDMax(
            newPosicaoServoDirecionadorEDMax = posicaoServoDirecionadorEDMax
        )
    }

    suspend fun updatePosicaoServoDirecionador12Min(posicaoServoDirecionador12Min: Int) {
        appDao.updatePosicaoServoDirecionador12Min(
            newPosicaoServoDirecionador12Min = posicaoServoDirecionador12Min
        )
    }

    suspend fun updatePosicaoServoDirecionador12Max(posicaoServoDirecionador12Max: Int) {
        appDao.updatePosicaoServoDirecionador12Max(
            newPosicaoServoDirecionador12Max = posicaoServoDirecionador12Max
        )
    }

    suspend fun updatePosicaoServoDirecionador34Min(posicaoServoDirecionador34Min: Int) {
        appDao.updatePosicaoServoDirecionador34Min(
            newPosicaoServoDirecionador34Min = posicaoServoDirecionador34Min
        )
    }

    suspend fun updatePosicaoServoDirecionador34Max(posicaoServoDirecionador34Max: Int) {
        appDao.updatePosicaoServoDirecionador34Max(
            newPosicaoServoDirecionador34Max = posicaoServoDirecionador34Max
        )
    }

    suspend fun updateCor(cor: String) {
        appDao.updateCor(
            newCor = cor
        )
    }

    suspend fun updateRValue(rValue: Int) {
        appDao.updateRValue(
            newRValue = rValue
        )
    }

    suspend fun updateGValue(gValue: Int) {
        appDao.updateGValue(
            newGValue = gValue
        )
    }

    suspend fun updateBValue(bValue: Int) {
        appDao.updateBValue(
            newBValue = bValue
        )
    }
}

