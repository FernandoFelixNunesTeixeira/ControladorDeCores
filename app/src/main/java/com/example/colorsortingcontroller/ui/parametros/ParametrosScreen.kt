package com.example.colorsortingcontroller.parametros

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
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.asFlow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.colorsortingcontroller.theme.ColorSortingControllerTheme
import kotlinx.coroutines.CoroutineScope
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

@Composable
fun ParametrosScreen(viewModel: ParametrosViewModel = viewModel()) {
    val uiState by viewModel.stateParametros.collectAsState()

   // val mensagemMQTT by viewModel.mensagemMQTT.asFlow().collectAsStateWithLifecycle(initialValue = "Esperando mensagem MQTT")
    val conexaoState by viewModel.conexaoMQTT.asFlow().collectAsStateWithLifecycle(initialValue = "Desconectado")
    val mensagemEntregue by viewModel.mensagemEntregue.asFlow().collectAsStateWithLifecycle(initialValue = "")

    // Variáveis para INPUT
    val coroutineScope = rememberCoroutineScope()

    var posicaoServoPortaMin by rememberSaveable { mutableStateOf(uiState.posicaoServoPortaMin.toString()) }
    var posicaoServoPortaMax by rememberSaveable { mutableStateOf(uiState.posicaoServoPortaMax.toString()) }

    var posicaoServoDirecionadorEDMin by rememberSaveable { mutableStateOf(uiState.posicaoServoDirecionadorEDMin.toString()) }
    var posicaoServoDirecionadorEDMax by rememberSaveable { mutableStateOf(uiState.posicaoServoDirecionadorEDMax.toString()) }

    var posicaoServoDirecionador12Min by rememberSaveable { mutableStateOf(uiState.posicaoServoDirecionador12Min.toString()) }
    var posicaoServoDirecionador12Max by rememberSaveable { mutableStateOf(uiState.posicaoServoDirecionador12Max.toString()) }

    var posicaoServoDirecionador34Min by rememberSaveable { mutableStateOf(uiState.posicaoServoDirecionador34Min.toString()) }
    var posicaoServoDirecionador34Max by rememberSaveable { mutableStateOf(uiState.posicaoServoDirecionador34Max.toString()) }

    var corColetor1 by rememberSaveable { mutableStateOf(uiState.coletorCor1.toString()) }
    var corColetor2 by rememberSaveable { mutableStateOf(uiState.coletorCor2.toString()) }
    var corColetor3 by rememberSaveable { mutableStateOf(uiState.coletorCor3.toString()) }
    var corColetor4 by rememberSaveable { mutableStateOf(uiState.coletorCor4.toString()) }
    var corColetor5 by rememberSaveable { mutableStateOf(uiState.coletorCor5.toString()) }
    var corColetor6 by rememberSaveable { mutableStateOf(uiState.coletorCor6.toString()) }
    var corColetor7 by rememberSaveable { mutableStateOf(uiState.coletorCor7.toString()) }

    var rValue1 by rememberSaveable { mutableStateOf(uiState.RCor1.toString()) }
    var gValue1 by rememberSaveable { mutableStateOf(uiState.GCor1.toString()) }
    var bValue1 by rememberSaveable { mutableStateOf(uiState.BCor1.toString()) }

    var rValue2 by rememberSaveable { mutableStateOf(uiState.RCor2.toString()) }
    var gValue2 by rememberSaveable { mutableStateOf(uiState.GCor2.toString()) }
    var bValue2 by rememberSaveable { mutableStateOf(uiState.BCor2.toString()) }

    var rValue3 by rememberSaveable { mutableStateOf(uiState.RCor3.toString()) }
    var gValue3 by rememberSaveable { mutableStateOf(uiState.GCor3.toString()) }
    var bValue3 by rememberSaveable { mutableStateOf(uiState.BCor3.toString()) }

    var rValue4 by rememberSaveable { mutableStateOf(uiState.RCor4.toString()) }
    var gValue4 by rememberSaveable { mutableStateOf(uiState.GCor4.toString()) }
    var bValue4 by rememberSaveable { mutableStateOf(uiState.BCor4.toString()) }

    var rValue5 by rememberSaveable { mutableStateOf(uiState.RCor5.toString()) }
    var gValue5 by rememberSaveable { mutableStateOf(uiState.GCor5.toString()) }
    var bValue5 by rememberSaveable { mutableStateOf(uiState.BCor5.toString()) }

    var rValue6 by rememberSaveable { mutableStateOf(uiState.RCor6.toString()) }
    var gValue6 by rememberSaveable { mutableStateOf(uiState.GCor6.toString()) }
    var bValue6 by rememberSaveable { mutableStateOf(uiState.BCor6.toString()) }

    var rValue7 by rememberSaveable { mutableStateOf(uiState.RCor7.toString()) }
    var gValue7 by rememberSaveable { mutableStateOf(uiState.GCor7.toString()) }
    var bValue7 by rememberSaveable { mutableStateOf(uiState.BCor7.toString()) }


    val validoOuNao by remember(
        posicaoServoPortaMin,
        posicaoServoPortaMax,
        posicaoServoDirecionadorEDMin,
        posicaoServoDirecionadorEDMax,
        posicaoServoDirecionador12Min,
        posicaoServoDirecionador12Max,
        posicaoServoDirecionador34Min,
        posicaoServoDirecionador34Max,
        rValue1,
        gValue1,
        bValue1,
        corColetor1,
        rValue2,
        gValue2,
        bValue2,
        corColetor2,
        rValue3,
        gValue3,
        bValue3,
        corColetor3,
        rValue4,
        gValue4,
        bValue4,
        corColetor4,
        rValue5,
        gValue5,
        bValue5,
        corColetor5,
        rValue6,
        gValue6,
        bValue6,
        corColetor6,
        rValue7,
        gValue7,
        bValue7,
        corColetor7) { derivedStateOf{
        viewModel.validateInput(
            posicaoServoPortaMin,
            posicaoServoPortaMax,
            posicaoServoDirecionadorEDMin,
            posicaoServoDirecionadorEDMax,
            posicaoServoDirecionador12Min,
            posicaoServoDirecionador12Max,
            posicaoServoDirecionador34Min,
            posicaoServoDirecionador34Max,
            rValue1,
            gValue1,
            bValue1,
            corColetor1,
            rValue2,
            gValue2,
            bValue2,
            corColetor2,
            rValue3,
            gValue3,
            bValue3,
            corColetor3,
            rValue4,
            gValue4,
            bValue4,
            corColetor4,
            rValue5,
            gValue5,
            bValue5,
            corColetor5,
            rValue6,
            gValue6,
            bValue6,
            corColetor6,
            rValue7,
            gValue7,
            bValue7,
            corColetor7
        )
    }
    }

    val scrollState = rememberScrollState()

    ColorSortingControllerTheme {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Exibe os valores da uiState
            Text("Estado da conexão: $conexaoState")
            Text("$mensagemEntregue")

            // Exibe o valor atual antes dos cards
            Text("Posição Servo Porta - Min: ${uiState.posicaoServoPortaMin} Max: ${uiState.posicaoServoPortaMax}")
            AngleCard(
                label = "Posição Servo Porta",
                posicaoMin = posicaoServoPortaMin,
                posicaoMax = posicaoServoPortaMax,
                onMinChange = { posicaoServoPortaMin = it},
                onMaxChange = { posicaoServoPortaMax = it}
            )

            // Exibe o valor atual antes dos cards
            Text("Posição Servo Direcionador ED - Min: ${uiState.posicaoServoDirecionadorEDMin} Max: ${uiState.posicaoServoDirecionadorEDMax}")
            AngleCard(
                label = "Posição Servo Direcionador ED",
                posicaoMin = posicaoServoDirecionadorEDMin,
                posicaoMax = posicaoServoDirecionadorEDMax,
                onMinChange = { posicaoServoDirecionadorEDMin = it},
                onMaxChange = { posicaoServoDirecionadorEDMax = it}
            )

            // Exibe o valor atual antes dos cards
            Text("Posição Servo Direcionador 12 - Min: ${uiState.posicaoServoDirecionador12Min} Max: ${uiState.posicaoServoDirecionador12Max}")
            AngleCard(
                label = "Posição Servo Direcionador 12",
                posicaoMin = posicaoServoDirecionador12Min,
                posicaoMax = posicaoServoDirecionador12Max,
                onMinChange = { posicaoServoDirecionador12Min = it},
                onMaxChange = { posicaoServoDirecionador12Max = it}
            )

            // Exibe o valor atual antes dos cards
            Text("Posição Servo Direcionador 34 - Min: ${uiState.posicaoServoDirecionador34Min} Max: ${uiState.posicaoServoDirecionador34Max}")
            AngleCard(
                label = "Posição Servo Direcionador 34",
                posicaoMin = posicaoServoDirecionador34Min,
                posicaoMax = posicaoServoDirecionador34Max,
                onMinChange = { posicaoServoDirecionador34Min = it},
                onMaxChange = { posicaoServoDirecionador34Max = it}
            )

            // Exibe os valores RGB antes do card
            Text("RGB Valores -> R Cor 1: ${uiState.RCor1}, G Cor1 : ${uiState.GCor1}, B Cor1: ${uiState.BCor1} - Coletor Cor 1: ${uiState.coletorCor1}")
            Text("RGB Valores -> R Cor 2: ${uiState.RCor2}, G Cor2 : ${uiState.GCor2}, B Cor2: ${uiState.BCor2} - Coletor Cor 2: ${uiState.coletorCor2}")
            Text("RGB Valores -> R Cor 3: ${uiState.RCor3}, G Cor3 : ${uiState.GCor3}, B Cor3: ${uiState.BCor3} - Coletor Cor 3: ${uiState.coletorCor3}")
            Text("RGB Valores -> R Cor 4: ${uiState.RCor4}, G Cor4 : ${uiState.GCor4}, B Cor4: ${uiState.BCor4} - Coletor Cor 4: ${uiState.coletorCor4}")
            Text("RGB Valores -> R Cor 5: ${uiState.RCor5}, G Cor5 : ${uiState.GCor5}, B Cor5: ${uiState.BCor5} - Coletor Cor 5: ${uiState.coletorCor5}" )
            Text("RGB Valores -> R Cor 6: ${uiState.RCor6}, G Cor6 : ${uiState.GCor6}, B Cor6: ${uiState.BCor6} - Coletor Cor 6: ${uiState.coletorCor6}")
            Text("RGB Valores -> R Cor 7: ${uiState.RCor7}, G Cor7 : ${uiState.GCor7}, B Cor7: ${uiState.BCor7} - Coletor Cor 7: ${uiState.coletorCor7}")
            RGBCard(
                label = "RGB Valores",

                // Valores RGB de cada cor e coletor

                RCor1 = rValue1,
                GCor1 = gValue1,
                BCor1 = bValue1,
                coletorCor1 = corColetor1,
                RCor2 = rValue2,
                GCor2 = gValue2,
                BCor2 = bValue2,
                coletorCor2 = corColetor2,
                RCor3 = rValue3,
                GCor3 = gValue3,
                BCor3 = bValue3,
                coletorCor3 = corColetor3,
                RCor4 = rValue4,
                GCor4 = gValue4,
                BCor4 = bValue4,
                coletorCor4 = corColetor4,
                RCor5 = rValue5,
                GCor5 = gValue5,
                BCor5 = bValue5,
                coletorCor5 = corColetor5,
                RCor6 = rValue6,
                GCor6 = gValue6,
                BCor6 = bValue6,
                coletorCor6 = corColetor6,
                RCor7 = rValue7,
                GCor7 = gValue7,
                BCor7 = bValue7,
                coletorCor7 = corColetor7,
                onR1Change = { rValue1 = it},
                onG1Change = { gValue1 = it },
                onB1Change = { bValue1 = it },
                onCorColetor1Change = { corColetor1 = it },

                onR2Change = { rValue2 = it},
                onG2Change = { gValue2 = it},
                onB2Change = { bValue2 = it},
                onCorColetor2Change = { corColetor2 = it },

                onR3Change = { rValue3 = it},
                onG3Change = { gValue3 = it},
                onB3Change = { bValue3 = it},
                onCorColetor3Change = { corColetor3 = it },

                onR4Change = { rValue4 = it},
                onG4Change = { gValue4 = it},
                onB4Change = { bValue4 = it},
                onCorColetor4Change = { corColetor4 = it },

                onR5Change = { rValue5 = it},
                onG5Change = { gValue5 = it},
                onB5Change = { bValue5 = it},
                onCorColetor5Change = { corColetor5 = it },

                onR6Change = { rValue6 = it},
                onG6Change = { gValue6 = it},
                onB6Change = { bValue6 = it},
                onCorColetor6Change= { corColetor6 = it },

                onR7Change = { rValue7 = it},
                onG7Change = { gValue7 = it},
                onB7Change = { bValue7 = it},
                onCorColetor7Change= { corColetor7 = it },

            )

            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.atualizarBancoDeDados(
                            posicaoServoPortaMin,
                            posicaoServoPortaMax,
                            posicaoServoDirecionadorEDMin,
                            posicaoServoDirecionadorEDMax,
                            posicaoServoDirecionador12Min,
                            posicaoServoDirecionador12Max,
                            posicaoServoDirecionador34Min,
                            posicaoServoDirecionador34Max,
                            rValue1,
                            gValue1,
                            bValue1,
                            corColetor1,
                            rValue2,
                            gValue2,
                            bValue2,
                            corColetor2,
                            rValue3,
                            gValue3,
                            bValue3,
                            corColetor3,
                            rValue4,
                            gValue4,
                            bValue4,
                            corColetor4,
                            rValue5,
                            gValue5,
                            bValue5,
                            corColetor5,
                            rValue6,
                            gValue6,
                            bValue6,
                            corColetor6,
                            rValue7,
                            gValue7,
                            bValue7,
                            corColetor7
                        )
                    }
                        viewModel.transformarObjetoJsoneEnviar(posicaoServoPortaMin,
                            posicaoServoPortaMax,
                            posicaoServoDirecionadorEDMin,
                            posicaoServoDirecionadorEDMax,
                            posicaoServoDirecionador12Min,
                            posicaoServoDirecionador12Max,
                            posicaoServoDirecionador34Min,
                            posicaoServoDirecionador34Max,
                            rValue1,
                            gValue1,
                            bValue1,
                            corColetor1,
                            rValue2,
                            gValue2,
                            bValue2,
                            corColetor2,
                            rValue3,
                            gValue3,
                            bValue3,
                            corColetor3,
                            rValue4,
                            gValue4,
                            bValue4,
                            corColetor4,
                            rValue5,
                            gValue5,
                            bValue5,
                            corColetor5,
                            rValue6,
                            gValue6,
                            bValue6,
                            corColetor6,
                            rValue7,
                            gValue7,
                            bValue7,
                            corColetor7)
                    Log.d("ParametrosUpdate", "Novos parâmetros: ${uiState.posicaoServoPortaMin}")
                },
                enabled = validoOuNao,
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
    posicaoMin: String,
    posicaoMax: String,
    onMinChange: (String) -> Unit,
    onMaxChange: (String) -> Unit
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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = label)
                Text("Ângulos:")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    // Campo para o ângulo mínimo
                    Text("Posição 1:")
                    InputField(value = posicaoMin, onValueChange = onMinChange)

                    // Campo para o ângulo máximo
                    Text("Posição 2:")
                    InputField(value = posicaoMax, onValueChange = onMaxChange)
                }
            }
        }
    }
}

