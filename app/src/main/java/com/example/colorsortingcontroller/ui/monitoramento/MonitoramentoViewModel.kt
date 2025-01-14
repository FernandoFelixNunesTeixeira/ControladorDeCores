package com.example.colorsortingcontroller.monitoramento

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.colorsortingcontroller.ScreenState
import com.example.colorsortingcontroller.data.repository.MonitoramentoRepository
import com.example.colorsortingcontroller.network.MQTTHandler

import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.UUID

private val BROKER_URL = "ssl://4b5548dcb4844ac9bd65c4373c6b8537.s1.eu.hivemq.cloud:8883"
private val CLIENT_ID = UUID.randomUUID().toString()// Aleatorizar ID
// Permite que uma mesma conta logue ao mesmo tempo em dois dispositivos diferentes
private lateinit var mqttHandler: MQTTHandler

class MonitoramentoViewModel(private val monitoramentoRepository: MonitoramentoRepository) : ViewModel() {
    private val _state = MutableStateFlow(ScreenState.monitoramento)
    val state: StateFlow<ScreenState> = _state

    //variável para controlar insert e updates no banco de dados
    private var valorMonitoramentoList : MutableStateFlow<Int>? = null

    init {
        getConexao()
        updateMonitoramentoFromDatabase()
        subscribeToTopic("monitoramento", 1)
        subscribeToTopic("monitoramentoReceber", 1)
        manipularMensagemMQTT()
    }

    // Envia a lista de monitoramento para a UI
    val monitoramento = monitoramentoRepository.allMonitoramento.map { list ->
        list.firstOrNull()
    }

    // Função UPDATE
    fun updateMonitoramento(
        estado: String,
        corAtual: String,
        sensorR: Float,
        sensorG: Float,
        sensorB: Float,
        portaAberta: Boolean,
        coletorAtual: Int
    ) = viewModelScope.launch {
        withContext(Dispatchers.IO)
        {
            monitoramentoRepository.updateEstado(
                estado = estado
            )
            monitoramentoRepository.updateCorAtual(
                corAtual = corAtual
            )

            monitoramentoRepository.updateSensorR(
                sensorR = sensorR
            )

            monitoramentoRepository.updateSensorG(
                sensorG = sensorG
            )

            monitoramentoRepository.updateSensorB(
                sensorB = sensorB
            )

            monitoramentoRepository.updatePortaAberta(
                portaAberta = portaAberta
            )

            monitoramentoRepository.updateColetorAtual(
                coletorAtual = coletorAtual
            )
        }
    }

    // Função DELETE
    fun deleteMonitoramento(id: Int)= viewModelScope.launch {
        withContext(Dispatchers.IO) {
            monitoramentoRepository.deleteMonitoramento(id)
        }
    }

    // Função INSERT
    fun insertMonitoramento(
        estado: String,
        corAtual: String,
        sensorR: Float,
        sensorG: Float,
        sensorB: Float,
        portaAberta: Boolean,
        coletorAtual: Int
    ) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            monitoramentoRepository.insertMonitoramento(
                estado, corAtual, sensorR, sensorG, sensorB, portaAberta, coletorAtual
            )
        }
    }
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    data class MonitoramentoUiState(
        var estado: String = "",
        var corAtual: String = "",
        val sensorR: Float = 0.toFloat(),
        val sensorG: Float = 0.toFloat(),
        val sensorB: Float= 0.toFloat(),
        val portaAberta: Boolean = false,
        val coletorAtual: Int = 0,
    )

    fun getConexao() {
        viewModelScope.launch {
            try {
                mqttHandler = MQTTHandler.getInstance()
                mqttHandler.connect(BROKER_URL, CLIENT_ID)
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    fun subscribeToTopic(topic: String, nivelQos: Int) {
        viewModelScope.launch {
            try {
                mqttHandler.subscribe(topic, nivelQos)
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    private val _stateMonitoramento = MutableStateFlow(MonitoramentoUiState())
    val stateMonitoramento: StateFlow<MonitoramentoUiState> = _stateMonitoramento

    val mensagemMQTT: LiveData<String> get() = mqttHandler.mqttStateMonitoramento
    val conexaoMQTT: LiveData<String> get() = mqttHandler.mqttState

    fun manipularMensagemMQTT() {
        viewModelScope.launch {
            try {
                mensagemMQTT.observeForever { novaMensagem ->
                    //Conversão da mensagem em MQTT contendo uma string json para um objeto json
                    if (novaMensagem != null) {
                        //Não tem entrada de usuário
                        val objetoJson =
                            JsonParser.parseString(novaMensagem).asJsonObject
                        _stateMonitoramento.value = MonitoramentoUiState(
                            objetoJson.get("EstadoAtual").asString,
                            objetoJson.get("CorAtual").asString,
                            objetoJson.get("SensorR").asFloat,
                            objetoJson.get("SensorG").asFloat,
                            objetoJson.get("SensorB").asFloat,
                            objetoJson.get("PortaAberta").asBoolean,
                            objetoJson.get("ColetorAtual").asInt
                        )
                        if (valorMonitoramentoList != null) {
                            updateMonitoramento(
                                _stateMonitoramento.value.estado,
                                _stateMonitoramento.value.corAtual,
                                _stateMonitoramento.value.sensorR,
                                _stateMonitoramento.value.sensorG,
                                _stateMonitoramento.value.sensorB,
                                _stateMonitoramento.value.portaAberta,
                                _stateMonitoramento.value.coletorAtual,
                            )
                        } else {
                            insertMonitoramento(
                                _stateMonitoramento.value.estado,
                                _stateMonitoramento.value.corAtual,
                                _stateMonitoramento.value.sensorR,
                                _stateMonitoramento.value.sensorG,
                                _stateMonitoramento.value.sensorB,
                                _stateMonitoramento.value.portaAberta,
                                _stateMonitoramento.value.coletorAtual,
                            )
                        }
                    }
                }
            } catch(e: Throwable){
                e.printStackTrace()
            }
        }
    }

    fun updateMonitoramentoFromDatabase() {
        viewModelScope.launch {
            monitoramentoRepository.allMonitoramento.collect { monitoramentoList ->
                Log.d("MonitoramentoUpdate", "monitoramentoList: $monitoramentoList")
                if (monitoramentoList.isNotEmpty()) {
                    val novosMonitoramentos = MonitoramentoUiState(
                        estado = monitoramentoList[0].estado,
                        corAtual = monitoramentoList[0].corAtual,
                        sensorR = monitoramentoList[0].sensorR,
                        sensorG = monitoramentoList[0].sensorG,
                        sensorB = monitoramentoList[0].sensorB,
                        portaAberta = monitoramentoList[0].portaAberta.toString().toBooleanStrict(),
                        coletorAtual = monitoramentoList[0].coletorAtual
                    )
                    _stateMonitoramento.value = novosMonitoramentos
                    Log.d("MonitoramentoUpdate", "Novos monitoramentos: $novosMonitoramentos")
                    valorMonitoramentoList = MutableStateFlow(1)
                }
            }
        }
    }

    override fun onCleared(){
        super.onCleared()
        //Encerrar a conexão
        mqttHandler.disconnect()
    }
}