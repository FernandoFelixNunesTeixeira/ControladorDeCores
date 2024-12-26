package com.example.colorsortingcontroller.data

import kotlinx.coroutines.flow.Flow

class MonitoramentoLocalSource(private val appDao: AppDao){
    fun getAllMonitoramento(): Flow<List<Monitoramento>> {
        return appDao.getAllMonitoramento()
    }

    suspend fun insertMonitoramento(estado: String, corAtual: String){
        appDao.insertMonitoramento(estado, corAtual)
    }

    suspend fun updateEstado(estado: String){
        appDao.updateEstado(newEstado = estado)
    }

    suspend fun updateCorAtual(corAtual: String){
        appDao.updateCorAtual(newCorAtual = corAtual)
    }

    suspend fun deleteMonitoramento(id: Int){
        appDao.deleteMonitoramento(id)
    }
}

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

class EstatisticasLocalSource(private val appDao: AppDao){
    fun getAllEstatisticas(): Flow<List<Estatisticas>> {
        return appDao.getAllEstatisticas()
    }

    suspend fun insertEstatisticas(
        pecasCor1: Int,
        pecasCor2: Int,
        pecasCor3: Int,
        pecasCor4: Int,
        pecasCor5: Int,
        pecasCor6: Int,
        pecasCor7: Int,
        pecasCor8: Int,

        pecasColetor1: Int,
        pecasColetor2: Int,
        pecasColetor3: Int,
        pecasColetor4: Int
    ){
        appDao.insertEstatisticas(
            pecasCor1,
            pecasCor2,
            pecasCor3,
            pecasCor4,
            pecasCor5,
            pecasCor6,
            pecasCor7,
            pecasCor8,
            pecasColetor1,
            pecasColetor2,
            pecasColetor3,
            pecasColetor4
        )
    }

    suspend fun updatePecasCor1(pecasCor1: Int){
        appDao.updatePecasCor1(newPecasCor1 = pecasCor1)
    }

    suspend fun updatePecasCor2(pecasCor2: Int){
        appDao.updatePecasCor2(newPecasCor2 = pecasCor2)
    }

    suspend fun updatePecasCor3(pecasCor3: Int){
        appDao.updatePecasCor3(newPecasCor3 = pecasCor3)
    }

    suspend fun updatePecasCor4(pecasCor4: Int){
        appDao.updatePecasCor4(newPecasCor4 = pecasCor4)
    }

    suspend fun updatePecasCor5(pecasCor5: Int){
        appDao.updatePecasCor5(newPecasCor5 = pecasCor5)
    }

    suspend fun updatePecasCor6(pecasCor6: Int){
        appDao.updatePecasCor6(newPecasCor6 = pecasCor6)
    }

    suspend fun updatePecasCor7(pecasCor7: Int){
        appDao.updatePecasCor7(newPecasCor7 = pecasCor7)
    }

    suspend fun updatePecasCor8(pecasCor8: Int){
        appDao.updatePecasCor8(newPecasCor8 = pecasCor8)
    }

    suspend fun updatePecasColetor1(pecasColetor1: Int){
        appDao.updatePecasColetor1(newPecasColetor1 = pecasColetor1)
    }

    suspend fun updatePecasColetor2(pecasColetor2: Int){
        appDao.updatePecasColetor2(newPecasColetor2 = pecasColetor2)
    }

    suspend fun updatePecasColetor3(pecasColetor3: Int){
        appDao.updatePecasColetor3(newPecasColetor3 = pecasColetor3)
    }

    suspend fun updatePecasColetor4(pecasColetor4: Int){
        appDao.updatePecasColetor4(newPecasColetor4 = pecasColetor4)
    }

    suspend fun deleteEstatisticas(id: Int){
        appDao.deleteEstatisticas(id)
    }
}