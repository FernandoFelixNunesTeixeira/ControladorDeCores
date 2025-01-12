package com.example.colorsortingcontroller.data.localSource

import com.example.colorsortingcontroller.data.entities.Parametros
import com.example.colorsortingcontroller.data.dao.ParametrosDao
import kotlinx.coroutines.flow.Flow



class ParametrosLocalSource(private val parametrosDao: ParametrosDao) {
    fun getAllParametros(): Flow<List<Parametros>> {
        return parametrosDao.getAllParametros()
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
        RCor1: Float,
        GCor1: Float,
        BCor1: Float,
        coletorCor1: Int,
        RCor2: Float,
        GCor2: Float,
        BCor2: Float,
        coletorCor2: Int,
        RCor3: Float,
        GCor3: Float,
        BCor3: Float,
        coletorCor3: Int,
        RCor4: Float,
        GCor4: Float,
        BCor4: Float,
        coletorCor4: Int,
        RCor5: Float,
        GCor5: Float,
        BCor5: Float,
        coletorCor5: Int,
        RCor6: Float,
        GCor6: Float,
        BCor6: Float,
        coletorCor6: Int,
        RCor7: Float,
        GCor7: Float,
        BCor7: Float,
        coletorCor7: Int
    ){
        parametrosDao.insert(
            posicaoServoPortaMin,
            posicaoServoPortaMax,
            posicaoServoDirecionadorEDMin,
            posicaoServoDirecionadorEDMax,
            posicaoServoDirecionador12Min,
            posicaoServoDirecionador12Max,
            posicaoServoDirecionador34Min,
            posicaoServoDirecionador34Max,
            RCor1,
            GCor1,
            BCor1,
            coletorCor1,
            RCor2,
            GCor2,
            BCor2,
            coletorCor2,
            RCor3,
            GCor3,
            BCor3,
            coletorCor3,
            RCor4,
            GCor4,
            BCor4,
            coletorCor4,
            RCor5,
            GCor5,
            BCor5,
            coletorCor5,
            RCor6,
            GCor6,
            BCor6,
            coletorCor6,
            RCor7,
            GCor7,
            BCor7,
            coletorCor7
        )
    }

    suspend fun delete(id: Int){
        parametrosDao.delete(id)
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

    suspend fun updateColetorCor1(coletorCor1: Int) {
        parametrosDao.updateColetorCor1(
            newColetorCor1 = coletorCor1
        )
    }

    suspend fun updateR1Value(r1Value: Float) {
        parametrosDao.updateR1Value(
            newR1Value = r1Value
        )
    }

    suspend fun updateG1Value(g1Value: Float) {
        parametrosDao.updateG1Value(
            newG1Value = g1Value
        )
    }

    suspend fun updateB1Value(b1Value: Float) {
        parametrosDao.updateB1Value(
            newB1Value = b1Value
        )
    }

    suspend fun updateColetorCor2(coletorCor2: Int) {
        parametrosDao.updateColetorCor2(
            newColetorCor2 = coletorCor2
        )
    }

    suspend fun updateR2Value(r2Value: Float) {
        parametrosDao.updateR2Value(
            newR2Value = r2Value
        )
    }

    suspend fun updateG2Value(g2Value: Float) {
        parametrosDao.updateG2Value(
            newG2Value = g2Value
        )
    }

    suspend fun updateB2Value(b2Value: Float) {
        parametrosDao.updateB2Value(
            newB2Value = b2Value
        )
    }

    suspend fun updateColetorCor3(coletorCor3: Int) {
        parametrosDao.updateColetorCor3(
            newColetorCor3 = coletorCor3
        )
    }

    suspend fun updateR3Value(r3Value: Float) {
        parametrosDao.updateR3Value(
            newR3Value = r3Value
        )
    }

    suspend fun updateG3Value(g3Value: Float) {
        parametrosDao.updateG3Value(
            newG3Value = g3Value
        )
    }

    suspend fun updateB3Value(b3Value: Float) {
        parametrosDao.updateB3Value(
            newB3Value = b3Value
        )
    }

    suspend fun updateColetorCor4(coletorCor4: Int) {
        parametrosDao.updateColetorCor4(
            newColetorCor4 = coletorCor4
        )
    }

    suspend fun updateR4Value(r4Value: Float) {
        parametrosDao.updateR4Value(
            newR4Value = r4Value
        )
    }

    suspend fun updateG4Value(g4Value: Float) {
        parametrosDao.updateG4Value(
            newG4Value = g4Value
        )
    }

    suspend fun updateB4Value(b4Value: Float) {
        parametrosDao.updateB4Value(
            newB4Value = b4Value
        )
    }


    suspend fun updateColetorCor5(coletorCor5: Int) {
        parametrosDao.updateColetorCor5(
            newColetorCor5 = coletorCor5
        )
    }

    suspend fun updateR5Value(r5Value: Float) {
        parametrosDao.updateR5Value(
            newR5Value = r5Value
        )
    }

    suspend fun updateG5Value(g5Value: Float) {
        parametrosDao.updateG5Value(
            newG5Value = g5Value
        )
    }

    suspend fun updateB5Value(b5Value: Float) {
        parametrosDao.updateB5Value(
            newB5Value = b5Value
        )
    }


    suspend fun updateColetorCor6(coletorCor6: Int) {
        parametrosDao.updateColetorCor6(
            newColetorCor6 = coletorCor6
        )
    }

    suspend fun updateR6Value(r6Value: Float) {
        parametrosDao.updateR6Value(
            newR6Value = r6Value
        )
    }

    suspend fun updateG6Value(g6Value: Float) {
        parametrosDao.updateG6Value(
            newG6Value = g6Value
        )
    }

    suspend fun updateB6Value(b6Value: Float) {
        parametrosDao.updateB6Value(
            newB6Value = b6Value
        )
    }


    suspend fun updateColetorCor7(coletorCor7: Int) {
        parametrosDao.updateColetorCor7(
            newColetorCor7 = coletorCor7
        )
    }

    suspend fun updateR7Value(r7Value: Float) {
        parametrosDao.updateR7Value(
            newR7Value = r7Value
        )
    }

    suspend fun updateG7Value(g7Value: Float) {
        parametrosDao.updateG7Value(
            newG7Value = g7Value
        )
    }

    suspend fun updateB7Value(b7Value: Float) {
        parametrosDao.updateB7Value(
            newB7Value = b7Value
        )
    }









}

