package com.example.colorsortingcontroller

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.colorsortingcontroller.data.AppDatabase
import com.example.colorsortingcontroller.data.dao.EstatisticasDao
import com.example.colorsortingcontroller.data.dao.ParametrosDao
import com.example.colorsortingcontroller.data.localSource.EstatisticasLocalSource
import com.example.colorsortingcontroller.data.repository.EstatisticasRepository
import com.example.colorsortingcontroller.data.dao.MonitoramentoDao
import com.example.colorsortingcontroller.data.localSource.MonitoramentoLocalSource
import com.example.colorsortingcontroller.data.repository.MonitoramentoRepository
import com.example.colorsortingcontroller.data.localSource.ParametrosLocalSource
import com.example.colorsortingcontroller.data.repository.ParametrosRepository
import com.example.colorsortingcontroller.navigation.AppNavigation
import com.example.colorsortingcontroller.estatisticas.EstatisticasViewModel
import com.example.colorsortingcontroller.monitoramento.MonitoramentoViewModel
import com.example.colorsortingcontroller.parametros.ParametrosViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.system.exitProcess

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(applicationContext)

        val monitoramentoDao: MonitoramentoDao = database.monitoramentoDao()
        val parametrosDao: ParametrosDao = database.parametrosDao()
        val estatisticasDao: EstatisticasDao = database.estatisticasDao()

        val monitoramentoSource = MonitoramentoLocalSource(monitoramentoDao)
        val parametrosSource = ParametrosLocalSource(parametrosDao)
        val estatisticasSource = EstatisticasLocalSource(estatisticasDao)

        val monitoramentoRepository = MonitoramentoRepository(monitoramentoSource)
        val parametrosRepository = ParametrosRepository(parametrosSource)
        val estatisticasRepository = EstatisticasRepository(estatisticasSource)

        setContent {
            MaterialTheme {
                val parametrosViewModel: ParametrosViewModel = remember {
                    ViewModelProvider(
                        this,
                        ParametrosViewModelFactory(parametrosRepository)
                    ).get(ParametrosViewModel::class.java)
                }

                val monitoramentoViewModel: MonitoramentoViewModel = remember {
                    ViewModelProvider(
                        this,
                        MonitoramentoViewModelFactory(monitoramentoRepository)
                    ).get(MonitoramentoViewModel::class.java)
                }

                val estatisticasViewModel: EstatisticasViewModel = remember {
                    ViewModelProvider(
                        this,
                        EstatisticasViewModelFactory(estatisticasRepository)
                    ).get(EstatisticasViewModel::class.java)
                }

                AppNavigation(
                    monitoramentoViewModel = monitoramentoViewModel,
                    parametrosViewModel = parametrosViewModel,
                    estatisticasViewModel = estatisticasViewModel
                )
            }
        }
    }


    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        finishAffinity()
        //Necessário para impedir problemas relacionados com o envio e recebimento de mensagens
        // ao clicar no botão voltar
        exitProcess(0)
    }
}
