package br.com.local.testemqtt

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.local.testemqtt.ui.theme.TesteMQTTTheme
import kotlinx.coroutines.delay

private val BROKER_URL = "ssl://44a41899400a4d2687717200b79f04cb.s1.eu.hivemq.cloud:8883"
private val CLIENT_ID = "Android_Client"
private lateinit var mqttHandler: MQTTHandler


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        mqttHandler = MQTTHandler()


        mqttHandler.connect(BROKER_URL , CLIENT_ID )
        //Precisa se inscrever em cada tópico que será manipulado
        subscribeToTopic("teste/mensage",1)
        subscribeToTopic("Vamos", 1)
        subscribeToTopic("Enviado", 1)
        publishMessage("Vamos", "teste", 1, false)
        setContent {
            TesteMQTTTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    while(true) {
                        publishMessage("Vamos", "teste", 1, false) }

                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        mqttHandler.disconnect()
        super.onDestroy()


    }

//RetainedFlag, definir se só clientes que estavam no broker quando a mensagem estaava publicada recebe a mensagem
    private fun publishMessage(topic: String, message: String, nivelQos: Int, retainedFlag: Boolean) {
       Toast.makeText(this, "Publishing message: " + message, Toast.LENGTH_SHORT).show()
        mqttHandler.publish(topic, message, nivelQos, retainedFlag)
       
    }

    private fun subscribeToTopic(topic: String, nivelQos: Int){
        Toast.makeText(this, "Subscribing to topic: " + topic, Toast.LENGTH_SHORT).show()
        mqttHandler.subscribe(topic, nivelQos);
    }


}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TesteMQTTTheme {
        Greeting("Android")
    }
}