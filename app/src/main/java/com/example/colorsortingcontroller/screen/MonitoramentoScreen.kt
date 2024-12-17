package com.example.colorsortingcontroller.screen

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.colorsortingcontroller.ui.theme.ColorSortingControllerTheme

// Tela de Monitoramento
@Composable
fun MonitoramentoScreen() {
    ColorSortingControllerTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface, // Cor de fundo
                    contentColor = MaterialTheme.colorScheme.onSurface // Cor do conteúdo
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                ) {
                    Text(text = "Estado: ")
                }
            }
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface, // Cor de fundo
                    contentColor = MaterialTheme.colorScheme.onSurface // Cor do conteúdo
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                ) {
                    Text(text = "Cor: ")
                }
            }
        }
    }
}