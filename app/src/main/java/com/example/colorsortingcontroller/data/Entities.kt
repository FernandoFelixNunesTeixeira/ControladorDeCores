package com.example.colorsortingcontroller.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "monitoramento")
data class Monitoramento (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "estado")
    val estado: String,
    @ColumnInfo(name = "corAtual")
    val corAtual: String
)

@Entity(tableName = "parametros")
data class Parametros (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "posicaoServoPortaMin")
    val posicaoServoPortaMin: Int,
    @ColumnInfo(name = "posicaoServoPortaMax")
    val posicaoServoPortaMax: Int,

    @ColumnInfo(name = "posicaoServoDirecionadorEDMin")
    val posicaoServoDirecionadorEDMin: Int,
    @ColumnInfo(name = "posicaoServoDirecionadorEDMax")
    val posicaoServoDirecionadorEDMax: Int,

    @ColumnInfo(name = "posicaoServoDirecionador12Min")
    val posicaoServoDirecionador12Min: Int,
    @ColumnInfo(name = "posicaoServoDirecionador12Max")
    val posicaoServoDirecionador12Max: Int,

    @ColumnInfo(name = "posicaoServoDirecionador34Min")
    val posicaoServoDirecionador34Min: Int,
    @ColumnInfo(name = "posicaoServoDirecionador34Max")
    val posicaoServoDirecionador34Max: Int,

    @ColumnInfo(name = "cor")
    val cor: String,

    @ColumnInfo(name = "rValue")
    val rValue: Int,
    @ColumnInfo(name = "gValue")
    val gValue: Int,
    @ColumnInfo(name = "bValue")
    val bValue: Int
)

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
    @ColumnInfo(name = "pecasCor8")
    val pecasCor8: Int,

    @ColumnInfo(name = "pecasColetor1")
    val pecasColetor1: Int,
    @ColumnInfo(name = "pecasColetor2")
    val pecasColetor2: Int,
    @ColumnInfo(name = "pecasColetor3")
    val pecasColetor3: Int,
    @ColumnInfo(name = "pecasColetor4")
    val pecasColetor4: Int
)