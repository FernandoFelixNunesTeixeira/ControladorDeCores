package com.example.colorsortingcontroller.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    // Monitoramento

    @Query("INSERT INTO monitoramento (estado, corAtual) VALUES (:estado, :corAtual)")
    suspend fun insertMonitoramento(estado: String, corAtual: String)

    @Query("UPDATE monitoramento SET estado = :newEstado WHERE id = 1")
    suspend fun updateEstado(newEstado: String)

    @Query("UPDATE monitoramento SET corAtual = :newCorAtual WHERE id = 1")
    suspend fun updateCorAtual(newCorAtual: String)

    @Query("DELETE FROM monitoramento WHERE id = :id")
    suspend fun deleteMonitoramento(id: Int)

    @Query("SELECT * FROM monitoramento WHERE id = 1")
    fun getAllMonitoramento(): Flow<List<Monitoramento>>

    // Parametros
    @Query("INSERT INTO parametros(" +
            "posicaoServoPortaMin,posicaoServoPortaMax,posicaoServoDirecionadorEDMin,posicaoServoDirecionadorEDMax," +
            "posicaoServoDirecionador12Min,posicaoServoDirecionador12Max,posicaoServoDirecionador34Min," +
            "posicaoServoDirecionador34Max,cor, rValue,gValue,bValue) " +
            "VALUES (:posicaoServoPortaMin, :posicaoServoPortaMax,:posicaoServoDirecionadorEDMin,:posicaoServoDirecionadorEDMax," +
            ":posicaoServoDirecionador12Min, :posicaoServoDirecionador12Max,:posicaoServoDirecionador34Min," +
            ":posicaoServoDirecionador34Max, :cor, :rValue,:gValue,:bValue)"
    )
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
    )

    @Query("DELETE FROM parametros WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("UPDATE parametros SET posicaoServoPortaMin = :newPosicaoServoPortaMin WHERE id = 1")
    suspend fun updatePosicaoServoPortaMin(newPosicaoServoPortaMin: Int)

    @Query("UPDATE parametros SET posicaoServoPortaMax = :newPosicaoServoPortaMax WHERE id = 1")
    suspend fun updatePosicaoServoPortaMax(newPosicaoServoPortaMax: Int)

    @Query("UPDATE parametros SET posicaoServoDirecionadorEDMin = :newPosicaoServoDirecionadorEDMin WHERE id = 1")
    suspend fun updatePosicaoServoDirecionadorEDMin(newPosicaoServoDirecionadorEDMin: Int)

    @Query("UPDATE parametros SET posicaoServoDirecionadorEDMax = :newPosicaoServoDirecionadorEDMax WHERE id = 1")
    suspend fun updatePosicaoServoDirecionadorEDMax(newPosicaoServoDirecionadorEDMax: Int)

    @Query("UPDATE parametros SET posicaoServoDirecionador12Min = :newPosicaoServoDirecionador12Min WHERE id = 1")
    suspend fun updatePosicaoServoDirecionador12Min(newPosicaoServoDirecionador12Min: Int)

    @Query("UPDATE parametros SET posicaoServoDirecionador12Max = :newPosicaoServoDirecionador12Max WHERE id = 1")
    suspend fun updatePosicaoServoDirecionador12Max(newPosicaoServoDirecionador12Max: Int)

    @Query("UPDATE parametros SET posicaoServoDirecionador34Min = :newPosicaoServoDirecionador34Min WHERE id = 1")
    suspend fun updatePosicaoServoDirecionador34Min(newPosicaoServoDirecionador34Min: Int)

    @Query("UPDATE parametros SET posicaoServoDirecionador34Max = :newPosicaoServoDirecionador34Max WHERE id = 1")
    suspend fun updatePosicaoServoDirecionador34Max(newPosicaoServoDirecionador34Max: Int)

    @Query("UPDATE parametros SET cor = :newCor WHERE id = 1")
    suspend fun updateCor(newCor: String)

    @Query("UPDATE parametros SET rValue = :newRValue WHERE id = 1")
    suspend fun updateRValue(newRValue: Int)

    @Query("UPDATE parametros SET gValue = :newGValue WHERE id = 1")
    suspend fun updateGValue(newGValue: Int)

    @Query("UPDATE parametros SET bValue = :newBValue WHERE id = 1")
    suspend fun updateBValue(newBValue: Int)

    @Query("SELECT * FROM parametros WHERE id = 1")
    fun getAllParametros(): Flow<List<Parametros>>

    // Estatisticas

    @Query("INSERT INTO estatisticas (" +
            "pecasCor1, pecasCor2, pecasCor3, pecasCor4, pecasCor5, pecasCor6, pecasCor7, pecasCor8, " +
            "pecasColetor1, pecasColetor2, pecasColetor3, pecasColetor4) " +
            "VALUES (:pecasCor1, :pecasCor2, :pecasCor3, :pecasCor4, :pecasCor5, :pecasCor6, :pecasCor7, :pecasCor8," +
            ":pecasColetor1, :pecasColetor2, :pecasColetor3, :pecasColetor4)")
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
    )

    @Query("UPDATE estatisticas SET pecasCor1 = :newPecasCor1 WHERE id = 1")
    suspend fun updatePecasCor1(newPecasCor1: Int)

    @Query("UPDATE estatisticas SET pecasCor2 = :newPecasCor2 WHERE id = 1")
    suspend fun updatePecasCor2(newPecasCor2: Int)

    @Query("UPDATE estatisticas SET pecasCor3 = :newPecasCor3 WHERE id = 1")
    suspend fun updatePecasCor3(newPecasCor3: Int)

    @Query("UPDATE estatisticas SET pecasCor4 = :newPecasCor4 WHERE id = 1")
    suspend fun updatePecasCor4(newPecasCor4: Int)

    @Query("UPDATE estatisticas SET pecasCor5 = :newPecasCor5 WHERE id = 1")
    suspend fun updatePecasCor5(newPecasCor5: Int)

    @Query("UPDATE estatisticas SET pecasCor6 = :newPecasCor6 WHERE id = 1")
    suspend fun updatePecasCor6(newPecasCor6: Int)

    @Query("UPDATE estatisticas SET pecasCor7 = :newPecasCor7 WHERE id = 1")
    suspend fun updatePecasCor7(newPecasCor7: Int)

    @Query("UPDATE estatisticas SET pecasCor8 = :newPecasCor8 WHERE id = 1")
    suspend fun updatePecasCor8(newPecasCor8: Int)

    @Query("UPDATE estatisticas SET pecasColetor1 = :newPecasColetor1 WHERE id = 1")
    suspend fun updatePecasColetor1(newPecasColetor1: Int)

    @Query("UPDATE estatisticas SET pecasColetor2 = :newPecasColetor2 WHERE id = 1")
    suspend fun updatePecasColetor2(newPecasColetor2: Int)

    @Query("UPDATE estatisticas SET pecasColetor3 = :newPecasColetor3 WHERE id = 1")
    suspend fun updatePecasColetor3(newPecasColetor3: Int)

    @Query("UPDATE estatisticas SET pecasColetor4 = :newPecasColetor4 WHERE id = 1")
    suspend fun updatePecasColetor4(newPecasColetor4: Int)

    @Query("DELETE FROM estatisticas WHERE id = :id")
    suspend fun deleteEstatisticas(id: Int)

    @Query("SELECT * FROM estatisticas WHERE id = 1")
    fun getAllEstatisticas(): Flow<List<Estatisticas>>
}