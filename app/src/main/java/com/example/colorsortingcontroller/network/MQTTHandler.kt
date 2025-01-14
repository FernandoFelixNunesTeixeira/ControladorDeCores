package com.example.colorsortingcontroller.network


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence



class MQTTHandler {


    private var conexao : Boolean = false




    companion object {

        @Volatile
        private var instancia: MQTTHandler? = null
        fun getInstance(): MQTTHandler {
            return instancia ?: synchronized(this) {
                instancia ?: MQTTHandler().also{ instancia = it}
            }
        }
    }


    private lateinit var client: MqttClient



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

                        // Configurar as opções de conexão
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
                                    }


                        override fun messageArrived(topic: String?, message: MqttMessage?) {
                            //Comparar mensagem com o tópico esperado


                                if (message != null && message.toString() != "null") {

                                    if (topic.equals("parametrosReceber")) {

                                        _MQTTstateParametros.postValue(String(message.payload))
                                        //Regular de forma simples, parar de receber mensagens, até vir uma nova
                                        publish("parametrosReceber", null.toString(), 1, false)


                                    }

                                    if (topic.equals("estatisticasReceber")) {
                                        _MQTTstateEstatisticas.postValue(String(message.payload))
                                    }

                                    if (topic.equals("monitoramentoReceber")) {

                                        _MQTTstateMonitoramento.postValue(String(message.payload))

                                    }
                                }
                            println("mensagem recebida no tópico $topic : ${message.toString()} ")
                        }


                        override fun deliveryComplete(token: IMqttDeliveryToken?) {
                            println("Comunicação realizada com sucesso!")
                                _MQTTstateEntregaMensagem.postValue("Comunicação realizada com sucesso!")

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

                        }

                    })



                        // Conectar com o broker
                        try {
                            client.connect(connectOptions)
                        } catch (e: Throwable) {
                            //Só pode iniciar tela pós login com internet ligada
                            Thread.sleep(1)
                            conexao = false
                            connect(brokerUrl, clientId)
                            e.printStackTrace()
                        }
                    }

                }
        } catch (e: MqttException) {
            e.printStackTrace()
        }
        catch (e: Throwable) {
            e.printStackTrace()
        }

    }


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
        } catch (e: Throwable) {
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
        catch (e: Throwable) {
            e.printStackTrace()
        }
    }
}




