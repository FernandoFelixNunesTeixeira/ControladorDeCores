package com.example.colorsortingcontroller.screen.graphs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.colorsortingcontroller.model.Datos
import com.github.tehras.charts.bar.BarChart
import com.github.tehras.charts.bar.BarChartData
import com.github.tehras.charts.bar.renderer.label.SimpleValueDrawer

@Composable
fun MascotaStatsScreen(pecasPorCor: List<Datos>, pecasPorColetor: List<Datos>) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Peças separadas por cor",
            style = MaterialTheme.typography.headlineMedium
        )
        Barras(pecasPorCor)

        Text(
            text = "Peças separadas por coletor",
            style = MaterialTheme.typography.headlineMedium
        )
        Barras(pecasPorColetor)
    }
}

@Composable
fun Barras(pecasPorCor: List<Datos>) {
    val barras = ArrayList<BarChartData.Bar>()

    val cores = listOf(
        Color(0xFFCC0606), // Cor 1
        Color(0xFF1C62DE), // Cor 2
        Color(0xFF21B300), // Cor 3
        Color(0xFFDACF03), // Cor 4
        Color(0xFF680EB7), // Cor 5
        Color(0xFFE76B19), // Cor 6
        Color(0xFFEC23E9), // Cor 7
        //Color(0xBE1BDEE5)  // Cor 8
    )

    pecasPorCor.mapIndexed { index, datos ->
        barras.add(
            BarChartData.Bar(
                label = datos.label,
                value = datos.value.toFloat(),
                color = cores.getOrNull(index) ?: Color.Gray
            )
        )
    }

    BarChart(
        barChartData = BarChartData(bars = barras),
        modifier = Modifier
            .padding(horizontal = 30.dp, vertical = 80.dp)
            .height(300.dp),
        labelDrawer = SimpleValueDrawer(
            drawLocation = SimpleValueDrawer.DrawLocation.XAxis
        )
    )
    LineaPunteada()
}

