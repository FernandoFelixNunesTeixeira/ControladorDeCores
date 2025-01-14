package com.example.colorsortingcontroller.parametros

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.colorsortingcontroller.ScreenState
import com.example.colorsortingcontroller.data.entities.Parametros
import com.example.colorsortingcontroller.data.repository.ParametrosRepository
import com.example.colorsortingcontroller.network.MQTTHandler


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
import java.util.UUID

private val BROKER_URL = "ssl://4b5548dcb4844ac9bd65c4373c6b8537.s1.eu.hivemq.cloud:8883"
private val CLIENT_ID = UUID.randomUUID().toString() //Praticamente impossível gerar id repetido
//Melhor do que passar login do usuário, porque assim permite que uma mesma conta, logue ao mesmo tempo
//em dois dispositivos diferentes

private lateinit var mqttHandler: MQTTHandler

class ParametrosViewModel(private val parametrosRepository: ParametrosRepository) : ViewModel() {
    private val _state = MutableStateFlow(ScreenState.parametros)
    val state: StateFlow<ScreenState> = _state

    //variável para controlar insert e updates no banco de dados
    private var valorParametrosList : MutableStateFlow<Int>? = null

    init {
        getConexao()
        updateParametrosFromDatabase()
        subscribeToTopic("parametros", 1)
        subscribeToTopic("parametrosReceber", 1)
        subscribeToTopic("novosParametros", 1)
        enviarSolicitacao()
        manipularMensagemMQTT()
    }

