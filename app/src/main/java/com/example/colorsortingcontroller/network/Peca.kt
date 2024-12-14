package com.example.colorsortingcontroller.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable



@Serializable
data class Peca (
    val id: String,
    @SerialName(value = "Cor1")
    val Cor1: String,
    @SerialName(value = "Cor2")
  val Cor2: String,
    @SerialName(value = "Cor3")
    val Cor3: String,
    @SerialName(value = "Cor4")
    val Cor4: String,
    @SerialName(value = "Quantidade_Cor1")
    val QuantidadeCor1: String,
    @SerialName(value = "Quantidade_Cor2")
    val QuantidadeCor2: String,
    @SerialName(value = "Quantidade_Cor3")
    val QuantidadeCor3: String,
    @SerialName(value = "Quantidade_Cor4")
    val QuantidadeCor4: String,
)

