package com.example.colorsortingcontroller

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontVariation.width
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp

class EstatisticasActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                ScaffoldEstatisticas()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldEstatisticas() {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val context = LocalContext.current

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(modifier = Modifier.background(Color.LightGray)
                .fillMaxSize()
                .padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Card(modifier = Modifier, shape = RectangleShape, colors = CardDefaults.cardColors(
                    containerColor = Color.LightGray
                )) {
                    Text(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .clickable {
                                // Navega para a MainActivity
                                val intent = Intent(context, MainActivity::class.java)
                                context.startActivity(intent)
                            },
                        text = "Monitoramento",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                Card(modifier = Modifier, shape = RectangleShape, colors = CardDefaults.cardColors(
                    containerColor = Color.LightGray
                )) {
                    Text(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .clickable {
                                // Navega para a ParametrosActivity
                                val intent = Intent(context, ParametrosActivity::class.java)
                                context.startActivity(intent)
                            },
                        text = "Parâmetros",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                Card(modifier = Modifier, shape = RectangleShape, colors = CardDefaults.cardColors(
                    containerColor = Color.LightGray
                )) {
                    Text(
                        text = "Estatísticas",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = topAppBarColors(
                        containerColor = Color(0xFF0B737E),
                        titleContentColor = Color(0xFFFFFFFF),
                    ),
                    title = {
                        Text(
                            text = "Estatísticas",
                            fontWeight = FontWeight.Bold
                        )
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier.background(Color(0xFFF5F5F5))
                    .padding(innerPadding)
                    .padding(innerPadding)
                    .padding(innerPadding)
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

            ) {
                Card( modifier = Modifier, colors =  CardDefaults.cardColors(
                    containerColor = Color.Green
                ),
                    elevation = CardDefaults.cardElevation( defaultElevation = 10.dp),
                    border = BorderStroke(
                        width = Dp.VisibilityThreshold,
                        color = Color.Black
                    )
                ) {

                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "Verde: Quantidade",
                        fontWeight = FontWeight.Bold
                    )

                }

                Card( modifier = Modifier, colors =  CardDefaults.cardColors(
                    containerColor = Color.Red
                ),
                    elevation = CardDefaults.cardElevation( defaultElevation = 10.dp),
                    border = BorderStroke(
                        width = Dp.VisibilityThreshold,
                        color = Color.Black
                    )
                ) {

                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = "Vermelho: Quantidade",
                            fontWeight = FontWeight.Bold
                        )


                }
                Card( modifier = Modifier, colors =  CardDefaults.cardColors(
                    containerColor = Color.Yellow
                ),
                    elevation = CardDefaults.cardElevation( defaultElevation = 10.dp),
                    border = BorderStroke(
                        width = Dp.VisibilityThreshold,
                        color = Color.Black
                    )
                ) {

                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = "Amarelo: Quantidade",
                            fontWeight = FontWeight.Bold
                        )

                    }

                Card( modifier = Modifier, colors =  CardDefaults.cardColors(
                    containerColor = Color.Cyan
                ),
                    elevation = CardDefaults.cardElevation( defaultElevation = 10.dp),
                    border = BorderStroke(
                        width = Dp.VisibilityThreshold,
                        color = Color.Black
                    )
                ) {

                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = "Azul: Quantidade",
                            fontWeight = FontWeight.Bold
                        )

                    }

                Card( modifier = Modifier, colors =  CardDefaults.cardColors(
                    containerColor = Color.LightGray
                ),
                    elevation = CardDefaults.cardElevation( defaultElevation = 10.dp),
                    border = BorderStroke(
                        width = Dp.VisibilityThreshold,
                        color = Color.Black,
                    )
                ) {

                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "Tempo de funcionamento: Quantidade de tempo",
                        fontWeight = FontWeight.Bold
                    )

                }


            }
        }

    }
}