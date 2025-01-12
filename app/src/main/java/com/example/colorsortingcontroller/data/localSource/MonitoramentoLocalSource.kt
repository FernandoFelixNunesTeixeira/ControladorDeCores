package com.example.colorsortingcontroller.data.localSource

import com.example.colorsortingcontroller.data.entities.Monitoramento
import com.example.colorsortingcontroller.data.dao.MonitoramentoDao
import kotlinx.coroutines.flow.Flow

class MonitoramentoLocalSource(private val monitoramentoDao: MonitoramentoDao){
    fun getAllMonitoramento(): Flow<List<Monitoramento>> {
        return monitoramentoDao.getAllMonitoramento()
    }

    suspend fun insertMonitoramento(estado: String, corAtual: String, sensorR: Float, sensorG: Float, sensorB: Float, portaAberta: Boolean, coletorAtual: Int){
        monitoramentoDao.insertMonitoramento(estado, corAtual, sensorR, sensorG, sensorB, portaAberta, coletorAtual)
    }

    suspend fun updateEstado(estado: String){
        monitoramentoDao.updateEstado(newEstado = estado)
    }

    suspend fun updateCorAtual(corAtual: String){
        monitoramentoDao.updateCorAtual(newCorAtual = corAtual)
    }

    suspend fun updateSensorR(sensorR: Float){
        monitoramentoDao.updateSensorR(newSensorR = sensorR)
    }
    suspend fun updateSensorG(sensorG: Float){
        monitoramentoDao.updateSensorG(newSensorG = sensorG)
    }

    suspend fun updateSensorB(sensorB: Float){
        monitoramentoDao.updateSensorB(newSensorB = sensorB)
    }

    suspend fun updatePortaAberta(portaAberta: Boolean){
        monitoramentoDao.updatePortaAberta(newPortaAberta = portaAberta)
    }

    suspend fun updateColetorAtual(coletorAtual: Int){
        monitoramentoDao.updateColetorAtual(newColetorAtual = coletorAtual)
    }

    suspend fun deleteMonitoramento(id: Int){
        monitoramentoDao.deleteMonitoramento(id)
    }
}