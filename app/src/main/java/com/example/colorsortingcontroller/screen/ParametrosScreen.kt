package com.example.colorsortingcontroller.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import com.example.colorsortingcontroller.ui.theme.ColorSortingControllerTheme
import kotlinx.coroutines.launch

@Composable
fun ParametrosScreen(viewModel: ParametrosViewModel = viewModel()) {

    val parametros by viewModel.parametros.collectAsState(initial = null)

    val coroutineScope = rememberCoroutineScope()

    var posicaoServoPortaMin by remember { mutableStateOf(parametros?.posicaoServoPortaMin ?: 90) }
    var posicaoServoPortaMax by remember { mutableStateOf(parametros?.posicaoServoPortaMax ?: 0) }

    var posicaoServoDirecionadorEDMin by remember { mutableStateOf(parametros?.posicaoServoDirecionadorEDMin ?: 72) }
    var posicaoServoDirecionadorEDMax by remember { mutableStateOf(parametros?.posicaoServoDirecionadorEDMax ?: 105) }

    var posicaoServoDirecionador12Min by remember { mutableStateOf(parametros?.posicaoServoDirecionador12Min ?: 70) }
    var posicaoServoDirecionador12Max by remember { mutableStateOf(parametros?.posicaoServoDirecionador12Max ?: 100) }

    var posicaoServoDirecionador34Min by remember { mutableStateOf(parametros?.posicaoServoDirecionador34Min ?: 80) }
    var posicaoServoDirecionador34Max by remember { mutableStateOf(parametros?.posicaoServoDirecionador34Max ?: 115) }

    var rValue by remember { mutableStateOf(parametros?.rValue ?: 255) }
    var gValue by remember { mutableStateOf(parametros?.gValue ?: 255) }
    var bValue by remember { mutableStateOf(parametros?.bValue ?: 255) }

    ColorSortingControllerTheme {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            AngleCard(
                label = "Posição Servo Porta",
                posicaoMin = posicaoServoPortaMin,
                posicaoMax = posicaoServoPortaMax,
                onMinChange = { posicaoServoPortaMin = it },
                onMaxChange = { posicaoServoPortaMax = it }
            )
            AngleCard(
                label = "Posição Servo Direcionador ED",
                posicaoMin = posicaoServoDirecionadorEDMin,
                posicaoMax = posicaoServoDirecionadorEDMax,
                onMinChange = { posicaoServoDirecionadorEDMin = it },
                onMaxChange = { posicaoServoDirecionadorEDMax = it }

            )
            AngleCard(
                label = "Posição Servo Direcionador 12",
                posicaoMin = posicaoServoDirecionador12Min,
                posicaoMax = posicaoServoDirecionador12Max,
                onMinChange = { posicaoServoDirecionador12Min = it },
                onMaxChange = { posicaoServoDirecionador12Max = it }
            )
            AngleCard(
                label = "Posição Servo Direcionador 34",
                posicaoMin = posicaoServoDirecionador34Min,
                posicaoMax = posicaoServoDirecionador34Max,
                onMinChange = { posicaoServoDirecionador34Min = it },
                onMaxChange = { posicaoServoDirecionador34Max = it }
            )
            RGBCard(
                label = "RGB Valores",
                R = rValue,
                G = gValue,
                B = bValue,
                onRChange = { rValue = it },
                onGChange = { gValue = it },
                onBChange = { bValue = it }
            )

            Button(
                onClick = {
                    // Utilização da coroutine para fins assíncronos
                    coroutineScope.launch {
                        viewModel.update(
                            posicaoServoPortaMin,
                            posicaoServoPortaMax,
                            posicaoServoDirecionadorEDMin,
                            posicaoServoDirecionadorEDMax,
                            posicaoServoDirecionador12Min,
                            posicaoServoDirecionador12Max,
                            posicaoServoDirecionador34Min,
                            posicaoServoDirecionador34Max,
                            rValue,
                            gValue,
                            bValue
                        )
                    }

                    viewModel.logAllParametros()
                },

                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Enviar")
            }
        }
    }
}

@Composable
fun AngleCard(
    label: String,
    posicaoMin: Int,
    posicaoMax: Int,
    onMinChange: (Int) -> Unit,
    onMaxChange: (Int) -> Unit
) {
    ColorSortingControllerTheme{
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Text(text = label)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Campo para o ângulo mínimo
                    InputField(value = posicaoMin, onValueChange = onMinChange)

                    // Campo para o ângulo máximo
                    InputField(value = posicaoMax, onValueChange = onMaxChange)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RGBCard(
    label: String,
    R: Int,
    G: Int,
    B: Int,
    onRChange: (Int) -> Unit,
    onGChange: (Int) -> Unit,
    onBChange: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedColor by remember { mutableStateOf("Vermelho") }

    val colorOptions = listOf("Vermelho", "Verde", "Azul", "Amarelo", "Roxo")

    ColorSortingControllerTheme {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(text = label)

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = it }
                ) {
                    OutlinedTextField(
                        value = selectedColor,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Selecione uma cor:") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                            focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface),
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        colorOptions.forEach { color ->
                            DropdownMenuItem(
                                text = { Text(color) },
                                onClick = {
                                    selectedColor = color
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    InputField(value = R, onValueChange = onRChange)
                    InputField(value = G, onValueChange = onGChange)
                    InputField(value = B, onValueChange = onBChange)
                }
            }
        }
    }
}

@Composable
fun InputField(value: Int, onValueChange: (Int) -> Unit) {
    var text by remember { mutableStateOf(value.toString()) }

    ColorSortingControllerTheme {
        BasicTextField(
            value = text,
            onValueChange = { newValue ->
                val filteredValue = newValue.filter { it.isDigit() }
                text = filteredValue

                filteredValue.toIntOrNull()?.let { numericValue ->
                    onValueChange(numericValue)
                }
            },
            textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onSurface),
            modifier = Modifier
                .width(80.dp)
                .height(40.dp)
                .padding(4.dp)
                .background(Color.Gray.copy(alpha = 0.3f), shape = MaterialTheme.shapes.small)
                .padding(4.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )
    }
}


