package com.example.colorsortingcontroller.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ParametrosDao {

    // Update cada motor e cor

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

    // Mostrar tudo
    @Query("SELECT * FROM parametros WHERE id = 1")
    fun getAllParametros(): Flow<List<Parametros>>

}