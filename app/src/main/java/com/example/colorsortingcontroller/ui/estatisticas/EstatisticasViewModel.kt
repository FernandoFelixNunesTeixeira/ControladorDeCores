package com.example.colorsortingcontroller.estatisticas

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.colorsortingcontroller.ScreenState
import com.example.colorsortingcontroller.data.repository.EstatisticasRepository
import com.example.colorsortingcontroller.network.MQTTHandler

import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.UUID

private val BROKER_URL = "ssl://4b5548dcb4844ac9bd65c4373c6b8537.s1.eu.hivemq.cloud:8883"
private val CLIENT_ID = UUID.randomUUID().toString()

private lateinit var mqttHandler: MQTTHandler

class EstatisticasViewModel(private val estatisticasRepository: EstatisticasRepository) : ViewModel() {
    private val _state = MutableStateFlow(ScreenState.estatisticas)
    val state: StateFlow<ScreenState> = _state

    //variável para controlar insert e updates no banco de dados
    private var valorEstatisticasList : MutableStateFlow<Int>? = null

    init {
        getConexao()
        updateEstatisticasFromDatabase()
        subscribeToTopic("estatisticas", 1)
        subscribeToTopic("estatisticasReceber", 1)
        subscribeToTopic("novasEstatisticas", 1)
        enviarSolicitacao()
        manipularMensagemMQTT()
    }

    // Envia a lista de monitoramento para a UI
    val estatisticas = estatisticasRepository.allEstatisticas.map { list ->
        list.firstOrNull()
    }

    // Função UPDATE
    fun updateEstatisticas(
        pecasCor1: Int,
        pecasCor2: Int,
        pecasCor3: Int,
        pecasCor4: Int,
        pecasCor5: Int,
        pecasCor6: Int,
        pecasCor7: Int,


        pecasColetor1: Int,
        pecasColetor2: Int,
        pecasColetor3: Int,
        pecasColetor4: Int
    ) = viewModelScope.launch {
        withContext(Dispatchers.IO)
        {
            estatisticasRepository.updatePecasCor(
                pecasCor1 = pecasCor1,
                pecasCor2 = pecasCor2,
                pecasCor3 = pecasCor3,
                pecasCor4 = pecasCor4,
                pecasCor5 = pecasCor5,
                pecasCor6 = pecasCor6,
                pecasCor7 = pecasCor7
            )
            estatisticasRepository.updatePecasColetor(
                pecasColetor1 = pecasColetor1,
                pecasColetor2 = pecasColetor2,
                pecasColetor3 = pecasColetor3,
                pecasColetor4 = pecasColetor4
            )
        }
    }

    // Função DELETE
    fun deleteEstatisticas(id: Int)= viewModelScope.launch {
        withContext(Dispatchers.IO) {
            estatisticasRepository.deleteEstatisticas(id)
        }
    }

