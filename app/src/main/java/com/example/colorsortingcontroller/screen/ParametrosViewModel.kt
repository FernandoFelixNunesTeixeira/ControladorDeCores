package com.example.colorsortingcontroller.screen

import android.util.Log
import android.util.MutableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.colorsortingcontroller.data.Monitoramento
import com.example.colorsortingcontroller.data.Parametros
import com.example.colorsortingcontroller.data.ParametrosRepository


import com.example.colorsortingcontroller.network.MQTTHandler
import com.example.colorsortingcontroller.network.MQTTUiState
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import com.google.gson.JsonParser
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import org.eclipse.paho.client.mqttv3.MqttClient
import java.lang.Thread.sleep


private val BROKER_URL = "ssl://77e0591acd6d4fb0b4cb6da7dc26b87b.s1.eu.hivemq.cloud:8883"
private val CLIENT_ID = "Android_ClientTestando"

private lateinit var mqttHandler: MQTTHandler

class ParametrosViewModel(private val parametrosRepository: ParametrosRepository) : ViewModel() {
    private val _state = MutableStateFlow(ScreenState.parametros)
    val state: StateFlow<ScreenState> = _state
    
    //val para impedir mudanças indesejáveis na variável
    private var valorParametrosList : MutableStateFlow<Int>? = null

    init {
        getConexao()
        subscribeToTopic("parametros", 1)
        subscribeToTopic("parametrosReceber", 1)
        updateParametrosFromDatabase()
        manipularMensagemMQTT()
    }

    fun getConexao() {
        viewModelScope.launch{
            try {
                mqttHandler = MQTTHandler.getInstance()
                mqttHandler.connect(BROKER_URL, CLIENT_ID)

            } catch(e: IOException) {
                MQTTUiState.Error
            }
        }
    }

    fun subscribeToTopic(topic: String, nivelQos: Int){
        viewModelScope.launch{
            try {
                mqttHandler.subscribe(topic, nivelQos)

            } catch(e: IOException) {
                MQTTUiState.Error
            }
        }
    }

    fun publishMessage(topic: String, message: String, nivelQos: Int, retainedFlag: Boolean) {
        viewModelScope.launch{
            try {
             //  Colocar Toast indicando que mensagem foi enviada na view
                mqttHandler.publish(topic, message, nivelQos, retainedFlag)
                delay(100)



            } catch(e: IOException) {
                MQTTUiState.Error
            }
        }
    }

    fun transformarObjetoJsoneEnviar( ) {
        //Delay necessário por evitar passar parametros novamente
        viewModelScope.launch{
            delay(100)
            try {
                var objetoJsonParametros = "{ "  + "\"" +  "PosicaoServoPortaAnguloMinimo" + "\" :" +
                        "\"" + stateParametros.value.posicaoServoPortaMin.toString() + "\"" + "," +
                        "\"" +  "PosicaoServoPortaAnguloMaximo" + "\" :" +
                        "\"" + stateParametros.value.posicaoServoPortaMax.toString() + "\"" +  "," +
                        "\"" +  "PosicaoServoDirecionadorEDAnguloMinimo"+ "\" :"  +
                        "\"" + stateParametros.value.posicaoServoDirecionadorEDMin.toString() + "\"" +  "," +
                        "\"" + "PosicaoServoDirecionadorEDAnguloMaximo"  + "\" :" +
                        "\"" + stateParametros.value.posicaoServoDirecionadorEDMax.toString() + "\"" + "," +
                        "\"" + "PosicaoServoDirecionador12AnguloMinimo"  +  "\" :" +
                        "\"" + stateParametros.value.posicaoServoDirecionador12Min.toString() + "\"" + "," +
                        "\"" + "PosicaoServoDirecionador12AnguloMaximo"  + "\" :" +
                        "\"" + stateParametros.value.posicaoServoDirecionador12Max.toString() + "\"" + "," +
                        "\"" + "PosicaoServoDirecionador34AnguloMinimo"  +  "\" :" +
                        "\"" + stateParametros.value.posicaoServoDirecionador34Min.toString() + "\"" + "," +
                        "\"" + "PosicaoServoDirecionador34AnguloMaximo"  + "\" :" +
                        "\"" +  stateParametros.value.posicaoServoDirecionador34Max.toString() + "\"" + "," +
                        "\"" + "Cor"  + "\" :" +
                        "\"" +  stateParametros.value.cor + "\"" + "," +
                        "\"" + "R"  + "\" :" +
                        "\"" +  stateParametros.value.rValue.toString() + "\"" + "," +
                        "\"" + "G"  + "\" :" +
                        "\"" +  stateParametros.value.gValue.toString() + "\"" + "," +
                        "\"" + "B"  + "\" :" +
                        "\"" +  stateParametros.value.bValue.toString() + "\"" + " }"
                publishMessage("parametros",  objetoJsonParametros, 1, false )
            } catch(e: IOException) {
                MQTTUiState.Error
            }
        }
    }

