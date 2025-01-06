package com.example.colorsortingcontroller.screen

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.colorsortingcontroller.ui.theme.ColorSortingControllerTheme
import androidx.compose.ui.unit.dp
import androidx.lifecycle.asFlow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.colorsortingcontroller.model.Datos
import com.example.colorsortingcontroller.screen.graphs.MascotaStatsScreen

// Tela de Estatísticas
@Composable
fun EstatisticasScreen(viewModel: EstatisticasViewModel = viewModel()) {

    val uiState by viewModel.stateEstatisticas.collectAsState()

    val conexaoState by viewModel.conexaoMQTT.asFlow().collectAsStateWithLifecycle(initialValue = "Desconectado")

    ColorSortingControllerTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Estado da conexão: $conexaoState")

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                ) {
                    Text(text = "Peças separadas por cor: " +
                            "1: [${uiState.pecasCor1}] 2: [${uiState.pecasCor2}] 3: [${uiState.pecasCor3}] 4: [${uiState.pecasCor4}] " +
                            "5: [${uiState.pecasCor5}] 6: [${uiState.pecasCor6}] 7: [${uiState.pecasCor7}] 8: [${uiState.pecasCor8}]")
                }
            }
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Peças separadas por coletor: " +
                            "1: [${uiState.pecasColetor1}] 2: [${uiState.pecasColetor2}] 3: [${uiState.pecasColetor3}] 4: [${uiState.pecasColetor4}]")
                }
            }
            MascotaStatsScreen(pecasPorCor = listOf(
                Datos("Cor 1", uiState.pecasCor1 ?: 0),
                Datos("Cor 2", uiState.pecasCor2 ?: 0),
                Datos("Cor 3", uiState.pecasCor3 ?: 0),
                Datos("Cor 4", uiState.pecasCor4 ?: 0),
                Datos("Cor 5", uiState.pecasCor5 ?: 0),
                Datos("Cor 6", uiState.pecasCor6 ?: 0),
                Datos("Cor 7", uiState.pecasCor7 ?: 0),
                Datos("Cor 8", uiState.pecasCor8 ?: 0)
            ), pecasPorColetor = listOf(
                Datos("Coletor 1", uiState.pecasColetor1 ?: 0),
                Datos("Coletor 2", uiState.pecasColetor2 ?: 0),
                Datos("Coletor 3", uiState.pecasColetor3 ?: 0),
                Datos("Coletor 4", uiState.pecasColetor4 ?: 0)
            )
            )
        }
    }
}