@Composable
fun RGBCard(
    label: String,
    RCor1: String,
    GCor1: String,
    BCor1: String,
    coletorCor1: String,
    RCor2: String,
    GCor2: String,
    BCor2: String,
    coletorCor2: String,
    RCor3: String,
    GCor3: String,
    BCor3: String,
    coletorCor3: String,
    RCor4: String,
    GCor4: String,
    BCor4: String,
    coletorCor4: String,
    RCor5: String,
    GCor5: String,
    BCor5: String,
    coletorCor5: String,
    RCor6: String,
    GCor6: String,
    BCor6: String,
    coletorCor6: String,
    RCor7: String,
    GCor7: String,
    BCor7: String,
    coletorCor7: String,

    onR1Change: (String) -> Unit,
    onG1Change: (String) -> Unit,
    onB1Change: (String) -> Unit,
    onCorColetor1Change: (String) -> Unit,

    onR2Change: (String) -> Unit,
    onG2Change: (String) -> Unit,
    onB2Change: (String) -> Unit,
    onCorColetor2Change: (String) -> Unit,

    onR3Change: (String) -> Unit,
    onG3Change: (String) -> Unit,
    onB3Change: (String) -> Unit,
    onCorColetor3Change: (String) -> Unit,

    onR4Change: (String) -> Unit,
    onG4Change: (String) -> Unit,
    onB4Change: (String) -> Unit,
    onCorColetor4Change: (String) -> Unit,

    onR5Change: (String) -> Unit,
    onG5Change: (String) -> Unit,
    onB5Change: (String) -> Unit,
    onCorColetor5Change: (String) -> Unit,

    onR6Change: (String) -> Unit,
    onG6Change: (String) -> Unit,
    onB6Change: (String) -> Unit,
    onCorColetor6Change: (String) -> Unit,

    onR7Change: (String) -> Unit,
    onG7Change: (String) -> Unit,
    onB7Change: (String) -> Unit,
    onCorColetor7Change: (String) -> Unit
) {
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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = label)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "Cor 1:     Coletor de cor: ")
                    InputField(value = coletorCor1, onValueChange = onCorColetor1Change)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "R: ")
                    InputField2(value = RCor1, onValueChange = onR1Change)
                    Text(text = "G: ")
                    InputField2(value = GCor1, onValueChange = onG1Change)
                    Text(text = "B: ")
                    InputField2(value = BCor1, onValueChange = onB1Change)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "Cor 2:     Coletor de cor: ")
                    InputField(value = coletorCor2, onValueChange = onCorColetor2Change)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "R: ")
                    InputField2(value = RCor2, onValueChange = onR2Change)
                    Text(text = "G: ")
                    InputField2(value = GCor2, onValueChange = onG2Change)
                    Text(text = "B: ")
                    InputField2(value = BCor2, onValueChange = onB2Change)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "Cor 3:     Coletor de cor: ")

                    InputField(value = coletorCor3, onValueChange = onCorColetor3Change)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "R: ")
                    InputField2(value = RCor3, onValueChange = onR3Change)
                    Text(text = "G: ")
                    InputField2(value = GCor3, onValueChange = onG3Change)
                    Text(text = "B: ")
                    InputField2(value = BCor3, onValueChange = onB3Change)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "Cor 4:     Coletor de cor: ")

                    InputField(value = coletorCor4, onValueChange = onCorColetor4Change)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "R: ")
                    InputField2(value = RCor4, onValueChange = onR4Change)
                    Text(text = "G: ")
                    InputField2(value = GCor4, onValueChange = onG4Change)
                    Text(text = "B: ")
                    InputField2(value = BCor4, onValueChange = onB4Change)
                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "Cor 5:     Coletor de cor: ")
                    InputField(value = coletorCor5, onValueChange = onCorColetor5Change)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "R: ")
                    InputField2(value = RCor5, onValueChange = onR5Change)
                    Text(text = "G: ")
                    InputField2(value = GCor5, onValueChange = onG5Change)
                    Text(text = "B: ")
                    InputField2(value = BCor5, onValueChange = onB5Change)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "Cor 6:     Coletor de cor:")
                    InputField(value = coletorCor6, onValueChange = onCorColetor6Change)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "R: ")
                    InputField2(value = RCor6, onValueChange = onR6Change)
                    Text(text = "G: ")
                    InputField2(value = GCor6, onValueChange = onG6Change)
                    Text(text = "B: ")
                    InputField2(value = BCor6, onValueChange = onB6Change)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "Cor 7:     Coletor de cor: ")
                    InputField(value = coletorCor7, onValueChange = onCorColetor7Change)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "R: ")
                    InputField2(value = RCor7, onValueChange = onR7Change)
                    Text(text = "G: ")
                    InputField2(value = GCor7, onValueChange = onG7Change)
                    Text(text = "B: ")
                    InputField2(value = BCor7, onValueChange = onB7Change)
                }
            }
        }
    }
}

@Composable
fun InputField(value: String, onValueChange: (String) -> Unit) {
    var text by remember { mutableStateOf(value) }

    ColorSortingControllerTheme {
        BasicTextField(
            value = text,
            onValueChange = { newValue ->
                val filteredValue = newValue.filter { it.isDigit() }
                text = filteredValue

                filteredValue.toIntOrNull().let { numericValue ->
                    if(numericValue != null)
                    onValueChange(numericValue.toString())
                    else onValueChange("")
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


@Composable
fun InputField2(value: String, onValueChange: (String) -> Unit) {
    var text by remember { mutableStateOf(value) }

    ColorSortingControllerTheme {
        BasicTextField(
            value = text,
            onValueChange = { newValue ->
                val filteredValue = newValue.filter { it.isDigit() || it.toString() == "."}
                text = filteredValue

                filteredValue.toFloatOrNull().let { numericValue ->
                    if(numericValue != null)
                        onValueChange(numericValue.toString())
                    else onValueChange("")
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


