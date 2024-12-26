package com.example.colorsortingcontroller.screen

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.colorsortingcontroller.data.Monitoramento
import com.example.colorsortingcontroller.data.MonitoramentoRepository
import com.example.colorsortingcontroller.data.Parametros
import com.example.colorsortingcontroller.network.MQTTHandler
import com.example.colorsortingcontroller.screen.ParametrosViewModel.ParametrosUiState
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException


private val BROKER_URL = "ssl://44a41899400a4d2687717200b79f04cb.s1.eu.hivemq.cloud:8883"
private val CLIENT_ID = "Android_Client3"
private lateinit var mqttHandler: MQTTHandler

class MonitoramentoViewModel(private val monitoramentoRepository: MonitoramentoRepository) : ViewModel() {
    private val _state = MutableStateFlow(ScreenState.monitoramento)
    val state: StateFlow<ScreenState> = _state

    init {
        getConexao()
        subscribeToTopic("monitoramento", 1)
        subscribeToTopic("monitoramentoReceber", 1)
        manipularMensagemMQTT()
        //obterTopico()
        //obterJson()

        // Apenas para testes iniciais:
        insertMonitoramento("tst_AbrePorta", "tst_Vermelho")

        updateMonitoramentoFromDatabase()
        //manipularMensagemMQTT()
        //getValor()
        //ReceberAtualizar()
    }

    // Recebe todos os valores do repositório
    val allMonitoramento: Flow<List<Monitoramento>> = monitoramentoRepository.allMonitoramento

    // Envia a lista de monitoramento para a UI
    val monitoramento = monitoramentoRepository.allMonitoramento.map { list ->
        list.firstOrNull()
    }

    // Função UPDATE
    fun updateMonitoramento(
        estado: String,
        corAtual: String
    ) = viewModelScope.launch {
        withContext(Dispatchers.IO)
        {
            monitoramentoRepository.updateEstado(
                estado = estado
            )
            monitoramentoRepository.updateCorAtual(
                corAtual = corAtual
            )
        }
    }

    // Função DELETE
    fun deleteMonitoramento(id: Int)= viewModelScope.launch {
        withContext(Dispatchers.IO){
            monitoramentoRepository.deleteMonitoramento(id)
        }
    }

    // Função INSERT
    fun insertMonitoramento(
        estado: String,
        corAtual: String
    ) = viewModelScope.launch {
        withContext(Dispatchers.IO){
            monitoramentoRepository.insertMonitoramento(
                estado, corAtual
            )
        }
    }
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    data class MonitoramentoUiState(
        var estado: String = "",
        var corAtual: String = ""
    )

    fun getConexao() {
        viewModelScope.launch {
            try {
                mqttHandler = MQTTHandler()
                mqttHandler.connect(BROKER_URL, CLIENT_ID)

            } catch (e: IOException) {
                ColorUiState.Error
            }
        }
    }

    fun subscribeToTopic(topic: String, nivelQos: Int) {
        viewModelScope.launch {
            try {
                mqttHandler.subscribe(topic, nivelQos);

            } catch (e: IOException) {
                ColorUiState.Error
            }
        }
    }


    private val _stateMonitoramento = MutableStateFlow(MonitoramentoUiState())
    val stateMonitoramento: StateFlow<MonitoramentoUiState> = _stateMonitoramento
    //  .stateIn(
    //      scope = viewModelScope,
    //      started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
    //     initialValue = EstatisticasUiState()
    //  )
    //   val conexaoParametrosUiState = mqttHandler.state3.value

    val mensagemMQTT: LiveData<String> get() = mqttHandler.mqttStateEstatisticas



    fun manipularMensagemMQTT() {
        viewModelScope.launch {
            while (true) {
                //Instância do objeto GSON
                delay(5000)
                //val gson = Gson()


                //Conversão da mensagem em MQTT contendo uma string json para um objeto json
                if (mqttHandler.mqttStateMonitoramento.value != null) {
                    val objetoJson =
                        JsonParser.parseString(mqttHandler.mqttStateMonitoramento.value).asJsonObject
                    _stateMonitoramento.value = MonitoramentoUiState(
                        objetoJson.get("EstadoAtual").asString,
                        objetoJson.get("CorAtual").asString
                    )


                }
            }
        }
        //Mensagem para debug, deixar pelo menos enquanto não tiver o projeto praticamente pronto
        println(" Mensagem atual: ${mqttHandler.mqttStateMonitoramento.value}")
    }

    fun updateMonitoramentoFromDatabase() {
        viewModelScope.launch {
            monitoramentoRepository.allMonitoramento.collect { monitoramentoList ->
                Log.d("MonitoramentoUpdate", "monitoramentoList: $monitoramentoList")
                if (monitoramentoList.isNotEmpty()) {
                    val novosMonitoramentos = MonitoramentoUiState(
                        estado = monitoramentoList[0].estado,
                        corAtual = monitoramentoList[0].corAtual
                    )
                    _stateMonitoramento.value = novosMonitoramentos
                    Log.d("MonitoramentoUpdate", "Novos monitoramentos: $novosMonitoramentos")
                }
            }
        }
    }
}