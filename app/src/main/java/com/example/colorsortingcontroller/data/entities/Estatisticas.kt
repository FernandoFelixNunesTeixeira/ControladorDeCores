package com.example.colorsortingcontroller.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "estatisticas")
data class Estatisticas (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "pecasCor1")
    val pecasCor1: Int,
    @ColumnInfo(name = "pecasCor2")
    val pecasCor2: Int,
    @ColumnInfo(name = "pecasCor3")
    val pecasCor3: Int,
    @ColumnInfo(name = "pecasCor4")
    val pecasCor4: Int,
    @ColumnInfo(name = "pecasCor5")
    val pecasCor5: Int,
    @ColumnInfo(name = "pecasCor6")
    val pecasCor6: Int,
    @ColumnInfo(name = "pecasCor7")
    val pecasCor7: Int,


    @ColumnInfo(name = "pecasColetor1")
    val pecasColetor1: Int,
    @ColumnInfo(name = "pecasColetor2")
    val pecasColetor2: Int,
    @ColumnInfo(name = "pecasColetor3")
    val pecasColetor3: Int,
    @ColumnInfo(name = "pecasColetor4")
    val pecasColetor4: Int
)