package com.example.colorsortingcontroller.screen

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.colorsortingcontroller.data.Monitoramento
import com.example.colorsortingcontroller.data.MonitoramentoRepository
import com.example.colorsortingcontroller.data.Parametros


import com.example.colorsortingcontroller.network.MQTTHandler
import com.example.colorsortingcontroller.network.MQTTUiState
import com.example.colorsortingcontroller.screen.ParametrosViewModel.ParametrosUiState
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException


private val BROKER_URL = "ssl://77e0591acd6d4fb0b4cb6da7dc26b87b.s1.eu.hivemq.cloud:8883"
private val CLIENT_ID = "Android_ClientTestando"
private lateinit var mqttHandler: MQTTHandler


class MonitoramentoViewModel(private val monitoramentoRepository: MonitoramentoRepository) : ViewModel() {
    private val _state = MutableStateFlow(ScreenState.monitoramento)
    val state: StateFlow<ScreenState> = _state

    //val para impedir mudanças indesejáveis na variável
    private val valorMonitoramentoList : ((List<Monitoramento>) -> Unit)? = null



    init {
        getConexao()


        subscribeToTopic("monitoramento", 1)
        subscribeToTopic("monitoramentoReceber", 1)
       // manipularMensagemMQTT()
        //obterTopico()
        //obterJson()

        // Apenas para testes iniciais:
     //   insertMonitoramento("tst_AbrePortaaa", "tst_Vermelhoooo")


        updateMonitoramentoFromDatabase()
        manipularMensagemMQTT()

     //   updateMonitoramentoFromDatabase()
      //  updateMonitoramentoFromDatabase()
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

                mqttHandler = MQTTHandler.getInstance()
                mqttHandler.connect(BROKER_URL, CLIENT_ID)



             //   mqttHandler.connect(BROKER_URL, CLIENT_ID)
            // MQTTHandler() mqttHandler.connect()

            } catch (e: IOException) {
                MQTTUiState.Error
            }
        }
    }

    fun subscribeToTopic(topic: String, nivelQos: Int) {
        viewModelScope.launch {
            try {
                mqttHandler.subscribe(topic, nivelQos);

            } catch (e: IOException) {
                MQTTUiState.Error
            }
        }
    }


    private val _stateMonitoramento = MutableStateFlow(MonitoramentoUiState())
    val stateMonitoramento: StateFlow<MonitoramentoUiState> = _stateMonitoramento

    //   val conexaoParametrosUiState = mqttHandler.state3.value

    val mensagemMQTT: LiveData<String> get() = mqttHandler.mqttStateMonitoramento



    fun manipularMensagemMQTT() {
        viewModelScope.launch {
            while (true) {
                //Instância do objeto GSON
                delay(1)
                //val gson = Gson()

                synchronized(this) {
                //Conversão da mensagem em MQTT contendo uma string json para um objeto json
                if (mensagemMQTT.value != null) {
                    val objetoJson =
                        JsonParser.parseString(mensagemMQTT.value).asJsonObject
                    val novoMonitoramento = MonitoramentoUiState(
                        objetoJson.get("EstadoAtual").asString,
                        objetoJson.get("CorAtual").asString
                    )
                        _stateMonitoramento.value = novoMonitoramento
                        if (valorMonitoramentoList != null) {
                            updateMonitoramento(
                                novoMonitoramento.estado,
                                novoMonitoramento.corAtual
                            )
                        } else {
                            insertMonitoramento(
                                novoMonitoramento.estado,
                                novoMonitoramento.corAtual
                            )
                        }
                    }
                    //updateMonitoramento(_stateMonitoramento.value.estado, _stateMonitoramento.value.corAtual)
                    //updateMonitoramento(_stateMonitoramento.value.estado, _stateMonitoramento.value.corAtual)
                 //   delay(500)
                //    updateMonitoramento(_stateMonitoramento.value.estado, _stateMonitoramento.value.corAtual)


                }
            }
        }
        //Mensagem para debug, deixar pelo menos enquanto não tiver o projeto praticamente pronto
        println(" Mensagem atual: ${mensagemMQTT.value}")
    }

    //var ListaMonitoramento : List<Monitoramento> = emptyList()

    fun updateMonitoramentoFromDatabase() {
        viewModelScope.launch {

                monitoramentoRepository.allMonitoramento.collect { monitoramentoList ->
                    Log.d("MonitoramentoUpdate", "monitoramentoList: $monitoramentoList")
                    if (monitoramentoList.isNotEmpty()) {
                        val novosMonitoramentos = MonitoramentoUiState(
                            estado = monitoramentoList[0].estado,
                            corAtual = monitoramentoList[0].corAtual
                        )
                        // ListaMonitoramento = monitoramentoList

                        _stateMonitoramento.value = novosMonitoramentos
                        Log.d("MonitoramentoUpdate", "Novos monitoramentos: $novosMonitoramentos")
                        valorMonitoramentoList?.invoke(monitoramentoList)
                    }

                }

        }
    }
}