package com.example.colorsortingcontroller.data.repository

import com.example.colorsortingcontroller.data.entities.Parametros
import com.example.colorsortingcontroller.data.localSource.ParametrosLocalSource
import kotlinx.coroutines.flow.Flow


class ParametrosRepository(private val parametrosSource: ParametrosLocalSource) {
    val allParametros: Flow<List<Parametros>> = parametrosSource.getAllParametros()

    //Inserir parametros no banco de dados
    suspend fun insertParametros(
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
        coletorCor7: Int
    ){ parametrosSource.insert(
            posicaoServoPortaMin,
            posicaoServoPortaMax,
            posicaoServoDirecionadorEDMin,
            posicaoServoDirecionadorEDMax,
            posicaoServoDirecionador12Min,
            posicaoServoDirecionador12Max,
            posicaoServoDirecionador34Min,
            posicaoServoDirecionador34Max,
        RCor1,
        GCor1,
        BCor1,
        coletorCor1,
        RCor2,
        GCor2,
        BCor2,
        coletorCor2,
        RCor3,
        GCor3,
        BCor3,
        coletorCor3,
        RCor4,
        GCor4,
        BCor4,
        coletorCor4,
        RCor5,
        GCor5,
        BCor5,
        coletorCor5,
        RCor6,
        GCor6,
        BCor6,
        coletorCor6,
        RCor7,
        GCor7,
        BCor7,
        coletorCor7
        )
    }

    suspend fun deleteParametros(id: Int){
        parametrosSource.delete(id)
    }


    // Cor RGB
    suspend fun updateRGBValues(r1Value: Float, g1Value: Float, b1Value: Float,
                                r2Value: Float, g2Value: Float, b2Value: Float,
                                r3Value: Float, g3Value: Float, b3Value: Float,
                                r4Value: Float, g4Value: Float, b4Value: Float,
                                r5Value: Float, g5Value: Float, b5Value: Float,
                                r6Value: Float, g6Value: Float, b6Value: Float,
                                r7Value: Float, g7Value: Float, b7Value: Float,
    ) {
        parametrosSource.updateR1Value(
            r1Value = r1Value
        )
        parametrosSource.updateG1Value(
            g1Value = g1Value
        )
        parametrosSource.updateB1Value(
            b1Value = b1Value
        )

        parametrosSource.updateR2Value(
            r2Value = r2Value
        )
        parametrosSource.updateG2Value(
            g2Value = g2Value
        )
        parametrosSource.updateB2Value(
            b2Value = b2Value
        )

        parametrosSource.updateR3Value(
            r3Value = r3Value
        )
        parametrosSource.updateG3Value(
            g3Value = g3Value
        )
        parametrosSource.updateB3Value(
            b3Value = b3Value
        )


        parametrosSource.updateR4Value(
            r4Value = r4Value
        )
        parametrosSource.updateG4Value(
            g4Value = g4Value
        )
        parametrosSource.updateB4Value(
            b4Value = b4Value
        )



        parametrosSource.updateR5Value(
            r5Value = r5Value
        )
        parametrosSource.updateG5Value(
            g5Value = g5Value
        )
        parametrosSource.updateB5Value(
            b5Value = b5Value
        )



        parametrosSource.updateR6Value(
            r6Value = r6Value
        )
        parametrosSource.updateG6Value(
            g6Value = g6Value
        )
        parametrosSource.updateB6Value(
            b6Value = b6Value
        )


        parametrosSource.updateR7Value(
            r7Value = r7Value
        )
        parametrosSource.updateG7Value(
            g7Value = g7Value
        )
        parametrosSource.updateB7Value(
            b7Value = b7Value
        )
    }

    suspend fun updateColetoresCor(coletorCor1: Int, coletorCor2: Int, coletorCor3: Int,
                                   coletorCor4: Int, coletorCor5: Int,
                                   coletorCor6: Int, coletorCor7: Int ){
        parametrosSource.updateColetorCor1(
            coletorCor1 = coletorCor1
        )
        parametrosSource.updateColetorCor2(
            coletorCor2 = coletorCor2
        )
        parametrosSource.updateColetorCor3(
            coletorCor3 = coletorCor3
        )
        parametrosSource.updateColetorCor4(
            coletorCor4 = coletorCor4
        )
        parametrosSource.updateColetorCor5(
            coletorCor5 = coletorCor5
        )
        parametrosSource.updateColetorCor6(
            coletorCor6 = coletorCor6
        )
        parametrosSource.updateColetorCor7(
            coletorCor7 = coletorCor7
        )
    }

    // Motor Porta
    suspend fun updateDirecionadorPorta(posicaoServoPortaMin: Int, posicaoServoPortaMax: Int) {
        parametrosSource.updatePosicaoServoPortaMin(
            posicaoServoPortaMin = posicaoServoPortaMin
        )
        parametrosSource.updatePosicaoServoPortaMax(
            posicaoServoPortaMax = posicaoServoPortaMax
        )
    }

    // Motor ED
    suspend fun updateDirecionadorED(posicaoServoDirecionadorEDMin: Int, posicaoServoDirecionadorEDMax: Int) {
        parametrosSource.updatePosicaoServoDirecionadorEDMin(
            posicaoServoDirecionadorEDMin = posicaoServoDirecionadorEDMin
        )
        parametrosSource.updatePosicaoServoDirecionadorEDMax(
            posicaoServoDirecionadorEDMax = posicaoServoDirecionadorEDMax
        )
    }

    // Motor 12
    suspend fun updateDirecionador12(posicaoServoDirecionador12Min: Int, posicaoServoDirecionador12Max: Int) {
        parametrosSource.updatePosicaoServoDirecionador12Min(
            posicaoServoDirecionador12Min = posicaoServoDirecionador12Min
        )
        parametrosSource.updatePosicaoServoDirecionador12Max(
            posicaoServoDirecionador12Max = posicaoServoDirecionador12Max
        )
    }

    // Motor 34
    suspend fun updateDirecionador34(posicaoServoDirecionador34Min: Int, posicaoServoDirecionador34Max: Int) {
        parametrosSource.updatePosicaoServoDirecionador34Min(
            posicaoServoDirecionador34Min = posicaoServoDirecionador34Min
        )
        parametrosSource.updatePosicaoServoDirecionador34Max(
            posicaoServoDirecionador34Max = posicaoServoDirecionador34Max
        )
    }
}

