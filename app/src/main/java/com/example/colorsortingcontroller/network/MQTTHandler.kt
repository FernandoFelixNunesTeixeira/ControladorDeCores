package com.example.colorsortingcontroller.network

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance


import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence




sealed interface MQTTUiState {
    object Success: MQTTUiState
    object Error: MQTTUiState
    object Loading: MQTTUiState
}

//interface Instancia {
  //  fun getInstance(): MQTTHandler
//}


class MQTTHandler: ViewModel() {
    private var instanciaMQTT : MQTTHandler? = null

    private var conexao : Boolean = false

    companion object {

        @Volatile
        private var instancia: MQTTHandler? = null
        fun getInstance(): MQTTHandler {
            return instancia ?: synchronized(this) {

                instancia ?: MQTTHandler().also{ instancia = it}
                //.connect(serverUri, clientId).also { instance = it }

            }
            ///instancia?.connect(brokerUrl, clientId)
        }
    }


    private lateinit var client: MqttClient

    ///var mqttUiState: MQTTUiState by mutableStateOf(MQTTUiState.Loading)
     //   private set

    //Pretendo adaptar melhor esse nome, para deixar mais especifico depois
    private val _MQTTstateParametros = MutableLiveData<String>()
    val mqttStateParametros: LiveData<String> get() = _MQTTstateParametros

    private val _MQTTstateEstatisticas = MutableLiveData<String>()
    val mqttStateEstatisticas: LiveData<String> get() = _MQTTstateEstatisticas

    private val _MQTTstateMonitoramento = MutableLiveData<String>()
    val mqttStateMonitoramento: LiveData<String> get() = _MQTTstateMonitoramento

    //Verificar se faz sentido colocar tipos anuláveis

    fun connect(brokerUrl: String, clientId: String
    ) {


        try {
                synchronized(this) {
                    if (conexao == false) {
                        // Configurar a camada de persistência
                        val persistence = MemoryPersistence()
                        // Inicializar o Cliente MQTT
                        client = MqttClient(brokerUrl, clientId, persistence)
                        conexao = true


                        val connectOptions = MqttConnectOptions().apply {
                            isCleanSession = true
                            userName = "Grupo"
                            password = "SenhaSenha1".toCharArray()

                        }


                        // Conectar com o broker
                        try {
                            client.connect(connectOptions)
                            // MQTTUiState.Success
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                }
            //
                client.setCallback(object : MqttCallback {
                    override fun connectionLost(cause: Throwable?) {
                        println("Conexão Perdida: ${cause?.message}")
                        MQTTUiState.Error
                    }


                    override fun messageArrived(topic: String?, message: MqttMessage?) {
                        //Comparar mensagem com o tópico esperado
                        synchronized(this) {
                            if (message != null) {
                                if (topic.equals("parametrosReceber")) {
                                    // val novoValorConexao = ConexaoParametrosUiState(message.toString())
                                    // _state3.value = novoValorConexao
                                    //     mqttUiState = MQTTUiState.MensagemRecebida(message.toString())
                                    _MQTTstateParametros.postValue(String(message.payload))
                                    Thread.sleep(2000)
                                }

                                if (topic.equals("estatisticasReceber")) {
                                    // val novoValorConexao = ConexaoParametrosUiState(message.toString())
                                    // _state3.value = novoValorConexao
                                    //     mqttUiState = MQTTUiState.MensagemRecebida(message.toString())
                                    _MQTTstateEstatisticas.postValue(String(message.payload))
                                    Thread.sleep(2000)
                                }

                                if (topic.equals("monitoramentoReceber")) {
                                    // val novoValorConexao = ConexaoParametrosUiState(message.toString())
                                    // _state3.value = novoValorConexao
                                    //     mqttUiState = MQTTUiState.MensagemRecebida(message.toString())
                                    _MQTTstateMonitoramento.postValue(String(message.payload))
                                    Thread.sleep(2000)
                                }
                            }
                        }
                        println("mensagem recebida no tópico $topic : ${message.toString()} ")
                    }

                    //   override   fun getmessageArrived(): String {

                    // }

                    override fun deliveryComplete(token: IMqttDeliveryToken?) {
                        //Toast.makeText( "mensagem entregue com sucesso", Toast.LENGTH_SHORT).show()
                        println("completo")

                    }

                })


                // Configure as opções de conexão




        } catch (e: MqttException) {
            e.printStackTrace()
        }

    }

    //Desconectar do broker

    fun disconnect() {
        try {
            client.disconnect()
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    //publicar mensagem

    fun publish(topic: String, message: String, nivelQos: Int, retainedFlag: Boolean) {
        try {
            val mqttMessage = MqttMessage(message.toByteArray()).toString().toByteArray()


            client.publish(topic, mqttMessage, nivelQos, retainedFlag)

        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    //assinar tópico
    fun subscribe(topic: String, nivelQos: Int) {
        try {
            client.subscribe(topic, nivelQos)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    //companion object {
    //    private const val TIMEOUT_MILLIS = 5_000L
   // }





    //   fun ReceberAtualizar(){
 //       viewModelScope.launch()
 //       {
 //           _state3.value = MQTTHandler.ConexaoParametrosUiState()
 //       }
 //   }


}




