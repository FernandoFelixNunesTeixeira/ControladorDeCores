package com.example.colorsortingcontroller.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.colorsortingcontroller.data.Parametros
import com.example.colorsortingcontroller.data.ParametrosLocalSource
import com.example.colorsortingcontroller.data.ParametrosRepository
import com.example.colorsortingcontroller.network.MQTTHandler

import com.example.colorsortingcontroller.network.MQTTUiState
import com.example.colorsortingcontroller.network.objetoJsonParametros

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import kotlin.properties.Delegates


private val BROKER_URL = "ssl://44a41899400a4d2687717200b79f04cb.s1.eu.hivemq.cloud:8883"
private val CLIENT_ID = "Android_Client"
private lateinit var mqttHandler: MQTTHandler
//private var ValorNovo by Delegates.notNull<Int>()

class ParametrosViewModel(private val parametrosRepository: ParametrosRepository) : ViewModel() {
    private val _state = MutableStateFlow(ScreenState.parametros)
    val state: StateFlow<ScreenState> = _state

    init {
        getConexao()
        subscribeToTopic("parametros", 1)
        obterTopico()
        //obterJson()
        updateParametrosFromDatabase()
    }


    //private val _mqqtState = MutableStateFlow(MQTTHandler)
    //val state3: StateFlow<MQTTHandler.Companion> = _mqqtState
    //var a : String? = getObjetoJsonParametros()

    fun obterTopico() {

    }

    fun getConexao() {
        viewModelScope.launch{
            try {
                mqttHandler = MQTTHandler()
                mqttHandler.connect(BROKER_URL , CLIENT_ID )

            } catch(e: IOException) {
                ColorUiState.Error
            }
        }
    }

    fun subscribeToTopic(topic: String, nivelQos: Int){
        viewModelScope.launch{
            try {
                mqttHandler.subscribe(topic, nivelQos);

            } catch(e: IOException) {
                ColorUiState.Error
            }
        }
    }

    fun publishMessage(topic: String, message: String, nivelQos: Int, retainedFlag: Boolean) {
        viewModelScope.launch{
            try {
             //  Colocar Toast indicando que mensagem foi enviada na view
                mqttHandler.publish(topic, message, nivelQos, retainedFlag)
            } catch(e: IOException) {
                ColorUiState.Error
            }
        }
    }

    private val _state2 = MutableStateFlow(ParametrosUiState())
    val state2: StateFlow<ParametrosUiState> = _state2

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

    class ParametrosUiState (
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
                    _state2.value = novosParametros
                    Log.d("ParametrosUpdate", "Novos parâmetros: $novosParametros")
                }
            }
        }
    }
}

