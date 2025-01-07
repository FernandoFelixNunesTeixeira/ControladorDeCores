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