    private val _stateParametros = MutableStateFlow(ParametrosUiState())
    val stateParametros: StateFlow<ParametrosUiState> = _stateParametros
    val mensagemMQTT: LiveData<String?> get() = mqttHandler.mqttStateParametros
    val conexaoMQTT: LiveData<String> get() = mqttHandler.mqttState
    val mensagemEntregue: LiveData<String> get() = mqttHandler.mqttStateEntregaMensagem

     fun manipularMensagemMQTT(){
         viewModelScope.launch {
             val novosParametros = ParametrosUiState()

             //while(true) {


               //  delay(1)
                 //Conversão da mensagem em MQTT contendo uma string json para um objeto json
                 mensagemMQTT.observeForever { novaMensagem ->
                 synchronized(this) {
                     //if (mensagemMQTT.value != null ) {


                     if (novaMensagem != null) {


                         val objetoJson =
                             JsonParser.parseString(novaMensagem).asJsonObject

                         _stateParametros.value = ParametrosUiState(
                             objetoJson.get("PosicaoServoPortaAnguloMinimo").asInt,
                             objetoJson.get("PosicaoServoPortaAnguloMaximo").asInt,
                             objetoJson.get("PosicaoServoDirecionadorEDAnguloMinimo").asInt,
                             objetoJson.get("PosicaoServoDirecionadorEDAnguloMaximo").asInt,
                             objetoJson.get("PosicaoServoDirecionador12AnguloMinimo").asInt,
                             objetoJson.get("PosicaoServoDirecionador12AnguloMaximo").asInt,
                             objetoJson.get("PosicaoServoDirecionador34AnguloMinimo").asInt,
                             objetoJson.get("PosicaoServoDirecionador34AnguloMaximo").asInt,
                             objetoJson.get("Cor").asString,
                             objetoJson.get("R").asInt,
                             objetoJson.get("G").asInt,
                             objetoJson.get("B").asInt
                         )


                         if (valorParametrosList != null) {
                             update(
                                 _stateParametros.value.posicaoServoPortaMin,
                                 _stateParametros.value.posicaoServoPortaMax,
                                 _stateParametros.value.posicaoServoDirecionadorEDMin,
                                 _stateParametros.value.posicaoServoDirecionadorEDMax,
                                 _stateParametros.value.posicaoServoDirecionador12Min,
                                 _stateParametros.value.posicaoServoDirecionador12Max,
                                 _stateParametros.value.posicaoServoDirecionador34Min,
                                 _stateParametros.value.posicaoServoDirecionador34Max,
                                 _stateParametros.value.cor,
                                 _stateParametros.value.rValue,
                                 _stateParametros.value.gValue,
                                 _stateParametros.value.bValue
                             )
                             updateParametrosFromDatabase()
                             sleep(100)
                         } else {
                             insert(
                                 _stateParametros.value.posicaoServoPortaMin,
                                 _stateParametros.value.posicaoServoPortaMax,
                                 _stateParametros.value.posicaoServoDirecionadorEDMin,
                                 _stateParametros.value.posicaoServoDirecionadorEDMax,
                                 _stateParametros.value.posicaoServoDirecionador12Min,
                                 _stateParametros.value.posicaoServoDirecionador12Max,
                                 _stateParametros.value.posicaoServoDirecionador34Min,
                                 _stateParametros.value.posicaoServoDirecionador34Max,
                                 _stateParametros.value.cor,
                                 _stateParametros.value.rValue,
                                 _stateParametros.value.gValue,
                                 _stateParametros.value.bValue
                             )
                             updateParametrosFromDatabase()

                             //Garantir que não seja realizado mais de um insert
                             valorParametrosList = MutableStateFlow(1)
                             mqttHandler.mqttStateParametros
                             //    }
                             //   }
                         }
                     }
                 }
                 }
             }
        // }
    }

    // Recebe todos os valores do repositório
   val allParametros: Flow<List<Parametros>> = parametrosRepository.allParametros


    // Envia a lista de parametros para a UI
    val parametros = parametrosRepository.allParametros.map { list ->
        list.firstOrNull()
    }

