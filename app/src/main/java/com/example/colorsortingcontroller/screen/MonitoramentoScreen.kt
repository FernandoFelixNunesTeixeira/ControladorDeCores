package com.example.colorsortingcontroller.com.example.colorsortingcontroller.screen

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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