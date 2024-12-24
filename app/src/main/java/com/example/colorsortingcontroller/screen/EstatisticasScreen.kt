package com.example.colorsortingcontroller.screen

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import com.example.colorsortingcontroller.ui.theme.ColorSortingControllerTheme
import androidx.compose.ui.unit.dp
import com.example.colorsortingcontroller.screen.graphs.Lineas

// Tela de Estatísticas
@Composable
fun EstatisticasScreen() {
    ColorSortingControllerTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
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
                    Text(text = "Peças separadas por cor: ")
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
                    Text(text = "Peças separadas por coletor: ")
                }
            }
            //Por enquanto só dá para por um por vez

            Lineas()
            //Pastel()

            // MascotaStatsScreen()
        }
    }
}