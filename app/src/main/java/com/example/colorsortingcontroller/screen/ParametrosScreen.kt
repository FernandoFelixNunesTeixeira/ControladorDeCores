package com.example.colorsortingcontroller.com.example.colorsortingcontroller.screen

import androidx.compose.foundation.background
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

// Tela de Parâmetros
@Composable
fun ParametrosScreen() {
    // Definir os valores iniciais para os ângulos
    var posicaoServoPortaMin by remember { mutableStateOf(90) }
    var posicaoServoPortaMax by remember { mutableStateOf(0) }

    var posicaoServoDirecionadorEDMin by remember { mutableStateOf(72) }
    var posicaoServoDirecionadorEDMax by remember { mutableStateOf(105) }

    var posicaoServoDirecionador12Min by remember { mutableStateOf(70) }
    var posicaoServoDirecionador12Max by remember { mutableStateOf(100) }

    var posicaoServoDirecionador34Min by remember { mutableStateOf(80) }
    var posicaoServoDirecionador34Max by remember { mutableStateOf(115) }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Bloco PosicaoServoPorta
        InfoCard(
            label = "Posição Servo Porta",
            posicaoMin = posicaoServoPortaMin,
            posicaoMax = posicaoServoPortaMax,
            onMinChange = { posicaoServoPortaMin = it },
            onMaxChange = { posicaoServoPortaMax = it }
        )

        // Bloco PosicaoServoDirecionadorED
        InfoCard(
            label = "Posição Servo Direcionador ED",
            posicaoMin = posicaoServoDirecionadorEDMin,
            posicaoMax = posicaoServoDirecionadorEDMax,
            onMinChange = { posicaoServoDirecionadorEDMin = it },
            onMaxChange = { posicaoServoDirecionadorEDMax = it }
        )

        // Bloco PosicaoServoDirecionador12
        InfoCard(
            label = "Posição Servo Direcionador 12",
            posicaoMin = posicaoServoDirecionador12Min,
            posicaoMax = posicaoServoDirecionador12Max,
            onMinChange = { posicaoServoDirecionador12Min = it },
            onMaxChange = { posicaoServoDirecionador12Max = it }
        )

        // Bloco PosicaoServoDirecionador34
        InfoCard(
            label = "Posição Servo Direcionador 34",
            posicaoMin = posicaoServoDirecionador34Min,
            posicaoMax = posicaoServoDirecionador34Max,
            onMinChange = { posicaoServoDirecionador34Min = it },
            onMaxChange = { posicaoServoDirecionador34Max = it }
        )

        Button(
            onClick = { /* Enviar parametros */ },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0B737E),
                contentColor = Color.White
            ),
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Enviar")
        }
    }
}

@Composable
fun InfoCard(
    label: String,
    posicaoMin: Int,
    posicaoMax: Int,
    onMinChange: (Int) -> Unit,
    onMaxChange: (Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFEAF5F7)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = label)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Campo para o ângulo mínimo
                AngleInputField(value = posicaoMin, onValueChange = onMinChange)

                // Campo para o ângulo máximo
                AngleInputField(value = posicaoMax, onValueChange = onMaxChange)
            }
        }
    }
}

@Composable
fun AngleInputField(value: Int, onValueChange: (Int) -> Unit) {
    var text by remember { mutableStateOf(value.toString()) }

    BasicTextField(
        value = text,
        onValueChange = { newValue ->
            val filteredValue = newValue.filter { it.isDigit() }
            text = filteredValue

            filteredValue.toIntOrNull()?.let { numericValue ->
                onValueChange(numericValue)
            }
        },
        modifier = Modifier
            .fillMaxWidth(0.4f)
            .padding(8.dp)
            .background(Color.Gray.copy(alpha = 0.2f), shape = MaterialTheme.shapes.small)
            .padding(8.dp),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        )
    )
}