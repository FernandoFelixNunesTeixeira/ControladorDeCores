package com.example.colorsortingcontroller.data.repository

import com.example.colorsortingcontroller.data.entities.Estatisticas
import com.example.colorsortingcontroller.data.localSource.EstatisticasLocalSource
import kotlinx.coroutines.flow.Flow

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

    ){
        estatisticasSource.updatePecasCor1(pecasCor1 = pecasCor1)
        estatisticasSource.updatePecasCor2(pecasCor2 = pecasCor2)
        estatisticasSource.updatePecasCor3(pecasCor3 = pecasCor3)
        estatisticasSource.updatePecasCor4(pecasCor4 = pecasCor4)
        estatisticasSource.updatePecasCor5(pecasCor5 = pecasCor5)
        estatisticasSource.updatePecasCor6(pecasCor6 = pecasCor6)
        estatisticasSource.updatePecasCor7(pecasCor7 = pecasCor7)
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