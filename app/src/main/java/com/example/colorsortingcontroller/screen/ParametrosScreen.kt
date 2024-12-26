package com.example.colorsortingcontroller.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.asFlow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.colorsortingcontroller.network.MQTTHandler
import com.example.colorsortingcontroller.ui.theme.ColorSortingControllerTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


@Composable
fun ParametrosScreen(viewModel: ParametrosViewModel = viewModel()) {
    //Inicializar de forma mais adeqauda depois
    //var viewModel2: MQTTHandler = viewModel()


    val parametros by viewModel.parametros.collectAsState(initial = null)
    val uiState by viewModel.state2.collectAsState()
    val mensagemMQTT by viewModel.mensagemMQTT.asFlow().collectAsStateWithLifecycle(initialValue = "{\n" +
            "\t\"PosicaoServoPortaAnguloMinimo\" : \"14\",\n" +
            "\t\"PosicaoServoPortaAnguloMaximo\" : \"89\",\n" +
            "\t\"PosicaoServoDirecionadorEDAnguloMinimo\" : \"13\",\n" +
            "\t\"PosicaoServoDirecionadorEDAnguloMaximo\" : \"97\",\n" +
            "\t\"PosicaoServoDirecionador12AnguloMinimo\" : \"75\",\n" +
            "\t\"PosicaoServoDirecionador12AnguloMaximo\" : \"82\",\n" +
            "\t\"PosicaoServoDirecionador34AnguloMinimo\" : \"27\",\n" +
            "\t\"PosicaoServoDirecionador34AnguloMaximo\" : \"14\",\n" +
            "\t\"R\" : \"100\",\n" +
            "\t\"G\" : \"200\",\n" +
            "\t\"B\" : \"150\",\n" +
            "\t\"Cor\":\"25\"\n" +
            "}")
    val coroutineScope = rememberCoroutineScope()

    // Banco de Dados
    val parametrosList by viewModel.allParametros.collectAsState(initial = emptyList())

    val bdPosicaoServoPortaMin = if (parametrosList.isNotEmpty()) parametrosList[0].posicaoServoPortaMin else 0
    val bdPosicaoServoPortaMax = if (parametrosList.isNotEmpty()) parametrosList[0].posicaoServoPortaMax else 0
    val bdPosicaoServoDirecionadorEDMin = if (parametrosList.isNotEmpty()) parametrosList[0].posicaoServoDirecionadorEDMin else 0
    val bdPosicaoServoDirecionadorEDMax = if (parametrosList.isNotEmpty()) parametrosList[0].posicaoServoDirecionadorEDMax else 0
    val bdPosicaoServoDirecionador12Min = if (parametrosList.isNotEmpty()) parametrosList[0].posicaoServoDirecionador12Min else 0
    val bdPosicaoServoDirecionador12Max = if (parametrosList.isNotEmpty()) parametrosList[0].posicaoServoDirecionador12Max else 0
    val bdPosicaoServoDirecionador34Min = if (parametrosList.isNotEmpty()) parametrosList[0].posicaoServoDirecionador34Min else 0
    val bdPosicaoServoDirecionador34Max = if (parametrosList.isNotEmpty()) parametrosList[0].posicaoServoDirecionador34Max else 0
    val bdCor = if (parametrosList.isNotEmpty()) parametrosList[0].cor else 0

    val bdRValue = if (parametrosList.isNotEmpty()) parametrosList[0].rValue else 0
    val bdGValue = if (parametrosList.isNotEmpty()) parametrosList[0].gValue else 0
    val bdBValue = if (parametrosList.isNotEmpty()) parametrosList[0].bValue else 0

    // Variáveis para INPUT

    var posicaoServoPortaMin by rememberSaveable { mutableStateOf(uiState.posicaoServoPortaMin) }
    var posicaoServoPortaMax by rememberSaveable { mutableStateOf(uiState.posicaoServoPortaMax) }

    var posicaoServoDirecionadorEDMin by rememberSaveable { mutableStateOf(uiState.posicaoServoDirecionadorEDMin) }
    var posicaoServoDirecionadorEDMax by rememberSaveable { mutableStateOf(uiState.posicaoServoDirecionadorEDMax) }

    var posicaoServoDirecionador12Min by rememberSaveable { mutableStateOf(uiState.posicaoServoDirecionador12Min) }
    var posicaoServoDirecionador12Max by rememberSaveable { mutableStateOf(uiState.posicaoServoDirecionadorEDMax) }

    var posicaoServoDirecionador34Min by rememberSaveable { mutableStateOf(uiState.posicaoServoDirecionador34Min) }
    var posicaoServoDirecionador34Max by rememberSaveable { mutableStateOf(uiState.posicaoServoDirecionador34Max) }

    var cor by rememberSaveable { mutableStateOf(uiState.cor) }

    var rValue by rememberSaveable { mutableStateOf(uiState.rValue) }
    var gValue by rememberSaveable { mutableStateOf(uiState.gValue) }
    var bValue by rememberSaveable { mutableStateOf(uiState.bValue) }

    val scrollState = rememberScrollState()

    ColorSortingControllerTheme {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
        ///    viewModel.ReceberAtualizar()
            // Exibe os valores da uiState
            Text("UI State - Posição Servo Porta Min: ${uiState.posicaoServoPortaMin}, Max: ${uiState.posicaoServoPortaMax}")
            Text("UI State - Posição Servo Direcionador ED Min: ${uiState.posicaoServoDirecionadorEDMin}, Max: ${uiState.posicaoServoDirecionadorEDMax}")
            Text("UI State - Posição Servo Direcionador 12 Min: ${uiState.posicaoServoDirecionador12Min}, Max: ${uiState.posicaoServoDirecionador12Max}")
            Text("UI State - Posição Servo Direcionador 34 Min: ${uiState.posicaoServoDirecionador34Min}, Max: ${uiState.posicaoServoDirecionador34Max}")
            Text("UI State - Cor: ${uiState.cor}, R: ${uiState.rValue}, G: ${uiState.gValue}, B: ${uiState.bValue}")
            Text("Comunicação UI State -  $mensagemMQTT")
            // Exibe o valor atual antes dos cards
            Text("Posição Servo Porta - Min: $bdPosicaoServoPortaMin Max: $bdPosicaoServoPortaMax")
            AngleCard(
                label = "Posição Servo Porta",
                posicaoMin = posicaoServoPortaMin,
                posicaoMax = posicaoServoPortaMax,
                onMinChange = { posicaoServoPortaMin = it},
                onMaxChange = { posicaoServoPortaMax = it}
            )

            // Exibe o valor atual antes dos cards
            Text("Posição Servo Direcionador ED - Min: $bdPosicaoServoDirecionadorEDMin Max: $bdPosicaoServoDirecionadorEDMax")
            AngleCard(
                label = "Posição Servo Direcionador ED",
                posicaoMin = posicaoServoDirecionadorEDMin,
                posicaoMax = posicaoServoDirecionadorEDMax,
                onMinChange = { posicaoServoDirecionadorEDMin = it },
                onMaxChange = { posicaoServoDirecionadorEDMax = it }
            )

            // Exibe o valor atual antes dos cards
            Text("Posição Servo Direcionador 12 - Min: $bdPosicaoServoDirecionador12Min Max: $bdPosicaoServoDirecionador12Max")
            AngleCard(
                label = "Posição Servo Direcionador 12",
                posicaoMin = posicaoServoDirecionador12Min,
                posicaoMax = posicaoServoDirecionador12Max,
                onMinChange = { posicaoServoDirecionador12Min = it },
                onMaxChange = { posicaoServoDirecionador12Max = it }
            )

            // Exibe o valor atual antes dos cards
            Text("Posição Servo Direcionador 34 - Min: $bdPosicaoServoDirecionador34Min Max: $bdPosicaoServoDirecionador34Max")
            AngleCard(
                label = "Posição Servo Direcionador 34",
                posicaoMin = posicaoServoDirecionador34Min,
                posicaoMax = posicaoServoDirecionador34Max,
                onMinChange = { posicaoServoDirecionador34Min = it },
                onMaxChange = { posicaoServoDirecionador34Max = it }
            )

            // Exibe os valores RGB antes do card
            Text("RGB Valores - Cor: $bdCor -> R: $bdRValue, G: $bdGValue, B: $bdBValue")
            RGBCard(
                label = "RGB Valores",
                R = rValue,
                G = gValue,
                B = bValue,
                cor = cor,
                onRChange = { rValue = it },
                onGChange = { gValue = it },
                onBChange = { bValue = it },
                onCorChange = { cor = it }
            )

            Button(
                onClick = {
                    coroutineScope.launch {
                       // viewModel.manipularMensagemMQTT()
                        viewModel.transformarObjetoJsoneEnviar()
                        if (parametros == null) {
                            viewModel.insert(
                                posicaoServoPortaMin,
                                posicaoServoPortaMax,
                                posicaoServoDirecionadorEDMin,
                                posicaoServoDirecionadorEDMax,
                                posicaoServoDirecionador12Min,
                                posicaoServoDirecionador12Max,
                                posicaoServoDirecionador34Min,
                                posicaoServoDirecionador34Max,
                                cor,
                                rValue,
                                gValue,
                                bValue
                            )
                        } else {
                            viewModel.update(
                                posicaoServoPortaMin,
                                posicaoServoPortaMax,
                                posicaoServoDirecionadorEDMin,
                                posicaoServoDirecionadorEDMax,
                                posicaoServoDirecionador12Min,
                                posicaoServoDirecionador12Max,
                                posicaoServoDirecionador34Min,
                                posicaoServoDirecionador34Max,
                                cor,
                                rValue,
                                gValue,
                                bValue
                            )
                        }
                    }

                    viewModel.updateParametrosFromDatabase()
                    Log.d("ParametrosUpdate", "Novos parâmetros: ${uiState.posicaoServoPortaMin}")
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
    cor: String,
    onRChange: (Int) -> Unit,
    onGChange: (Int) -> Unit,
    onBChange: (Int) -> Unit,
    onCorChange: (String) -> Unit
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
                                    onCorChange(color)
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


