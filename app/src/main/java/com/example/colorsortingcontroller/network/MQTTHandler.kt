package com.example.colorsortingcontroller.network

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import com.example.colorsortingcontroller.MainActivity
import kotlinx.coroutines.delay


import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import kotlin.concurrent.thread


sealed interface MQTTUiState {
    object Success: MQTTUiState
    object Error: MQTTUiState
    object Loading: MQTTUiState
}

//interface Instancia {
  //  fun getInstance(): MQTTHandler
//}

//Verificar posteriormente se será model ou viewModel
class MQTTHandler {
   // private var instanciaMQTT : MQTTHandler? = null

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



    //Mostrar o estado geral da conexão
    private val _MQTTstate = MutableLiveData<String>()
    val mqttState: LiveData<String> get() = _MQTTstate

    private val _MQTTstateEntregaMensagem = MutableLiveData<String>()
    val mqttStateEntregaMensagem: LiveData<String> get() = _MQTTstateEntregaMensagem

    private val _MQTTstateParametros = MutableLiveData<String?>()
    val mqttStateParametros: LiveData<String?> get() = _MQTTstateParametros

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
                            isAutomaticReconnect = true
                            //Falso para manter as informações da conexão anterior ao reconectar automaticamente
                            isCleanSession = false
                            userName = "Grupo"
                            password = "SenhaSenha1".toCharArray()
                        }

                    //
                    client.setCallback(object : MqttCallbackExtended {
                        override fun connectionLost(cause: Throwable?) {
                            println("Conexão Perdida: ${cause?.message}")
                            _MQTTstate.postValue("Desconectado")
                            //  Toast.makeText(context, "Erro de Autenticação: ", Toast.LENGTH_SHORT).show()
                            // MQTTUiState.Error
                            //Implementação da reconexão, para o caso de queda da internet
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
                                        Thread.sleep(100)
                                        //Tornar valor nulo porque existe entrada do usuário nessa tela, evitar conflito de forma mais simples
                                        _MQTTstateParametros.postValue(null)

                                    }

                                    if (topic.equals("estatisticasReceber")) {
                                        // val novoValorConexao = ConexaoParametrosUiState(message.toString())
                                        // _state3.value = novoValorConexao
                                        //     mqttUiState = MQTTUiState.MensagemRecebida(message.toString())
                                        _MQTTstateEstatisticas.postValue(String(message.payload))
                                        Thread.sleep(100)
                                    }

                                    if (topic.equals("monitoramentoReceber")) {
                                        // val novoValorConexao = ConexaoParametrosUiState(message.toString())
                                        // _state3.value = novoValorConexao
                                        //     mqttUiState = MQTTUiState.MensagemRecebida(message.toString())
                                        _MQTTstateMonitoramento.postValue(String(message.payload))
                                        Thread.sleep(100)
                                    }
                                }
                            }
                            println("mensagem recebida no tópico $topic : ${message.toString()} ")
                        }


                        override fun deliveryComplete(token: IMqttDeliveryToken?) {

                            //   Toast.makeText( applicationContext,"mensagem entregue com sucesso", Toast.LENGTH_SHORT).show()
                            println("Mensagem entregue com sucesso!")
                            _MQTTstateEntregaMensagem.postValue("Mensagem entregue com sucesso!")

                        }

                        //Gerenciar conexões completadas
                        override fun connectComplete(reconnect: Boolean, serverURI: String?) {


                            if (reconnect) {
                                println("Reconectado ao broker: $serverURI")
                                _MQTTstate.postValue("Conectado")
                            } else {
                                println("Conexão inicial ao broker: $serverURI")
                                _MQTTstate.postValue("Conectado")
                            }
//
                        }

                    })



                        // Conectar com o broker
                        try {
                            client.connect(connectOptions)
                            //MQTTUiState.Success
                        //    _MQTTstate.postValue("Conectado")
                        } catch (e: Exception) {
                            //Só pode iniciar tela pós login com internet ligada, seria bom colocar um icone indicando
                            Thread.sleep(1)
                            conexao = false
                            connect(brokerUrl, clientId)
                            e.printStackTrace()
                        }
                    }

                }




                // Configure as opções de conexão




        } catch (e: MqttException) {
            e.printStackTrace()
        }

    }

    //Desconectar do broker
    //Não há necessidade de uso, porque a ideia é reconexão automática, até que o aplicativo seja fechado,
    //o que já encerrará a conexão
    fun disconnect() {
        synchronized(this) {
            try {
                client.disconnect()
            } catch (e: MqttException) {
                e.printStackTrace()
            }
        }
    }

    //publicar mensagem

    fun publish(topic: String, message: String, nivelQos: Int, retainedFlag: Boolean) {
        try {
            val mqttMessage = MqttMessage(message.toByteArray()).toString().toByteArray()


            client.publish(topic, mqttMessage, nivelQos, retainedFlag)

        } catch (e: MqttException) {
            e.printStackTrace()
            _MQTTstateEntregaMensagem.postValue("Mensagem não pode ser entregue!")
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

   // override fun onCleared(){
   //     super.onCleared()
   //     //Encerrar a conexão
   //     disconnect()
   // }





    //   fun ReceberAtualizar(){
 //       viewModelScope.launch()
 //       {
 //           _state3.value = MQTTHandler.ConexaoParametrosUiState()
 //       }
 //   }


}




