package com.example.colorsortingcontroller.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Maquina (
    val id: String,
    @SerialName(value = "Motor1")
    val Motor1: String,
    @SerialName(value = "Motor2")
    val Motor2: String,
    @SerialName(value = "Motor3")
    val Motor3: String,
    @SerialName(value = "Motor4")
    val Motor4: String,
    @SerialName(value = "Tempo_Funcionamento")
    val TempoFuncionamento: String,

)

