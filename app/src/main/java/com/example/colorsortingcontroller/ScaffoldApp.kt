package com.example.colorsortingcontroller.com.example.colorsortingcontroller

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.colorsortingcontroller.com.example.colorsortingcontroller.screen.ScreenState
import kotlinx.coroutines.launch

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
            DrawerContent(onScreenChange = {
                onScreenChange(it)
                coroutineScope.launch { drawerState.close() }
            })
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF0B737E),
                        titleContentColor = Color(0xFFFFFFFF),
                    ),
                    title = { Text(state.title) },
                    navigationIcon = {
                        IconButton(onClick = { coroutineScope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Open Drawer", tint = Color.White)
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(Color(0xFFF5F5F5))
            ) {
                content()
            }
        }
    }
}

@Composable
fun DrawerContent(onScreenChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.5f)
            .background(Color.LightGray)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        DrawerItem(Icons.Default.Computer, "Monitoramento") { onScreenChange("monitoramento") }
        DrawerItem(Icons.Default.ContentPaste, "Parâmetros") { onScreenChange("parametros") }
        DrawerItem(Icons.Default.AutoGraph, "Estatísticas") { onScreenChange("estatisticas") }
    }
}

@Composable
fun DrawerItem(icon: ImageVector, label: String, onClick: () -> Unit) {
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


