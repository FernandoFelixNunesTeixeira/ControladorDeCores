package com.example.colorsortingcontroller.screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.colorsortingcontroller.data.Estatisticas
import com.example.colorsortingcontroller.data.EstatisticasRepository
import com.example.colorsortingcontroller.data.Monitoramento
import com.example.colorsortingcontroller.data.MonitoramentoRepository

import com.example.colorsortingcontroller.network.MQTTHandler
import com.example.colorsortingcontroller.network.MQTTUiState
import com.example.colorsortingcontroller.screen.MonitoramentoViewModel.MonitoramentoUiState
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

private val BROKER_URL = "ssl://77e0591acd6d4fb0b4cb6da7dc26b87b.s1.eu.hivemq.cloud:8883"
private val CLIENT_ID = "Android_ClientTestando"
private lateinit var mqttHandler: MQTTHandler


class EstatisticasViewModel(private val estatisticasRepository: EstatisticasRepository) : ViewModel() {
    private val _state = MutableStateFlow(ScreenState.estatisticas)
    val state: StateFlow<ScreenState> = _state

    //val para impedir mudanças indesejáveis na variável
    private val valorEstatisticasList : ((List<Estatisticas>) -> Unit)? = null

    init {
        getConexao()

        subscribeToTopic("estatisticas", 1)
        subscribeToTopic("estatisticasReceber", 1)
    //    manipularMensagemMQTT()
        //obterTopico()
        //obterJson()

        // Apenas para testes iniciais:
      //  insertEstatisticas(12, 5, 10, 12, 33, 41,
       //     4, 7, 93, 12, 55, 32)

        updateEstatisticasFromDatabase()


        manipularMensagemMQTT()


    }

    // Recebe todos os valores do repositório
    val allEstatisticas: Flow<List<Estatisticas>> = estatisticasRepository.allEstatisticas

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
        pecasCor8: Int,

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
                pecasCor7 = pecasCor7,
                pecasCor8 = pecasCor8
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
        withContext(Dispatchers.IO){
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
        pecasCor8: Int,

        pecasColetor1: Int,
        pecasColetor2: Int,
        pecasColetor3: Int,
        pecasColetor4: Int
    ) = viewModelScope.launch {
        withContext(Dispatchers.IO){
            estatisticasRepository.insertEstatisticas(
                pecasCor1,
                pecasCor2,
                pecasCor3,
                pecasCor4,
                pecasCor5,
                pecasCor6,
                pecasCor7,
                pecasCor8,
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


    private val _stateEstatisticas = MutableStateFlow(EstatisticasUiState())
    val stateEstatisticas: StateFlow<EstatisticasUiState> = _stateEstatisticas
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
                delay(1)
                //val gson = Gson()

                //Conversão da mensagem em MQTT contendo uma string json para um objeto json
                synchronized(this) {
                if (mensagemMQTT.value != null) {
                    val objetoJson =
                        JsonParser.parseString(mensagemMQTT.value).asJsonObject
                    _stateEstatisticas.value = EstatisticasUiState(
                        objetoJson.get("PecasSeparadasCor1").asInt,
                        objetoJson.get("PecasSeparadasCor2").asInt,
                        objetoJson.get("PecasSeparadasCor3").asInt,
                        objetoJson.get("PecasSeparadasCor4").asInt,
                        objetoJson.get("PecasSeparadasCor5").asInt,
                        objetoJson.get("PecasSeparadasCor6").asInt,
                        objetoJson.get("PecasSeparadasCor7").asInt,
                        objetoJson.get("PecasSeparadasCor8").asInt,
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
                            _stateEstatisticas.value.pecasCor8,
                            _stateEstatisticas.value.pecasColetor1,
                            _stateEstatisticas.value.pecasColetor2,
                            _stateEstatisticas.value.pecasColetor3,
                            _stateEstatisticas.value.pecasColetor4,
                        )
                    } else {
                        insertEstatisticas(
                            _stateEstatisticas.value.pecasCor1,
                            _stateEstatisticas.value.pecasCor2,
                            _stateEstatisticas.value.pecasCor3,
                            _stateEstatisticas.value.pecasCor4,
                            _stateEstatisticas.value.pecasCor5,
                            _stateEstatisticas.value.pecasCor6,
                            _stateEstatisticas.value.pecasCor7,
                            _stateEstatisticas.value.pecasCor8,
                            _stateEstatisticas.value.pecasColetor1,
                            _stateEstatisticas.value.pecasColetor2,
                            _stateEstatisticas.value.pecasColetor3,
                            _stateEstatisticas.value.pecasColetor4,
                        )
                    }

                    updateEstatisticas(
                        _stateEstatisticas.value.pecasCor1,
                        _stateEstatisticas.value.pecasCor2,
                        _stateEstatisticas.value.pecasCor3,
                        _stateEstatisticas.value.pecasCor4,
                        _stateEstatisticas.value.pecasCor5,
                        _stateEstatisticas.value.pecasCor6,
                        _stateEstatisticas.value.pecasCor7,
                        _stateEstatisticas.value.pecasCor8,
                        _stateEstatisticas.value.pecasColetor1,
                        _stateEstatisticas.value.pecasColetor2,
                        _stateEstatisticas.value.pecasColetor3,
                        _stateEstatisticas.value.pecasColetor4,
                    )
                }
                /*    updateEstatisticas(
                        _stateEstatisticas.value.pecasCor1,
                        _stateEstatisticas.value.pecasCor2,
                        _stateEstatisticas.value.pecasCor3,
                        _stateEstatisticas.value.pecasCor4,
                        _stateEstatisticas.value.pecasCor5,
                        _stateEstatisticas.value.pecasCor6,
                        _stateEstatisticas.value.pecasCor7,
                        _stateEstatisticas.value.pecasCor8,
                        _stateEstatisticas.value.pecasColetor1,
                        _stateEstatisticas.value.pecasColetor2,
                        _stateEstatisticas.value.pecasColetor3,
                        _stateEstatisticas.value.pecasColetor4,
                    ) */

                }
            }
        }
        //Mensagem para debug, deixar pelo menos enquanto não tiver o projeto praticamente pronto
        println(" Mensagem atual: ${mensagemMQTT.value}")
    }

    data class EstatisticasUiState(
        var pecasCor1: Int = 0,
        var pecasCor2: Int = 0,
        var pecasCor3: Int = 0,
        var pecasCor4: Int = 0,
        var pecasCor5: Int = 0,
        var pecasCor6: Int = 0,
        var pecasCor7: Int = 0,
        var pecasCor8: Int = 0,

        var pecasColetor1: Int = 0,
        var pecasColetor2: Int = 0,
        var pecasColetor3: Int = 0,
        var pecasColetor4: Int = 0
    )

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
                        pecasCor8 = estatisticasList[0].pecasCor8,

                        pecasColetor1 = estatisticasList[0].pecasColetor1,
                        pecasColetor2 = estatisticasList[0].pecasColetor2,
                        pecasColetor3 = estatisticasList[0].pecasColetor3,
                        pecasColetor4 = estatisticasList[0].pecasColetor4
                    )
                    _stateEstatisticas.value = novasEstatisticas
                    Log.d("EstatisticasUpdate", "Novas estatísticas: $novasEstatisticas")
                    valorEstatisticasList?.invoke(estatisticasList)
                }

            }
        }
    }
}