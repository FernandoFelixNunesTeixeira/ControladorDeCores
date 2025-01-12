package com.example.colorsortingcontroller.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.colorsortingcontroller.data.entities.Monitoramento
import kotlinx.coroutines.flow.Flow

@Dao
interface MonitoramentoDao {
    @Query("INSERT INTO monitoramento (estado, corAtual, sensorR, sensorG, sensorB, portaAberta, coletorAtual) VALUES (:estado, :corAtual, :sensorR, :sensorG, :sensorB, :portaAberta, :coletorAtual)")
    suspend fun insertMonitoramento(estado: String, corAtual: String, sensorR: Float, sensorG: Float, sensorB: Float, portaAberta:Boolean, coletorAtual: Int)



    @Query("UPDATE monitoramento SET estado = :newEstado WHERE id = 1")
    suspend fun updateEstado(newEstado: String)

    @Query("UPDATE monitoramento SET corAtual = :newCorAtual WHERE id = 1")
    suspend fun updateCorAtual(newCorAtual: String)

    @Query("UPDATE monitoramento SET sensorR = :newSensorR WHERE id = 1")
    suspend fun updateSensorR(newSensorR: Float)

    @Query("UPDATE monitoramento SET sensorG = :newSensorG WHERE id = 1")
    suspend fun updateSensorG(newSensorG: Float)

    @Query("UPDATE monitoramento SET sensorB = :newSensorB WHERE id = 1")
    suspend fun updateSensorB(newSensorB: Float)

    @Query("UPDATE monitoramento SET portaAberta = :newPortaAberta WHERE id = 1")
    suspend fun updatePortaAberta(newPortaAberta: Boolean)

    @Query("UPDATE monitoramento SET coletorAtual = :newColetorAtual WHERE id = 1")
    suspend fun updateColetorAtual(newColetorAtual: Int)




    @Query("DELETE FROM monitoramento WHERE id = :id")
    suspend fun deleteMonitoramento(id: Int)

    @Query("SELECT * FROM monitoramento")
    fun getAllMonitoramento(): Flow<List<Monitoramento>>
}