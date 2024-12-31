package com.example.colorsortingcontroller.screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.colorsortingcontroller.data.Monitoramento
import com.example.colorsortingcontroller.data.Parametros
import com.example.colorsortingcontroller.data.ParametrosRepository


import com.example.colorsortingcontroller.network.MQTTHandler
import com.example.colorsortingcontroller.network.MQTTUiState
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


private val BROKER_URL = "ssl://77e0591acd6d4fb0b4cb6da7dc26b87b.s1.eu.hivemq.cloud:8883"
private val CLIENT_ID = "Android_ClientTestando"

private lateinit var mqttHandler: MQTTHandler
//private var ValorNovo by Delegates.notNull<Int>()

class ParametrosViewModel(private val parametrosRepository: ParametrosRepository) : ViewModel() {
    private val _state = MutableStateFlow(ScreenState.parametros)
    val state: StateFlow<ScreenState> = _state
    
    //val para impedir mudanças indesejáveis na variável
    private val valorParametrosList : ((List<Parametros>) -> Unit)? = null

    init {
        getConexao()

        subscribeToTopic("parametros", 1)
        subscribeToTopic("parametrosReceber", 1)
   //     obterTopico()
        //obterJson()
        updateParametrosFromDatabase()
        manipularMensagemMQTT()
        //getValor()
            //ReceberAtualizar()

    }


    //private val _mqqtState = MutableStateFlow(MQTTHandler)
    //val state3: StateFlow<MQTTHandler.Companion> = _mqqtState
    //var a : String? = getObjetoJsonParametros()

   // fun obterTopico() {
//
   // }

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
            } catch(e: IOException) {
                MQTTUiState.Error
            }
        }
    }


    fun transformarObjetoJsoneEnviar( ) {


        viewModelScope.launch{
            try {
                var objetoJsonParametros = "{ "  + "\"" +  "PosicaoServoPortaAnguloMinimo" + "\" :" +
                        "\"" + state2.value.posicaoServoPortaMin.toString() + "\"" + "," +
                        "\"" +  "PosicaoServoPortaAnguloMaximo" + "\" :" +
                        "\"" + state2.value.posicaoServoPortaMax.toString() + "\"" +  "," +
                        "\"" +  "PosicaoServoDirecionadorEDAnguloMinimo"+ "\" :"  +
                        "\"" + state2.value.posicaoServoDirecionadorEDMin.toString() + "\"" +  "," +
                        "\"" + "PosicaoServoDirecionadorEDAnguloMaximo"  + "\" :" +
                        "\"" + state2.value.posicaoServoDirecionadorEDMax.toString() + "\"" + "," +
                        "\"" + "PosicaoServoDirecionador12AnguloMinimo"  +  "\" :" +
                        "\"" + state2.value.posicaoServoDirecionador12Min.toString() + "\"" + "," +
                        "\"" + "PosicaoServoDirecionador12AnguloMaximo"  + "\" :" +
                        "\"" + state2.value.posicaoServoDirecionador12Max.toString() + "\"" + "," +
                        "\"" + "PosicaoServoDirecionador34AnguloMinimo"  +  "\" :" +
                        "\"" + state2.value.posicaoServoDirecionador34Min.toString() + "\"" + "," +
                        "\"" + "PosicaoServoDirecionador34AnguloMaximo"  + "\" :" +
                        "\"" +  state2.value.posicaoServoDirecionador34Max.toString() + "\"" + "," +
                        "\"" + "Cor"  + "\" :" +
                        "\"" +  state2.value.cor + "\"" + "," +
                        "\"" + "R"  + "\" :" +
                        "\"" +  state2.value.rValue.toString() + "\"" + "," +
                        "\"" + "G"  + "\" :" +
                        "\"" +  state2.value.gValue.toString() + "\"" + "," +
                        "\"" + "B"  + "\" :" +
                        "\"" +  state2.value.bValue.toString() + "\"" + " }"
                publishMessage("parametros",  objetoJsonParametros, 1, false )



            } catch(e: IOException) {
                MQTTUiState.Error
            }
        }
    }

    private val _state2 = MutableStateFlow(ParametrosUiState())
    val state2: StateFlow<ParametrosUiState> = _state2
  //  .stateIn(
  //      scope = viewModelScope,
  //      started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
   //     initialValue = ParametrosUiState()
  //  )
 //   val conexaoParametrosUiState = mqttHandler.state3.value

    val mensagemMQTT: LiveData<String> get() = mqttHandler.mqttStateParametros

//
 //   "PosicaoServoPortaAnguloMinimo" : "14",
 //   "PosicaoServoPortaAnguloMaximo" : "89",
 //   "PosicaoServoDirecionadorEDAnguloMinimo" : "13",
 //   "PosicaoServoDirecionadorEDAnguloMaximo" : "97",
//    "PosicaoServoDirecionador12AnguloMinimo" : "75",
//    "PosicaoServoDirecionador12AnguloMaximo" : "82",
//    "PosicaoServoDirecionador34AnguloMinimo" : "27",
 //   "PosicaoServoDirecionador34AnguloMaximo" : "14",
 //   "R" : "100",
//    "G" : "200",
//    "B" : "150",
 //   "Cor":"25"

     fun manipularMensagemMQTT(){
         viewModelScope.launch {
             while(true) {
                 //Instância do objeto GSON
                 delay(1)
                 //val gson = Gson()
                 //var json : FlowCollector<String>

                 //Conversão da mensagem em MQTT contendo uma string json para um objeto json
                 synchronized(this) {
                     if (mensagemMQTT.value != null) {
                         val objetoJson =
                             JsonParser.parseString(mensagemMQTT.value).asJsonObject
                         _state2.value = ParametrosUiState(
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
                         // updateParametrosFromDatabase()
                         if (valorParametrosList != null) {
                             update(
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
                         } else {
                             insert(
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

                         }
                         update(
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
                     }
                 }
                 //Mensagem para debug, deixar pelo menos enquanto não tiver o projeto praticamente pronto
                 println(" Mensagem atual: ${mensagemMQTT.value}")
             }
         }

    }
//   private val _state4 = MutableStateFlow(MQTTUiState.MensagemRecebida())
  //  val state4: MutableStateFlow<MQTTUiState.MensagemRecebida> = _state4


    //fun getValor() {
          //  return state4.value.objetoJson
        //val a = mqttUiState.

   // }
  //  _state4.value = conexaoParametrosUiState
//val mqttUiState2 : MQTTUiState = _state4.value
   //     when(mqttUiState2) {

     //   }

   // fun ReceberAtualizar(){

    //  viewModelScope.launch()
    //    {
        //  if(mqttHandler.state3.value.objetoJson != null)}{
       //   _state4.value = mqttHandler.state3.value
      //}
    //   }
  //  }


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
                    _state2.value = novosParametros
                    Log.d("ParametrosUpdate", "Novos parâmetros: $novosParametros")
                    valorParametrosList?.invoke(parametrosList)
                }

            }
        }
    }
}

