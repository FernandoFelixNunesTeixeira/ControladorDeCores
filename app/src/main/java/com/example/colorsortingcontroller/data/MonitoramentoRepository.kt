package com.example.colorsortingcontroller.data

import kotlinx.coroutines.flow.Flow

class MonitoramentoRepository(private val monitoramentoSource: MonitoramentoLocalSource){
    val allMonitoramento: Flow<List<Monitoramento>> = monitoramentoSource.getAllMonitoramento()

    suspend fun insertMonitoramento(estado: String, corAtual: String){
        monitoramentoSource.insertMonitoramento(estado, corAtual)
    }

    suspend fun updateEstado(estado: String){
        monitoramentoSource.updateEstado(estado = estado)
    }

    suspend fun updateCorAtual(corAtual: String){
        monitoramentoSource.updateCorAtual(corAtual = corAtual)
    }

    suspend fun deleteMonitoramento(id: Int){
        monitoramentoSource.deleteMonitoramento(id)
    }
}