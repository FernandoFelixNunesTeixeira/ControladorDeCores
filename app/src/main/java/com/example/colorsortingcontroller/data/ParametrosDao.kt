package com.example.colorsortingcontroller.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking

@Dao
interface ParametrosDao {

    // Update cada motor e cor

    @Query("UPDATE parametros SET posicaoServoPortaMin = :newPosicaoServoPortaMin")
    suspend fun updatePosicaoServoPortaMin(newPosicaoServoPortaMin: Int)

    @Query("UPDATE parametros SET posicaoServoPortaMax = :newPosicaoServoPortaMax")
    suspend fun updatePosicaoServoPortaMax(newPosicaoServoPortaMax: Int)

    @Query("UPDATE parametros SET posicaoServoDirecionadorEDMin = :newPosicaoServoDirecionadorEDMin")
    suspend fun updatePosicaoServoDirecionadorEDMin(newPosicaoServoDirecionadorEDMin: Int)

    @Query("UPDATE parametros SET posicaoServoDirecionadorEDMax = :newPosicaoServoDirecionadorEDMax")
    suspend fun updatePosicaoServoDirecionadorEDMax(newPosicaoServoDirecionadorEDMax: Int)

    @Query("UPDATE parametros SET posicaoServoDirecionador12Min = :newPosicaoServoDirecionador12Min")
    suspend fun updatePosicaoServoDirecionador12Min(newPosicaoServoDirecionador12Min: Int)

    @Query("UPDATE parametros SET posicaoServoDirecionador12Max = :newPosicaoServoDirecionador12Max")
    suspend fun updatePosicaoServoDirecionador12Max(newPosicaoServoDirecionador12Max: Int)

    @Query("UPDATE parametros SET posicaoServoDirecionador34Min = :newPosicaoServoDirecionador34Min")
    suspend fun updatePosicaoServoDirecionador34Min(newPosicaoServoDirecionador34Min: Int)

    @Query("UPDATE parametros SET posicaoServoDirecionador34Max = :newPosicaoServoDirecionador34Max")
    suspend fun updatePosicaoServoDirecionador34Max(newPosicaoServoDirecionador34Max: Int)

    @Query("UPDATE parametros SET rValue = :newRValue")
    suspend fun updateRValue(newRValue: Int)

    @Query("UPDATE parametros SET rValue = :newGValue")
    suspend fun updateGValue(newGValue: Int)

    @Query("UPDATE parametros SET rValue = :newBValue")
    suspend fun updateBValue(newBValue: Int)

    // Mostrar tudo

    @Query("SELECT * FROM parametros")
    fun getAllParametros(): Flow<List<Parametros>>


}