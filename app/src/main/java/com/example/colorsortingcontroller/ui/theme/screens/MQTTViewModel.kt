package com.example.colorsortingcontroller.ui.theme.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence


sealed interface MQTTUiState {
    object Sucess: MQTTUiState
    object Error: MQTTUiState
    object Loading: MQTTUiState
}


class MQTTViewModel: ViewModel() {
    private lateinit var client: MqttClient

    var mqttUiState: MQTTUiState by mutableStateOf(MQTTUiState.Loading)
        private set

    //Verificar se faz sentido colocar tipos anuláveis

    fun connect(brokerUrl: String, clientId: String) {
        try {


            // Configurar a camada de persistência
            val persistence = MemoryPersistence()


            // Inicializar o Cliente MQTT
            client = MqttClient(brokerUrl, clientId, persistence)

            //
            client.setCallback(object: MqttCallback{
                override fun connectionLost(cause: Throwable?) {
                    println("Conexão Perdida: ${cause?.message}")
                    MQTTUiState.Error
                }

                override fun messageArrived(topic: String?, message: MqttMessage?) {
                    //Comparar mensagem com o tópico esperado
                    //   if(topic.equals("Vamos"))
                    println("mensagem recebida no tópico $topic : ${message.toString()} ")


                }

                override fun deliveryComplete(token: IMqttDeliveryToken?) {
                    //Toast.makeText( "mensagem entregue com sucesso", Toast.LENGTH_SHORT).show()
                    println("completo")

                }

            })
            // Configure as opções de conexão
            val connectOptions = MqttConnectOptions().apply {
                isCleanSession = true
                userName = "Grupo"
                password = "SenhaSenha1".toCharArray()

            }


            // Conectar com o broker
            try {
                client.connect(connectOptions)
                    MQTTUiState.Sucess
            } catch (e: Exception) {
                e.printStackTrace()

            }

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

}