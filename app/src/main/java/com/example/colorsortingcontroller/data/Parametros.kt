package com.example.colorsortingcontroller.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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