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
        cor: String,
        rValue: Int,
        gValue: Int,
        bValue: Int
    ){ parametrosSource.insert(
            posicaoServoPortaMin,
            posicaoServoPortaMax,
            posicaoServoDirecionadorEDMin,
            posicaoServoDirecionadorEDMax,
            posicaoServoDirecionador12Min,
            posicaoServoDirecionador12Max,
            posicaoServoDirecionador34Min,
            posicaoServoDirecionador34Max,
            cor,
            rValue,
            gValue,
            bValue
        )
    }

    suspend fun deleteParametros(id: Int){
        parametrosSource.delete(id)
    }


    // Cor RGB
    suspend fun updateRGBValues(rValue: Int, gValue: Int, bValue: Int) {
        parametrosSource.updateRValue(
            rValue = rValue
        )
        parametrosSource.updateGValue(
            gValue = gValue
        )
        parametrosSource.updateBValue(
            bValue = bValue
        )
    }

    suspend fun updateCor(cor: String){
        parametrosSource.updateCor(
            cor = cor
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

class EstatisticasRepository(private val estatisticasSource: EstatisticasLocalSource){
    val allEstatisticas: Flow<List<Estatisticas>> = estatisticasSource.getAllEstatisticas()

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
    ){
        estatisticasSource.insertEstatisticas(
            pecasCor1,
            pecasCor2,
            pecasCor3,
            pecasCor4,
            pecasCor5,
            pecasCor6,
            pecasCor7,
            pecasCor8,
            pecasColetor1,
            pecasColetor2,
            pecasColetor3,
            pecasColetor4
        )
    }

    suspend fun updatePecasCor(
        pecasCor1: Int,
        pecasCor2: Int,
        pecasCor3: Int,
        pecasCor4: Int,
        pecasCor5: Int,
        pecasCor6: Int,
        pecasCor7: Int,
        pecasCor8: Int
    ){
        estatisticasSource.updatePecasCor1(pecasCor1 = pecasCor1)
        estatisticasSource.updatePecasCor2(pecasCor2 = pecasCor2)
        estatisticasSource.updatePecasCor3(pecasCor3 = pecasCor3)
        estatisticasSource.updatePecasCor4(pecasCor4 = pecasCor4)
        estatisticasSource.updatePecasCor5(pecasCor5 = pecasCor5)
        estatisticasSource.updatePecasCor6(pecasCor6 = pecasCor6)
        estatisticasSource.updatePecasCor7(pecasCor7 = pecasCor7)
        estatisticasSource.updatePecasCor8(pecasCor8 = pecasCor8)
    }

    suspend fun updatePecasColetor(
        pecasColetor1: Int,
        pecasColetor2: Int,
        pecasColetor3: Int,
        pecasColetor4: Int
    ){
        estatisticasSource.updatePecasColetor1(pecasColetor1 = pecasColetor1)
        estatisticasSource.updatePecasColetor2(pecasColetor2 = pecasColetor2)
        estatisticasSource.updatePecasColetor3(pecasColetor3 = pecasColetor3)
        estatisticasSource.updatePecasColetor4(pecasColetor4 = pecasColetor4)
    }

    suspend fun deleteEstatisticas(id: Int){
        estatisticasSource.deleteEstatisticas(id)
    }
}