    // Função da ViewModel para atualizar os dados
    fun update(
        posicaoServoPortaMin: Int,
        posicaoServoPortaMax: Int,
        posicaoServoDirecionadorEDMin: Int,
        posicaoServoDirecionadorEDMax: Int,
        posicaoServoDirecionador12Min: Int,
        posicaoServoDirecionador12Max: Int,
        posicaoServoDirecionador34Min: Int,
        posicaoServoDirecionador34Max: Int,
        cor: String,

        rValue: Int,
        gValue: Int,
        bValue: Int
    ) = viewModelScope.launch {
        withContext(Dispatchers.IO)
        {
            parametrosRepository.updateCor(
                cor = cor
            )

            parametrosRepository.updateRGBValues(
                rValue = rValue,
                gValue = gValue,
                bValue = bValue
            )

            parametrosRepository.updateDirecionadorPorta(
                posicaoServoPortaMin = posicaoServoPortaMin,
                posicaoServoPortaMax = posicaoServoPortaMax
            )

            parametrosRepository.updateDirecionadorED(
                posicaoServoDirecionadorEDMin = posicaoServoDirecionadorEDMin,
                posicaoServoDirecionadorEDMax = posicaoServoDirecionadorEDMax
            )

            parametrosRepository.updateDirecionador12(
                posicaoServoDirecionador12Min = posicaoServoDirecionador12Min,
                posicaoServoDirecionador12Max = posicaoServoDirecionador12Max,
            )

            parametrosRepository.updateDirecionador34(
                posicaoServoDirecionador34Min = posicaoServoDirecionador34Min,
                posicaoServoDirecionador34Max = posicaoServoDirecionador34Max
            )

        }

    }

    fun delete(id: Int)= viewModelScope.launch {
        withContext(Dispatchers.IO){
            parametrosRepository.deleteParametros(id)
        }
    }

    // Função da ViewModel para atualizar os dados
    fun insert(
        posicaoServoPortaMin: Int,
        posicaoServoPortaMax: Int,
        posicaoServoDirecionadorEDMin: Int,
        posicaoServoDirecionadorEDMax: Int,
        posicaoServoDirecionador12Min: Int,
        posicaoServoDirecionador12Max: Int,
        posicaoServoDirecionador34Min: Int,
        posicaoServoDirecionador34Max: Int,
        cor: String,

        rValue: Int,
        gValue: Int,
        bValue: Int
    ) = viewModelScope.launch {
        withContext(Dispatchers.IO){
            parametrosRepository.insertParametros(
                posicaoServoPortaMin, posicaoServoPortaMax,
                posicaoServoDirecionadorEDMin,posicaoServoDirecionadorEDMax,
                posicaoServoDirecionador12Min, posicaoServoDirecionador12Max,
                posicaoServoDirecionador34Min, posicaoServoDirecionador34Max,
                cor, rValue, gValue, bValue
            )
        }
    }
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

   data class ParametrosUiState (
        var posicaoServoPortaMin: Int = 0,
        var posicaoServoPortaMax: Int = 0,
        var posicaoServoDirecionadorEDMin: Int = 0,
        var posicaoServoDirecionadorEDMax: Int = 0,
        var posicaoServoDirecionador12Min: Int = 0,
        var posicaoServoDirecionador12Max: Int = 0,
        var posicaoServoDirecionador34Min: Int = 0,
        var posicaoServoDirecionador34Max: Int = 0,
        var cor: String = "vermelho",

        var rValue: Int = 0,
        var gValue: Int = 0,
        var bValue: Int = 0
    )

    fun updateParametrosFromDatabase() {
        viewModelScope.launch {
            parametrosRepository.allParametros.collect { parametrosList ->
                Log.d("ParametrosUpdate", "parametrosList: $parametrosList")
                if (parametrosList.isNotEmpty()) {
                    val novosParametros = ParametrosUiState(
                        posicaoServoPortaMin = parametrosList[0].posicaoServoPortaMin,
                        posicaoServoPortaMax = parametrosList[0].posicaoServoPortaMax,
                        posicaoServoDirecionadorEDMin = parametrosList[0].posicaoServoDirecionadorEDMin,
                        posicaoServoDirecionadorEDMax = parametrosList[0].posicaoServoDirecionadorEDMax,
                        posicaoServoDirecionador12Min = parametrosList[0].posicaoServoDirecionador12Min,
                        posicaoServoDirecionador12Max = parametrosList[0].posicaoServoDirecionador12Max,
                        posicaoServoDirecionador34Min = parametrosList[0].posicaoServoDirecionador34Min,
                        posicaoServoDirecionador34Max = parametrosList[0].posicaoServoDirecionador34Max,
                        cor = parametrosList[0].cor,
                        rValue = parametrosList[0].rValue,
                        gValue = parametrosList[0].gValue,
                        bValue = parametrosList[0].bValue
                    )
                    _stateParametros.value = novosParametros
                    Log.d("ParametrosUpdate", "Novos parâmetros: $novosParametros")
                    valorParametrosList = MutableStateFlow(1)
                    delay(100)
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