    // Função INSERT
    fun insertEstatisticas(
        pecasCor1: Int,
        pecasCor2: Int,
        pecasCor3: Int,
        pecasCor4: Int,
        pecasCor5: Int,
        pecasCor6: Int,
        pecasCor7: Int,


        pecasColetor1: Int,
        pecasColetor2: Int,
        pecasColetor3: Int,
        pecasColetor4: Int
    ) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            estatisticasRepository.insertEstatisticas(
                pecasCor1,
                pecasCor2,
                pecasCor3,
                pecasCor4,
                pecasCor5,
                pecasCor6,
                pecasCor7,
                pecasColetor1,
                pecasColetor2,
                pecasColetor3,
                pecasColetor4
            )
        }
    }
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }





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
                mqttHandler.subscribe(topic, nivelQos);

            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    private val _stateEstatisticas = MutableStateFlow(EstatisticasUiState())
    val stateEstatisticas: StateFlow<EstatisticasUiState> = _stateEstatisticas

    val mensagemMQTT: LiveData<String> get() = mqttHandler.mqttStateEstatisticas
    val conexaoMQTT: LiveData<String> get() = mqttHandler.mqttState

    var tempoEspera = System.currentTimeMillis()
    fun manipularMensagemMQTT() {
        viewModelScope.launch {
                try {
                    //Conversão da mensagem em MQTT contendo uma string json para um objeto json
                    mensagemMQTT.observeForever { novaMensagem ->
                     //Não tem entrada de usuário
                        if (novaMensagem != null) {
                            val objetoJson =
                                JsonParser.parseString(novaMensagem).asJsonObject
                            _stateEstatisticas.value = EstatisticasUiState(
                                objetoJson.get("PecasSeparadasCor1").asInt,
                                objetoJson.get("PecasSeparadasCor2").asInt,
                                objetoJson.get("PecasSeparadasCor3").asInt,
                                objetoJson.get("PecasSeparadasCor4").asInt,
                                objetoJson.get("PecasSeparadasCor5").asInt,
                                objetoJson.get("PecasSeparadasCor6").asInt,
                                objetoJson.get("PecasSeparadasCor7").asInt,
                              
                                objetoJson.get("PecasSeparadasColetor1").asInt,
                                objetoJson.get("PecasSeparadasColetor2").asInt,
                                objetoJson.get("PecasSeparadasColetor3").asInt,
                                objetoJson.get("PecasSeparadasColetor4").asInt
                            )
                            if (valorEstatisticasList != null) {
                                updateEstatisticas(
                                    _stateEstatisticas.value.pecasCor1,
                                    _stateEstatisticas.value.pecasCor2,
                                    _stateEstatisticas.value.pecasCor3,
                                    _stateEstatisticas.value.pecasCor4,
                                    _stateEstatisticas.value.pecasCor5,
                                    _stateEstatisticas.value.pecasCor6,
                                    _stateEstatisticas.value.pecasCor7,
                                    _stateEstatisticas.value.pecasColetor1,
                                    _stateEstatisticas.value.pecasColetor2,
                                    _stateEstatisticas.value.pecasColetor3,
                                    _stateEstatisticas.value.pecasColetor4,
                                )
                                updateEstatisticasFromDatabase()
                            } else {
                                insertEstatisticas(
                                    _stateEstatisticas.value.pecasCor1,
                                    _stateEstatisticas.value.pecasCor2,
                                    _stateEstatisticas.value.pecasCor3,
                                    _stateEstatisticas.value.pecasCor4,
                                    _stateEstatisticas.value.pecasCor5,
                                    _stateEstatisticas.value.pecasCor6,
                                    _stateEstatisticas.value.pecasCor7,

                                    _stateEstatisticas.value.pecasColetor1,
                                    _stateEstatisticas.value.pecasColetor2,
                                    _stateEstatisticas.value.pecasColetor3,
                                    _stateEstatisticas.value.pecasColetor4,
                                )
                            }
                        }

                    }
                } catch(e: Throwable){
                    e.printStackTrace()
                }
        }
    }

    data class EstatisticasUiState(
        var pecasCor1: Int = 0,
        var pecasCor2: Int = 0,
        var pecasCor3: Int = 0,
        var pecasCor4: Int = 0,
        var pecasCor5: Int = 0,
        var pecasCor6: Int = 0,
        var pecasCor7: Int = 0,
        var pecasColetor1: Int = 0,
        var pecasColetor2: Int = 0,
        var pecasColetor3: Int = 0,
        var pecasColetor4: Int = 0
    )

    fun publishMessage(topic: String, message: String, nivelQos: Int, retainedFlag: Boolean) {
        viewModelScope.launch{
            try {
                //  Colocar Toast indicando que mensagem foi enviada na view
                mqttHandler.publish(topic, message, nivelQos, retainedFlag)




            } catch(e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun enviarSolicitacao(){
        viewModelScope.launch{
            try {
                publishMessage("novasEstatisticas", "Envie estatisticas", 1, false )
            } catch(e: IOException) {
                e.printStackTrace()
            } catch(e: Throwable) {
                e.printStackTrace()
            }

        }
    }

    fun updateEstatisticasFromDatabase() {
        viewModelScope.launch {
            estatisticasRepository.allEstatisticas.collect { estatisticasList ->
                Log.d("EstatisticasUpdate", "estatisticasList: $estatisticasList")
                if (estatisticasList.isNotEmpty()) {
                    val novasEstatisticas = EstatisticasUiState(
                        pecasCor1 = estatisticasList[0].pecasCor1,
                        pecasCor2 = estatisticasList[0].pecasCor2,
                        pecasCor3 = estatisticasList[0].pecasCor3,
                        pecasCor4 = estatisticasList[0].pecasCor4,
                        pecasCor5 = estatisticasList[0].pecasCor5,
                        pecasCor6 = estatisticasList[0].pecasCor6,
                        pecasCor7 = estatisticasList[0].pecasCor7,


                        pecasColetor1 = estatisticasList[0].pecasColetor1,
                        pecasColetor2 = estatisticasList[0].pecasColetor2,
                        pecasColetor3 = estatisticasList[0].pecasColetor3,
                        pecasColetor4 = estatisticasList[0].pecasColetor4
                    )
                    _stateEstatisticas.value = novasEstatisticas
                    Log.d("EstatisticasUpdate", "Novas estatísticas: $novasEstatisticas")
                    valorEstatisticasList = MutableStateFlow(1)
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