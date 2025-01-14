package com.example.colorsortingcontroller.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.colorsortingcontroller.data.dao.EstatisticasDao
import com.example.colorsortingcontroller.data.dao.MonitoramentoDao
import com.example.colorsortingcontroller.data.dao.ParametrosDao
import com.example.colorsortingcontroller.data.entities.Estatisticas
import com.example.colorsortingcontroller.data.entities.Monitoramento
import com.example.colorsortingcontroller.data.entities.Parametros

@Database(entities = [Monitoramento::class, Parametros::class, Estatisticas::class],
    version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun parametrosDao(): ParametrosDao
    abstract fun monitoramentoDao(): MonitoramentoDao
    abstract fun estatisticasDao(): EstatisticasDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(
            context: Context

        ): AppDatabase {

            return Instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "color-sorting-db"
                ).fallbackToDestructiveMigration()
                    .build()

                Instance = instance

                instance
            }
        }
    }
}