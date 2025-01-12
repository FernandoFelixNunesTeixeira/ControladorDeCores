package com.example.colorsortingcontroller.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.colorsortingcontroller.data.entities.Parametros
import kotlinx.coroutines.flow.Flow

@Dao
interface ParametrosDao {
    @Query("INSERT INTO parametros(" +
            "posicaoServoPortaMin,posicaoServoPortaMax,posicaoServoDirecionadorEDMin,posicaoServoDirecionadorEDMax," +
            "posicaoServoDirecionador12Min,posicaoServoDirecionador12Max,posicaoServoDirecionador34Min," +
            "posicaoServoDirecionador34Max, RCor1,\n" +
            "            GCor1,\n" +
            "            BCor1,\n" +
            "            coletorCor1,\n" +
            "            RCor2,\n" +
            "            GCor2,\n" +
            "            BCor2,\n" +
            "            coletorCor2,\n" +
            "            RCor3,\n" +
            "            GCor3,\n" +
            "            BCor3,\n" +
            "            coletorCor3,\n" +
            "            RCor4,\n" +
            "            GCor4,\n" +
            "            BCor4,\n" +
            "            coletorCor4,\n" +
            "            RCor5,\n" +
            "            GCor5,\n" +
            "            BCor5,\n" +
            "            coletorCor5,\n" +
            "            RCor6,\n" +
            "            GCor6,\n" +
            "            BCor6,\n" +
            "            coletorCor6,\n" +
            "            RCor7,\n" +
            "            GCor7,\n" +
            "            BCor7,\n" +
            "            coletorCor7) " +
            "VALUES (:posicaoServoPortaMin, :posicaoServoPortaMax,:posicaoServoDirecionadorEDMin,:posicaoServoDirecionadorEDMax," +
            ":posicaoServoDirecionador12Min, :posicaoServoDirecionador12Max,:posicaoServoDirecionador34Min," +
            ":posicaoServoDirecionador34Max,  :RCor1,  :GCor1,  :BCor1,  :coletorCor1," +
            ":RCor2,  :GCor2,  :BCor2,  :coletorCor2," +
            ":RCor3,  :GCor3,  :BCor3,  :coletorCor3," +
            ":RCor4,  :GCor4,  :BCor4,  :coletorCor4," +
            ":RCor5,  :GCor5,  :BCor5,  :coletorCor5," +
            ":RCor6,  :GCor6,  :BCor6,  :coletorCor6," +
            ":RCor7,  :GCor7,  :BCor7,  :coletorCor7 )"
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
        RCor1: Float,
        GCor1: Float,
        BCor1: Float,
        coletorCor1: Int,
        RCor2: Float,
        GCor2: Float,
        BCor2: Float,
        coletorCor2: Int,
        RCor3: Float,
        GCor3: Float,
        BCor3: Float,
        coletorCor3: Int,
        RCor4: Float,
        GCor4: Float,
        BCor4: Float,
        coletorCor4: Int,
        RCor5: Float,
        GCor5: Float,
        BCor5: Float,
        coletorCor5: Int,
        RCor6: Float,
        GCor6: Float,
        BCor6: Float,
        coletorCor6: Int,
        RCor7: Float,
        GCor7: Float,
        BCor7: Float,
        coletorCor7: Int,
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

    @Query("UPDATE parametros SET coletorCor1 = :newColetorCor1 WHERE id = 1")
    suspend fun updateColetorCor1(newColetorCor1: Int)

    @Query("UPDATE parametros SET RCor1 = :newR1Value WHERE id = 1")
    suspend fun updateR1Value(newR1Value: Float)

    @Query("UPDATE parametros SET GCor1 = :newG1Value WHERE id = 1")
    suspend fun updateG1Value(newG1Value: Float)

    @Query("UPDATE parametros SET BCor1 = :newB1Value WHERE id = 1")
    suspend fun updateB1Value(newB1Value: Float)


    @Query("UPDATE parametros SET coletorCor2 = :newColetorCor2 WHERE id = 1")
    suspend fun updateColetorCor2(newColetorCor2: Int)

    @Query("UPDATE parametros SET RCor2 = :newR2Value WHERE id = 1")
    suspend fun updateR2Value(newR2Value: Float)

    @Query("UPDATE parametros SET GCor2 = :newG2Value WHERE id = 1")
    suspend fun updateG2Value(newG2Value: Float)

    @Query("UPDATE parametros SET BCor2 = :newB2Value WHERE id = 1")
    suspend fun updateB2Value(newB2Value: Float)





    @Query("UPDATE parametros SET coletorCor3 = :newColetorCor3 WHERE id = 1")
    suspend fun updateColetorCor3(newColetorCor3: Int)

    @Query("UPDATE parametros SET RCor3 = :newR3Value WHERE id = 1")
    suspend fun updateR3Value(newR3Value: Float)

    @Query("UPDATE parametros SET GCor3 = :newG3Value WHERE id = 1")
    suspend fun updateG3Value(newG3Value: Float)

    @Query("UPDATE parametros SET BCor3 = :newB3Value WHERE id = 1")
    suspend fun updateB3Value(newB3Value: Float)



    @Query("UPDATE parametros SET coletorCor4 = :newColetorCor4 WHERE id = 1")
    suspend fun updateColetorCor4(newColetorCor4: Int)

    @Query("UPDATE parametros SET RCor4 = :newR4Value WHERE id = 1")
    suspend fun updateR4Value(newR4Value: Float)

    @Query("UPDATE parametros SET GCor4 = :newG4Value WHERE id = 1")
    suspend fun updateG4Value(newG4Value: Float)

    @Query("UPDATE parametros SET BCor4 = :newB4Value WHERE id = 1")
    suspend fun updateB4Value(newB4Value: Float)


    @Query("UPDATE parametros SET coletorCor5 = :newColetorCor5 WHERE id = 1")
    suspend fun updateColetorCor5(newColetorCor5: Int)

    @Query("UPDATE parametros SET RCor5 = :newR5Value WHERE id = 1")
    suspend fun updateR5Value(newR5Value: Float)

    @Query("UPDATE parametros SET GCor5 = :newG5Value WHERE id = 1")
    suspend fun updateG5Value(newG5Value: Float)

    @Query("UPDATE parametros SET BCor5 = :newB5Value WHERE id = 1")
    suspend fun updateB5Value(newB5Value: Float)



    @Query("UPDATE parametros SET coletorCor6 = :newColetorCor6 WHERE id = 1")
    suspend fun updateColetorCor6(newColetorCor6: Int)

    @Query("UPDATE parametros SET RCor6 = :newR6Value WHERE id = 1")
    suspend fun updateR6Value(newR6Value: Float)

    @Query("UPDATE parametros SET GCor6 = :newG6Value WHERE id = 1")
    suspend fun updateG6Value(newG6Value: Float)

    @Query("UPDATE parametros SET BCor6 = :newB6Value WHERE id = 1")
    suspend fun updateB6Value(newB6Value: Float)


    @Query("UPDATE parametros SET coletorCor7 = :newColetorCor7 WHERE id = 1")
    suspend fun updateColetorCor7(newColetorCor7: Int)

    @Query("UPDATE parametros SET RCor7 = :newR7Value WHERE id = 1")
    suspend fun updateR7Value(newR7Value: Float)

    @Query("UPDATE parametros SET GCor7 = :newG7Value WHERE id = 1")
    suspend fun updateG7Value(newG7Value: Float)

    @Query("UPDATE parametros SET BCor7 = :newB7Value WHERE id = 1")
    suspend fun updateB7Value(newB7Value: Float)




    @Query("SELECT * FROM parametros")
    fun getAllParametros(): Flow<List<Parametros>>
}