    fun getConexao() {
        viewModelScope.launch{
            try {
                mqttHandler = MQTTHandler.getInstance()
                mqttHandler.connect(BROKER_URL, CLIENT_ID)

            } catch(e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    fun subscribeToTopic(topic: String, nivelQos: Int){
        viewModelScope.launch{
            try {
                mqttHandler.subscribe(topic, nivelQos)

            } catch(e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    fun publishMessage(topic: String, message: String, nivelQos: Int, retainedFlag: Boolean) {
        viewModelScope.launch{
            try {
             //  Colocar Toast indicando que mensagem foi enviada na view
                mqttHandler.publish(topic, message, nivelQos, retainedFlag)
            } catch(e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    private fun enviarSolicitacao(){
        viewModelScope.launch{
            try {
                publishMessage("novosParametros", "Envie parametros", 1, false )
            } catch(e: IOException) {
                e.printStackTrace()
            } catch(e: Throwable) {
                e.printStackTrace()
            }

        }
    }

    fun transformarObjetoJsoneEnviar(posicaoServoPortaMin: String,
                                     posicaoServoPortaMax: String,
                                     posicaoServoDirecionadorEDMin: String,
                                     posicaoServoDirecionadorEDMax: String,
                                     posicaoServoDirecionador12Min: String,
                                     posicaoServoDirecionador12Max: String,
                                     posicaoServoDirecionador34Min: String,
                                     posicaoServoDirecionador34Max: String,
                                     RCor1: String,
                                     GCor1: String,
                                     BCor1: String,
                                     coletorCor1: String,
                                     RCor2: String,
                                     GCor2: String,
                                     BCor2: String,
                                     coletorCor2: String,
                                     RCor3: String,
                                     GCor3: String,
                                     BCor3: String,
                                     coletorCor3: String,
                                     RCor4: String,
                                     GCor4: String,
                                     BCor4: String,
                                     coletorCor4: String,
                                     RCor5: String,
                                     GCor5: String,
                                     BCor5: String,
                                     coletorCor5: String,
                                     RCor6: String,
                                     GCor6: String,
                                     BCor6: String,
                                     coletorCor6: String,
                                     RCor7: String,
                                     GCor7: String,
                                     BCor7: String,
                                     coletorCor7: String) {
        viewModelScope.launch{
            try {
                var objetoJsonParametros = "{"  + "\"" +  "PosicaoServoPortaAnguloMinimo" + "\":" +
                        posicaoServoPortaMin  + "," +
                        "\"" +  "PosicaoServoPortaAnguloMaximo" + "\" :" +
                        posicaoServoPortaMax +  "," +
                        "\"" +  "PosicaoServoDirecionadorEDAnguloMinimo"+ "\":"  +
                        posicaoServoDirecionadorEDMin +  "," +
                        "\"" + "PosicaoServoDirecionadorEDAnguloMaximo"  + "\":" +
                         posicaoServoDirecionadorEDMax  + "," +
                        "\"" + "PosicaoServoDirecionador12AnguloMinimo"  +  "\":" +
                        posicaoServoDirecionador12Min  + "," +
                        "\"" + "PosicaoServoDirecionador12AnguloMaximo"  + "\":" +
                        posicaoServoDirecionador12Max  + "," +
                        "\"" + "PosicaoServoDirecionador34AnguloMinimo"  +  "\":" +
                         posicaoServoDirecionador34Min  + "," +
                        "\"" + "PosicaoServoDirecionador34AnguloMaximo"  + "\":" +
                          posicaoServoDirecionador34Max  + "," +
                        "\"" + "Cor1R"  + "\":" +
                         RCor1 + "," +
                        "\"" + "Cor1G"  + "\":" +
                          GCor1  + "," +
                        "\"" + "Cor1B" + "\":" +
                         BCor1  + "," +
                        "\"" + "Cor1Coletor"  + "\":" +
                        coletorCor1 + "," +
                        "\"" + "Cor2R"  + "\":" +
                          RCor2  + "," +
                        "\"" + "Cor2G"  + "\":" +
                          GCor2 + "," +
                        "\"" + "Cor2B" + "\":" +
                       BCor2 + "," +
                        "\"" + "Cor2Coletor"  + "\":" +
                          coletorCor2  + "," +
                        "\"" + "Cor3R"  + "\":" +
                         RCor3  + "," +
                        "\"" + "Cor3G"  + "\":" +
                          GCor3  + "," +
                        "\"" + "Cor3B" + "\":" +
                          BCor3  + "," +
                        "\"" + "Cor3Coletor"  + "\":" +
                         coletorCor3  + "," +
                        "\"" + "Cor4R"  + "\":" +
                          RCor4  + "," +
                        "\"" + "Cor4G"  + "\":" +
                        GCor4  + "," +
                        "\"" + "Cor4B" + "\":" +
                       BCor4  + "," +
                        "\"" + "Cor4Coletor"  + "\":" +
                        coletorCor4 + "," +
                        "\"" + "Cor5R"  + "\":" +
                        RCor5  + "," +
                        "\"" + "Cor5G"  + "\":" +
                         GCor5 + "," +
                        "\"" + "Cor5B" + "\":" +
                        BCor5 + "," +
                        "\"" + "Cor5Coletor"  + "\":" +
                        coletorCor5 + "," +
                        "\"" + "Cor6R"  + "\":" +
                        RCor6 + "," +
                        "\"" + "Cor6G"  + "\":" +
                        GCor6 + "," +
                        "\"" + "Cor6B" + "\":" +
                        BCor6 + "," +
                        "\"" + "Cor6Coletor"  + "\":" +
                        coletorCor6 + "," +
                        "\"" + "Cor7R"  + "\":" +
                        RCor7 + "," +
                        "\"" + "Cor7G"  + "\":" +
                        GCor7 + "," +
                        "\"" + "Cor7B" + "\":" +
                        BCor7 + "," +
                        "\"" + "Cor7Coletor"  + "\":" +
                        coletorCor7  + "}"
                publishMessage("parametros",  objetoJsonParametros, 1, false)
            } catch(e: Throwable) {
                e.printStackTrace()
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
             try {
                 //Conversão da mensagem em MQTT contendo uma string json para um objeto json
                 mensagemMQTT.observeForever { novaMensagem ->
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
                             objetoJson.get("Cor1R").asFloat,
                             objetoJson.get("Cor1G").asFloat,
                             objetoJson.get("Cor1B").asFloat,
                             objetoJson.get("Cor1Coletor").asInt,
                             objetoJson.get("Cor2R").asFloat,
                             objetoJson.get("Cor2G").asFloat,
                             objetoJson.get("Cor2B").asFloat,
                             objetoJson.get("Cor2Coletor").asInt,
                             objetoJson.get("Cor3R").asFloat,
                             objetoJson.get("Cor3G").asFloat,
                             objetoJson.get("Cor3B").asFloat,
                             objetoJson.get("Cor3Coletor").asInt,
                             objetoJson.get("Cor4R").asFloat,
                             objetoJson.get("Cor4G").asFloat,
                             objetoJson.get("Cor4B").asFloat,
                             objetoJson.get("Cor4Coletor").asInt,
                             objetoJson.get("Cor5R").asFloat,
                             objetoJson.get("Cor5G").asFloat,
                             objetoJson.get("Cor5B").asFloat,
                             objetoJson.get("Cor5Coletor").asInt,
                             objetoJson.get("Cor6R").asFloat,
                             objetoJson.get("Cor6G").asFloat,
                             objetoJson.get("Cor6B").asFloat,
                             objetoJson.get("Cor6Coletor").asInt,
                             objetoJson.get("Cor7R").asFloat,
                             objetoJson.get("Cor7G").asFloat,
                             objetoJson.get("Cor7B").asFloat,
                             objetoJson.get("Cor7Coletor").asInt
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
                                 _stateParametros.value.RCor1,
                                 _stateParametros.value.GCor1,
                                 _stateParametros.value.BCor1,
                                 _stateParametros.value.coletorCor1,
                                 _stateParametros.value.RCor2,
                                 _stateParametros.value.GCor2,
                                 _stateParametros.value.BCor2,
                                 _stateParametros.value.coletorCor2,
                                 _stateParametros.value.RCor3,
                                 _stateParametros.value.GCor3,
                                 _stateParametros.value.BCor3,
                                 _stateParametros.value.coletorCor3,
                                 _stateParametros.value.RCor4,
                                 _stateParametros.value.GCor4,
                                 _stateParametros.value.BCor4,
                                 _stateParametros.value.coletorCor4,
                                 _stateParametros.value.RCor5,
                                 _stateParametros.value.GCor5,
                                 _stateParametros.value.BCor5,
                                 _stateParametros.value.coletorCor5,
                                 _stateParametros.value.RCor6,
                                 _stateParametros.value.GCor6,
                                 _stateParametros.value.BCor6,
                                 _stateParametros.value.coletorCor6,
                                 _stateParametros.value.RCor7,
                                 _stateParametros.value.GCor7,
                                 _stateParametros.value.BCor7,
                                 _stateParametros.value.coletorCor7
                             )
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
                                 _stateParametros.value.RCor1,
                                 _stateParametros.value.GCor1,
                                 _stateParametros.value.BCor1,
                                 _stateParametros.value.coletorCor1,
                                 _stateParametros.value.RCor2,
                                 _stateParametros.value.GCor2,
                                 _stateParametros.value.BCor2,
                                 _stateParametros.value.coletorCor2,
                                 _stateParametros.value.RCor3,
                                 _stateParametros.value.GCor3,
                                 _stateParametros.value.BCor3,
                                 _stateParametros.value.coletorCor3,
                                 _stateParametros.value.RCor4,
                                 _stateParametros.value.GCor4,
                                 _stateParametros.value.BCor4,
                                 _stateParametros.value.coletorCor4,
                                 _stateParametros.value.RCor5,
                                 _stateParametros.value.GCor5,
                                 _stateParametros.value.BCor5,
                                 _stateParametros.value.coletorCor5,
                                 _stateParametros.value.RCor6,
                                 _stateParametros.value.GCor6,
                                 _stateParametros.value.BCor6,
                                 _stateParametros.value.coletorCor6,
                                 _stateParametros.value.RCor7,
                                 _stateParametros.value.GCor7,
                                 _stateParametros.value.BCor7,
                                 _stateParametros.value.coletorCor7
                             )
                         }
                     }
                 }
             } catch (e: Throwable){
                 e.printStackTrace()
             }
         }
    }

    // Envia a lista de parametros para a UI
    val parametros = parametrosRepository.allParametros.map { list ->
        list.firstOrNull()
    }

    //Lógica para atualizar o banco de dados
    fun atualizarBancoDeDados(
        posicaoServoPortaMin: String,
        posicaoServoPortaMax: String,
        posicaoServoDirecionadorEDMin: String,
        posicaoServoDirecionadorEDMax: String,
        posicaoServoDirecionador12Min: String,
        posicaoServoDirecionador12Max: String,
        posicaoServoDirecionador34Min: String,
        posicaoServoDirecionador34Max: String,
        RCor1: String,
        GCor1: String,
        BCor1: String,
        coletorCor1: String,
        RCor2: String,
        GCor2: String,
        BCor2: String,
        coletorCor2: String,
        RCor3: String,
        GCor3: String,
        BCor3: String,
        coletorCor3: String,
        RCor4: String,
        GCor4: String,
        BCor4: String,
        coletorCor4: String,
        RCor5: String,
        GCor5: String,
        BCor5: String,
        coletorCor5: String,
        RCor6: String,
        GCor6: String,
        BCor6: String,
        coletorCor6: String,
        RCor7: String,
        GCor7: String,
        BCor7: String,
        coletorCor7: String
    ){
        viewModelScope.launch {
        if (valorParametrosList == null) {
                insert(
                    posicaoServoPortaMin.toInt(),
                    posicaoServoPortaMax.toInt(),
                    posicaoServoDirecionadorEDMin.toInt(),
                    posicaoServoDirecionadorEDMax.toInt(),
                    posicaoServoDirecionador12Min.toInt(),
                    posicaoServoDirecionador12Max.toInt(),
                    posicaoServoDirecionador34Min.toInt(),
                    posicaoServoDirecionador34Max.toInt(),
                    RCor1.toFloat(), GCor1.toFloat(), BCor1.toFloat(), coletorCor1.toInt(),
                    RCor2.toFloat(), GCor2.toFloat(),
                    BCor2.toFloat(), coletorCor2.toInt(), RCor3.toFloat(), GCor3.toFloat(), BCor3.toFloat(),
                    coletorCor3.toInt(),
                    RCor4.toFloat(), GCor4.toFloat(), BCor4.toFloat(), coletorCor4.toInt(),
                    RCor5.toFloat(), GCor5.toFloat(), BCor5.toFloat(), coletorCor5.toInt(),
                    RCor6.toFloat(), GCor6.toFloat(), BCor6.toFloat(),
                    coletorCor6.toInt(), RCor7.toFloat(), GCor7.toFloat(), BCor7.toFloat(),
                    coletorCor7.toInt()
                )
            } else {
                update(
                    posicaoServoPortaMin.toInt(),
                    posicaoServoPortaMax.toInt(),
                    posicaoServoDirecionadorEDMin.toInt(),
                    posicaoServoDirecionadorEDMax.toInt(),
                    posicaoServoDirecionador12Min.toInt(),
                    posicaoServoDirecionador12Max.toInt(),
                    posicaoServoDirecionador34Min.toInt(),
                    posicaoServoDirecionador34Max.toInt(),
                    RCor1.toFloat(), GCor1.toFloat(), BCor1.toFloat(), coletorCor1.toInt(),
                    RCor2.toFloat(), GCor2.toFloat(),
                    BCor2.toFloat(), coletorCor2.toInt(), RCor3.toFloat(), GCor3.toFloat(), BCor3.toFloat(),
                    coletorCor3.toInt(),
                    RCor4.toFloat(), GCor4.toFloat(), BCor4.toFloat(), coletorCor4.toInt(),
                    RCor5.toFloat(), GCor5.toFloat(), BCor5.toFloat(), coletorCor5.toInt(),
                    RCor6.toFloat(), GCor6.toFloat(), BCor6.toFloat(),
                    coletorCor6.toInt(), RCor7.toFloat(), GCor7.toFloat(), BCor7.toFloat(),
                    coletorCor7.toInt()
                )
            }
        }
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
        RCor1: Float,
        GCor1: Float,
        BCor1: Float,
        coletorCor1: Int,
        RCor2: Float ,
        GCor2: Float,
        BCor2: Float,
        coletorCor2: Int,
        RCor3: Float,
        GCor3: Float,
        BCor3: Float,
        coletorCor3: Int,
        RCor4: Float,
        GCor4: Float,
        BCor4: Float,
        coletorCor4: Int,
        RCor5: Float,
        GCor5: Float,
        BCor5: Float ,
        coletorCor5: Int,
        RCor6: Float,
        GCor6: Float,
        BCor6: Float,
        coletorCor6: Int,
        RCor7: Float,
        GCor7: Float,
        BCor7: Float,
        coletorCor7: Int
    ) = viewModelScope.launch {
        withContext(Dispatchers.IO)
        {
            parametrosRepository.updateColetoresCor(
                coletorCor1 = coletorCor1,
                coletorCor2 = coletorCor2,
                coletorCor3 = coletorCor3,
                coletorCor4 = coletorCor4,
                coletorCor5 = coletorCor5,
                coletorCor6 = coletorCor6,
                coletorCor7 = coletorCor7,
            )

            parametrosRepository.updateRGBValues(
                r1Value = RCor1,
                g1Value = GCor1,
                b1Value = BCor1,
                r2Value = RCor2,
                g2Value = GCor2,
                b2Value = BCor2,
                r3Value = RCor3,
                g3Value = GCor3,
                b3Value = BCor3,
                r4Value = RCor4,
                g4Value = GCor4,
                b4Value = BCor4,
                r5Value = RCor5,
                g5Value = GCor5,
                b5Value = BCor5,
                r6Value = RCor6,
                g6Value = GCor6,
                b6Value = BCor6,
                r7Value = RCor7,
                g7Value = GCor7,
                b7Value = BCor7
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
        withContext(Dispatchers.IO) {
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
        RCor1: Float,
        GCor1: Float,
        BCor1: Float,
        coletorCor1: Int,
        RCor2: Float ,
        GCor2: Float,
        BCor2: Float,
        coletorCor2: Int,
        RCor3: Float,
        GCor3: Float,
        BCor3: Float,
        coletorCor3: Int,
        RCor4: Float,
        GCor4: Float,
        BCor4: Float,
        coletorCor4: Int,
        RCor5: Float,
        GCor5: Float,
        BCor5: Float ,
        coletorCor5: Int,
        RCor6: Float,
        GCor6: Float,
        BCor6: Float,
        coletorCor6: Int,
        RCor7: Float,
        GCor7: Float,
        BCor7: Float,
        coletorCor7: Int
    ) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            parametrosRepository.insertParametros(
                posicaoServoPortaMin, posicaoServoPortaMax,
                posicaoServoDirecionadorEDMin, posicaoServoDirecionadorEDMax,
                posicaoServoDirecionador12Min, posicaoServoDirecionador12Max,
                posicaoServoDirecionador34Min, posicaoServoDirecionador34Max,
                RCor1, GCor1, BCor1, coletorCor1, RCor2, GCor2,
                BCor2, coletorCor2, RCor3, GCor3, BCor3, coletorCor3,
                RCor4, GCor4, BCor4, coletorCor4, RCor5, GCor5, BCor5, coletorCor5,
                RCor6, GCor6, BCor6,
                coletorCor6, RCor7, GCor7, BCor7,
                coletorCor7
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

        var RCor1: Float = 0.toFloat(),
        var GCor1: Float = 0.toFloat(),
        var BCor1: Float = 0.toFloat(),
        var coletorCor1: Int = 0,
        var RCor2: Float = 0.toFloat(),
        var GCor2: Float = 0.toFloat(),
        var BCor2: Float = 0.toFloat(),
        var coletorCor2: Int = 0,
        var RCor3: Float = 0.toFloat(),
        var GCor3: Float = 0.toFloat(),
        var BCor3: Float = 0.toFloat(),
        var coletorCor3: Int = 0,
        var RCor4: Float = 0.toFloat(),
        var GCor4: Float = 0.toFloat(),
        var BCor4: Float = 0.toFloat(),
        var coletorCor4: Int = 0,
        var RCor5: Float = 0.toFloat(),
        var GCor5: Float = 0.toFloat(),
        var BCor5: Float = 0.toFloat(),
        var coletorCor5: Int = 0,
        var RCor6: Float = 0.toFloat(),
        var GCor6: Float = 0.toFloat(),
        var BCor6: Float = 0.toFloat(),
        var coletorCor6: Int = 0,
        var RCor7: Float = 0.toFloat(),
        var GCor7: Float = 0.toFloat(),
        var BCor7: Float = 0.toFloat(),
        var coletorCor7: Int = 0
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
                        RCor1 = parametrosList[0].RCor1,
                        GCor1 = parametrosList[0].GCor1,
                        BCor1 = parametrosList[0].BCor1,
                        coletorCor1 = parametrosList[0].coletorCor1,
                        RCor2 = parametrosList[0].RCor2,
                        GCor2 = parametrosList[0].GCor2,
                        BCor2 = parametrosList[0].BCor2,
                        coletorCor2 = parametrosList[0].coletorCor2,
                        RCor3 = parametrosList[0].RCor3,
                        GCor3 = parametrosList[0].GCor3,
                        BCor3 = parametrosList[0].BCor3,
                        coletorCor3 = parametrosList[0].coletorCor3,
                        RCor4 = parametrosList[0].RCor4,
                        GCor4 = parametrosList[0].GCor4,
                        BCor4 = parametrosList[0].BCor4,
                        coletorCor4 = parametrosList[0].coletorCor4,
                        RCor5 = parametrosList[0].RCor5,
                        GCor5 = parametrosList[0].GCor5,
                        BCor5 = parametrosList[0].BCor5,
                        coletorCor5 = parametrosList[0].coletorCor5,
                        RCor6 = parametrosList[0].RCor6,
                        GCor6 = parametrosList[0].GCor6,
                        BCor6 = parametrosList[0].BCor6,
                        coletorCor6 = parametrosList[0].coletorCor6,
                        RCor7 = parametrosList[0].RCor7,
                        GCor7 = parametrosList[0].GCor7,
                        BCor7 = parametrosList[0].BCor7,
                        coletorCor7 = parametrosList[0].coletorCor7,
                    )
                    _stateParametros.value = novosParametros
                    Log.d("ParametrosUpdate", "Novos parâmetros: $novosParametros")
                    valorParametrosList = MutableStateFlow(1)
                    delay(300)
                }
            }
        }
    }


     fun validateInput(
         posicaoServoPortaMin: String,
         posicaoServoPortaMax: String,
         posicaoServoDirecionadorEDMin: String,
         posicaoServoDirecionadorEDMax: String,
         posicaoServoDirecionador12Min: String,
         posicaoServoDirecionador12Max: String,
         posicaoServoDirecionador34Min: String,
         posicaoServoDirecionador34Max: String,
         RCor1: String,
         GCor1: String,
         BCor1: String,
         coletorCor1: String,
         RCor2: String,
         GCor2: String,
         BCor2: String,
         coletorCor2: String,
         RCor3: String,
         GCor3: String,
         BCor3: String,
         coletorCor3: String,
         RCor4: String,
         GCor4: String,
         BCor4: String,
         coletorCor4: String,
         RCor5: String,
         GCor5: String,
         BCor5: String ,
         coletorCor5: String,
         RCor6: String,
         GCor6: String,
         BCor6: String,
         coletorCor6: String,
         RCor7: String,
         GCor7: String,
         BCor7: String,
         coletorCor7: String): Boolean {
         return (
                 // Campos não podem ser nulos
                 posicaoServoPortaMin.isNotBlank()
                 && posicaoServoPortaMax.isNotBlank()
                 && posicaoServoDirecionadorEDMin.isNotBlank()
                 && posicaoServoDirecionadorEDMax.isNotBlank()
                 && posicaoServoDirecionador12Min.isNotBlank()
                 && posicaoServoDirecionador12Max.isNotBlank()
                 && RCor1.isNotBlank() && GCor1.isNotBlank() && BCor1.isNotBlank() && coletorCor1.isNotBlank()
                 && RCor2.isNotBlank() && GCor2.isNotBlank() && BCor2.isNotBlank() && coletorCor2.isNotBlank()
                 && RCor3.isNotBlank() && GCor3.isNotBlank() && BCor3.isNotBlank() && coletorCor3.isNotBlank()
                 && RCor4.isNotBlank() && GCor4.isNotBlank() && BCor4.isNotBlank() && coletorCor4.isNotBlank()
                 && RCor5.isNotBlank() && GCor5.isNotBlank() && BCor5.isNotBlank() && coletorCor5.isNotBlank()
                 && RCor6.isNotBlank() && GCor6.isNotBlank() && BCor6.isNotBlank() && coletorCor6.isNotBlank()
                 && RCor7.isNotBlank() && GCor7.isNotBlank() && BCor7.isNotBlank() && coletorCor7.isNotBlank()

                 // Intervalo RGB entre 0 e 1
                 && RCor1.toFloat() <= 1 && GCor1.toFloat() <= 1 && BCor1.toFloat() <= 1 && coletorCor1.toInt() <= 4 && coletorCor1.toInt() != 0
                 && RCor2.toFloat() <= 1 && GCor2.toFloat() <= 1 && BCor2.toFloat() <= 1 && coletorCor2.toInt() <= 4 && coletorCor2.toInt() != 0
                 && RCor3.toFloat() <= 1 && GCor3.toFloat() <= 1 && BCor3.toFloat() <= 1 && coletorCor3.toInt() <= 4 && coletorCor3.toInt() != 0
                 && RCor4.toFloat() <= 1 && GCor4.toFloat() <= 1 && BCor4.toFloat() <= 1 && coletorCor4.toInt() <= 4 && coletorCor4.toInt() != 0
                 && RCor5.toFloat() <= 1 && GCor5.toFloat() <= 1 && BCor5.toFloat() <= 1 && coletorCor5.toInt() <= 4 && coletorCor5.toInt() != 0
                 && RCor6.toFloat() <= 1 && GCor6.toFloat() <= 1 && BCor6.toFloat() <= 1 && coletorCor6.toInt() <= 4 && coletorCor6.toInt() != 0
                 && RCor7.toFloat() <= 1 && GCor7.toFloat() <= 1 && BCor7.toFloat() <= 1 && coletorCor7.toInt() <= 4 && coletorCor7.toInt() != 0

                 // Intervalo do ângulo das portas
                 && posicaoServoPortaMin.toInt() >= 0 && posicaoServoPortaMin.toInt() <= 90
                 && posicaoServoDirecionadorEDMin.toInt() >= 70 && posicaoServoDirecionadorEDMin.toInt() <= 110
                 && posicaoServoDirecionador12Min.toInt() >= 70 && posicaoServoDirecionador12Min.toInt() <= 110
                 && posicaoServoDirecionador34Min.toInt() >= 80 && posicaoServoDirecionador34Min.toInt() <= 120
                 && posicaoServoPortaMax.toInt() >= 0 && posicaoServoPortaMax.toInt() <= 90
                 && posicaoServoDirecionadorEDMax.toInt() >= 70 && posicaoServoDirecionadorEDMax.toInt() <= 110
                 && posicaoServoDirecionador12Max.toInt() >= 70 && posicaoServoDirecionador12Max.toInt() <= 110
                 && posicaoServoDirecionador34Max.toInt() >= 80 && posicaoServoDirecionador34Max.toInt() <= 120

                 // Angulo máximo deve ser sempre maior que o ângulo mínimo
                 && posicaoServoPortaMin.toInt() < posicaoServoPortaMax.toInt()
                 && posicaoServoDirecionadorEDMin.toInt() < posicaoServoDirecionadorEDMax.toInt()
                 && posicaoServoDirecionador12Min.toInt() < posicaoServoDirecionador12Max.toInt()
                 && posicaoServoDirecionador34Min.toInt() < posicaoServoDirecionador34Max.toInt()
            )
        }

    override fun onCleared(){
        super.onCleared()
        //Encerrar a conexão
        mqttHandler.disconnect()
    }
}