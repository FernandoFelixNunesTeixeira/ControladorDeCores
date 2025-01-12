package com.example.colorsortingcontroller.data.entities

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
    val corAtual: String,


    @ColumnInfo(name = "sensorR")
    val sensorR: Float,
    @ColumnInfo(name = "sensorG")
    val sensorG: Float,
    @ColumnInfo(name = "sensorB")
    val sensorB: Float,
    @ColumnInfo(name = "portaAberta")
    val portaAberta: Boolean,
    @ColumnInfo(name = "coletorAtual")
    val coletorAtual: Int,


    )