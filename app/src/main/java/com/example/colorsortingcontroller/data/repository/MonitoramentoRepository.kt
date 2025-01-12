package com.example.colorsortingcontroller.data.repository

import com.example.colorsortingcontroller.data.entities.Monitoramento
import com.example.colorsortingcontroller.data.localSource.MonitoramentoLocalSource
import kotlinx.coroutines.flow.Flow

class MonitoramentoRepository(private val monitoramentoSource: MonitoramentoLocalSource){
    val allMonitoramento: Flow<List<Monitoramento>> = monitoramentoSource.getAllMonitoramento()

    suspend fun insertMonitoramento(estado: String, corAtual: String, sensorR: Float, sensorG: Float, sensorB: Float, portaAberta: Boolean, coletorAtual: Int){
        monitoramentoSource.insertMonitoramento(estado, corAtual, sensorR, sensorG, sensorB, portaAberta, coletorAtual)
    }

    suspend fun updateEstado(estado: String){
        monitoramentoSource.updateEstado(estado = estado)
    }

    suspend fun updateCorAtual(corAtual: String){
        monitoramentoSource.updateCorAtual(corAtual = corAtual)
    }


    suspend fun updateSensorR(sensorR: Float){
        monitoramentoSource.updateSensorR(sensorR = sensorR)
    }
    suspend fun updateSensorG(sensorG: Float){
        monitoramentoSource.updateSensorG(sensorG = sensorG)
    }

    suspend fun updateSensorB(sensorB: Float){
        monitoramentoSource.updateSensorB(sensorB = sensorB)
    }

    suspend fun updatePortaAberta(portaAberta: Boolean){
        monitoramentoSource.updatePortaAberta(portaAberta = portaAberta)
    }

    suspend fun updateColetorAtual(coletorAtual: Int){
        monitoramentoSource.updateColetorAtual(coletorAtual = coletorAtual)
    }

    suspend fun deleteMonitoramento(id: Int){
        monitoramentoSource.deleteMonitoramento(id)
    }
}