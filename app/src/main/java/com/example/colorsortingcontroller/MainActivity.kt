package com.example.colorsortingcontroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.text.input.KeyboardType

// Instanciamento das classes ViewModel
data class ScreenState(val title: String, val backgroundColor: Color)

class MonitoramentoViewModel : ViewModel() {
    private val _state = MutableStateFlow(ScreenState("Monitoramento", Color(0xFFF5F5F5)))
    val state: StateFlow<ScreenState> = _state
}

class ParametrosViewModel : ViewModel() {
    private val _state = MutableStateFlow(ScreenState("Parâmetros", Color(0xFFF5F5F5)))
    val state: StateFlow<ScreenState> = _state
}

class EstatisticasViewModel : ViewModel() {
    private val _state = MutableStateFlow(ScreenState("Estatísticas", Color(0xFFF5F5F5)))
    val state: StateFlow<ScreenState> = _state
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // Telas
                val monitoramentoViewModel: MonitoramentoViewModel = viewModel()
                val parametrosViewModel: ParametrosViewModel = viewModel()
                val estatisticasViewModel: EstatisticasViewModel = viewModel()

                // Tela atual (monitoramento é a tela default)
                var currentScreen by remember { mutableStateOf("monitoramento") }

                // Mudança da tela
                when (currentScreen) {
                    "monitoramento" -> ScaffoldApp(
                        state = monitoramentoViewModel.state.collectAsState().value,
                        onScreenChange = { currentScreen = it }
                    ) {
                        MonitoramentoScreen()
                    }
                    "parametros" -> ScaffoldApp(
                        state = parametrosViewModel.state.collectAsState().value,
                        onScreenChange = { currentScreen = it }
                    ) {
                        ParametrosScreen()
                    }
                    "estatisticas" -> ScaffoldApp(
                        state = estatisticasViewModel.state.collectAsState().value,
                        onScreenChange = { currentScreen = it }
                    ) {
                        EstatisticasScreen()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldApp(
    state: ScreenState,
    onScreenChange: (String) -> Unit,
    content: @Composable () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.5f)
                    .background(Color.LightGray)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                DrawerItem(
                    icon = Icons.Default.Computer,
                    label = "Monitoramento",
                    onClick = {
                        onScreenChange("monitoramento")
                        coroutineScope.launch { drawerState.close() }
                    }
                )
                DrawerItem(
                    icon = Icons.Default.ContentPaste,
                    label = "Parâmetros",
                    onClick = {
                        onScreenChange("parametros")
                        coroutineScope.launch { drawerState.close() }
                    }
                )
                DrawerItem(
                    icon = Icons.Default.AutoGraph,
                    label = "Estatísticas",
                    onClick = {
                        onScreenChange("estatisticas")
                        coroutineScope.launch { drawerState.close() }
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF0B737E),
                        titleContentColor = Color(0xFFFFFFFF),
                    ),
                    title = {
                        Text(
                            text = state.title,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Open Drawer",
                                tint = Color.White
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(state.backgroundColor),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                content()
            }
        }
    }
}

@Composable
fun DrawerItem(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color(0xFF0B737E),
            modifier = Modifier.padding(end = 16.dp)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )
    }
}

// Tela de Monitoramento
@Composable
fun MonitoramentoScreen() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFEAF5F7)
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Estado: ")
            }
        }
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFEAF5F7)
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Cor: ")
            }
        }
    }
}

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

// Tela de Estatísticas
@Composable
fun EstatisticasScreen() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFEAF5F7)
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Peças separadas por cor: ")
            }
        }
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFEAF5F7)
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Peças separadas por coletor: ")
            }
        }
    }
}

