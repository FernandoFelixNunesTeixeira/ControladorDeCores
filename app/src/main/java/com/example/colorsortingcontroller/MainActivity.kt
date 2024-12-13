package com.example.colorsortingcontroller

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {

                    ScaffoldMonitoramento()

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldMonitoramento() {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val context = LocalContext.current

    ModalNavigationDrawer(
        drawerState = drawerState,

        drawerContent = {
            Column(modifier = Modifier.fillMaxSize().background(Color.LightGray).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Card(modifier = Modifier, shape = RectangleShape, colors = CardDefaults.cardColors(
                    containerColor = Color.LightGray
                )) {
                    Text(
                        text = "Monitoramento",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }


                Card(modifier = Modifier, shape = RectangleShape,  colors = CardDefaults.cardColors(
                    containerColor = Color.LightGray
                )) {
                    Text(
                        modifier = Modifier.padding(top = 8.dp).clickable {
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
                    modifier = Modifier.padding(top = 8.dp).clickable {
                        // Navega para a EstatisticasActivity
                        val intent = Intent(context, EstatisticasActivity::class.java)
                        context.startActivity(intent)
                    },
                    text = "Estatísticas",
                    style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                )
            }
                }
        },
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
                            text = "Monitoramento",
                            fontWeight = FontWeight.Bold
                        )
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize().background(Color(0xFFF5F5F5)),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "nada ainda"
                )
            }
        }
    }
}