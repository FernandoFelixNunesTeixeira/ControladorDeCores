package com.example.colorsortingcontroller

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.colorsortingcontroller.screen.ScreenState
import kotlinx.coroutines.launch
import com.example.colorsortingcontroller.ui.theme.ColorSortingControllerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldApp(
    state: ScreenState?,
    onScreenChange: (ScreenState) -> Unit,
    content: @Composable () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    // Usando o MaterialTheme com o tema definido
    ColorSortingControllerTheme{
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
                        title = {
                            if (state != null) {
                                Text("${state.title}")
                            }
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                coroutineScope.launch {
                                    drawerState.open()
                                }
                            }) {
                                Icon(Icons.Default.Menu, contentDescription = "Open Drawer", tint = MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    )
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    content()
                }
            }
        }
    }
}

@Composable
fun DrawerContent(onScreenChange: (ScreenState) -> Unit) {
    ColorSortingControllerTheme {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.5f)
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            DrawerItem(Icons.Default.Computer, "Monitoramento") {
                onScreenChange(ScreenState.monitoramento)
            }
            DrawerItem(Icons.Default.ContentPaste, "Parâmetros") {
                onScreenChange(ScreenState.parametros)
            }
            DrawerItem(Icons.Default.AutoGraph, "Estatísticas") {
                onScreenChange(ScreenState.estatisticas)
            }
        }
    }
}

@Composable
fun DrawerItem(icon: ImageVector, label: String, onClick: () -> Unit) {
    ColorSortingControllerTheme {
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
                tint = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.padding(end = 16.dp)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}