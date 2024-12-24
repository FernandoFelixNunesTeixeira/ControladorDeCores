package com.example.colorsortingcontroller.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Parametros::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun parametrosDao(): ParametrosDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(
            context: Context,
            applicationScope: CoroutineScope
        ): AppDatabase {

            return Instance ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "parametros-db"
                ).fallbackToDestructiveMigration()
                    .build()

                    //.also{ Instance = it}
                Instance = instance
                // return instance
                instance
            }
        }
    }
}