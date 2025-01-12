package com.example.colorsortingcontroller.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.colorsortingcontroller.data.entities.Estatisticas
import kotlinx.coroutines.flow.Flow

@Dao
interface EstatisticasDao {
    @Query("INSERT INTO estatisticas (" +
            "pecasCor1, pecasCor2, pecasCor3, pecasCor4, pecasCor5, pecasCor6, pecasCor7, " +
            "pecasColetor1, pecasColetor2, pecasColetor3, pecasColetor4) " +
            "VALUES (:pecasCor1, :pecasCor2, :pecasCor3, :pecasCor4, :pecasCor5, :pecasCor6, :pecasCor7, " +
            ":pecasColetor1, :pecasColetor2, :pecasColetor3, :pecasColetor4)")
    suspend fun insertEstatisticas(
        pecasCor1: Int,
        pecasCor2: Int,
        pecasCor3: Int,
        pecasCor4: Int,
        pecasCor5: Int,
        pecasCor6: Int,
        pecasCor7: Int,

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

    @Query("SELECT * FROM estatisticas")
    fun getAllEstatisticas(): Flow<List<Estatisticas>>
}