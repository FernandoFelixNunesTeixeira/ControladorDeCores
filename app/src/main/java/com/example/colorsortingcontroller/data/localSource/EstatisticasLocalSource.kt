package com.example.colorsortingcontroller.data.localSource


import com.example.colorsortingcontroller.data.dao.EstatisticasDao
import com.example.colorsortingcontroller.data.entities.Estatisticas
import kotlinx.coroutines.flow.Flow

class EstatisticasLocalSource(private val estatisticasDao: EstatisticasDao){
    fun getAllEstatisticas(): Flow<List<Estatisticas>> {
        return estatisticasDao.getAllEstatisticas()
    }

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
        estatisticasDao.insertEstatisticas(
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

    suspend fun updatePecasCor1(pecasCor1: Int){
        estatisticasDao.updatePecasCor1(newPecasCor1 = pecasCor1)
    }

    suspend fun updatePecasCor2(pecasCor2: Int){
        estatisticasDao.updatePecasCor2(newPecasCor2 = pecasCor2)
    }

    suspend fun updatePecasCor3(pecasCor3: Int){
        estatisticasDao.updatePecasCor3(newPecasCor3 = pecasCor3)
    }

    suspend fun updatePecasCor4(pecasCor4: Int){
        estatisticasDao.updatePecasCor4(newPecasCor4 = pecasCor4)
    }

    suspend fun updatePecasCor5(pecasCor5: Int){
        estatisticasDao.updatePecasCor5(newPecasCor5 = pecasCor5)
    }

    suspend fun updatePecasCor6(pecasCor6: Int){
        estatisticasDao.updatePecasCor6(newPecasCor6 = pecasCor6)
    }

    suspend fun updatePecasCor7(pecasCor7: Int){
        estatisticasDao.updatePecasCor7(newPecasCor7 = pecasCor7)
    }



    suspend fun updatePecasColetor1(pecasColetor1: Int){
        estatisticasDao.updatePecasColetor1(newPecasColetor1 = pecasColetor1)
    }

    suspend fun updatePecasColetor2(pecasColetor2: Int){
        estatisticasDao.updatePecasColetor2(newPecasColetor2 = pecasColetor2)
    }

    suspend fun updatePecasColetor3(pecasColetor3: Int){
        estatisticasDao.updatePecasColetor3(newPecasColetor3 = pecasColetor3)
    }

    suspend fun updatePecasColetor4(pecasColetor4: Int){
        estatisticasDao.updatePecasColetor4(newPecasColetor4 = pecasColetor4)
    }

    suspend fun deleteEstatisticas(id: Int){
        estatisticasDao.deleteEstatisticas(id)
    }
}