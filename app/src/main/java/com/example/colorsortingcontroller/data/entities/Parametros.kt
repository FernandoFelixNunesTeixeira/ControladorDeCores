package com.example.colorsortingcontroller.data.entities

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


    @ColumnInfo(name = "RCor1")
    var RCor1: Float,
    @ColumnInfo(name = "GCor1")
    var GCor1: Float,
    @ColumnInfo(name = "BCor1")
    var BCor1: Float,
    @ColumnInfo(name = "coletorCor1")
    var coletorCor1: Int,
    @ColumnInfo(name = "RCor2")
    var RCor2: Float,
    @ColumnInfo(name = "GCor2")
    var GCor2: Float,
    @ColumnInfo(name = "BCor2")
    var BCor2: Float,
    @ColumnInfo(name = "coletorCor2")
    var coletorCor2: Int,
    @ColumnInfo(name = "RCor3")
    var RCor3: Float,
    @ColumnInfo(name = "GCor3")
    var GCor3: Float,
    @ColumnInfo(name = "BCor3")
    var BCor3: Float,
    @ColumnInfo(name = "coletorCor3")
    var coletorCor3: Int,
    @ColumnInfo(name = "RCor4")
    var RCor4: Float,
    @ColumnInfo(name = "GCor4")
    var GCor4: Float,
    @ColumnInfo(name = "BCor4")
    var BCor4: Float,
    @ColumnInfo(name = "coletorCor4")
    var coletorCor4: Int,
    @ColumnInfo(name = "RCor5")
    var RCor5: Float,
    @ColumnInfo(name = "GCor5")
    var GCor5: Float,
    @ColumnInfo(name = "BCor5")
    var BCor5: Float,
    @ColumnInfo(name = "coletorCor5")
    var coletorCor5: Int,
    @ColumnInfo(name = "RCor6")
    var RCor6: Float,
    @ColumnInfo(name = "GCor6")
    var GCor6: Float,
    @ColumnInfo(name = "BCor6")
    var BCor6: Float,
    @ColumnInfo(name = "coletorCor6")
    var coletorCor6: Int,
    @ColumnInfo(name = "RCor7")
    var RCor7: Float,
    @ColumnInfo(name = "GCor7")
    var GCor7: Float,
    @ColumnInfo(name = "BCor7")
    var BCor7: Float,
    @ColumnInfo(name = "coletorCor7")
    var coletorCor7: Int
)