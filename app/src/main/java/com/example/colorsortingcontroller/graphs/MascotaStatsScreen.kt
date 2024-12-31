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
import com.github.tehras.charts.line.LineChart
import com.github.tehras.charts.line.LineChartData
import com.github.tehras.charts.line.renderer.line.SolidLineDrawer
import com.github.tehras.charts.piechart.PieChart
import com.github.tehras.charts.piechart.PieChartData
import com.github.tehras.charts.piechart.renderer.SimpleSliceDrawer


@Composable
fun MascotaStatsScreen(pecasPorCor: List<Datos>) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Peças separadas por cor",
            style = MaterialTheme.typography.headlineMedium
        )
        Barras(pecasPorCor)
    }
}

@Composable
fun Barras(pecasPorCor: List<Datos>) {
    val barras = ArrayList<BarChartData.Bar>()
    pecasPorCor.map { datos ->
        barras.add(
            BarChartData.Bar(
                label = datos.label,
                value = datos.value.toFloat(),
                color = randomColor()
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

@Composable
fun Pastel() {
    val datos: List<Datos> = listOf(
        Datos("Vermelho", 15),
        Datos("Amarelo", 27),
        Datos("Verde", 32),
        Datos("Azul", 80)
    )
    var slices = ArrayList<PieChartData.Slice>()
    datos.mapIndexed { index, datos ->
        slices.add(PieChartData.Slice(
            value = datos.value.toFloat(),
            color = randomColor(),
            ))
        PieChart(
            pieChartData = PieChartData(
                slices = slices,
                ),
            modifier = Modifier
                .padding(horizontal = 30.dp, vertical = 80.dp)
                .height(300.dp),
            sliceDrawer = SimpleSliceDrawer(
                sliceThickness = 100f
            )
        )
    }
}

@Composable
fun Lineas() {
    val datos: List<Datos> = listOf(
        Datos("Vermelho", 15),
        Datos("Amarelo", 27),
        Datos("Verde", 32),
        Datos("Azul", 80)
    )
    var puntos = ArrayList<LineChartData.Point>( )
    datos.mapIndexed { index, datos ->
        puntos.add(LineChartData.Point(
            value = datos.value.toFloat(),
            label = datos.label
        )
        )
    }
    var lineas = ArrayList<LineChartData>()
    lineas.add(
        LineChartData(
            points = puntos,
            lineDrawer = SolidLineDrawer()
        )
    )
    LineChart(
        linesChartData = lineas,
        modifier = Modifier
            .padding(horizontal = 30.dp, vertical = 80.dp)
            .height(300.dp)

    )
}

val cores = mutableListOf(
    Color(0xFFBB86FC),
    Color(0xFF6200EE),
    Color(0xFF3700B3),
    Color(0xFF03DAC5),
    Color(0xFF018786),
    Color(0xFF000000),
    Color(0xFFFFFFFF),
    Color(0xBE8C8989),
)

fun randomColor(): Color {
    val randomIndex = ( Math.random() * cores.size).toInt()
    val cor = cores[randomIndex]
    cores.removeAt(randomIndex)
    return cor
}

