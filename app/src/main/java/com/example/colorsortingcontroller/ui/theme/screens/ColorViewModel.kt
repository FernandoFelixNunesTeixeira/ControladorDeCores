package com.example.colorsortingcontroller.ui.theme.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.colorsortingcontroller.network.ColorApi
import kotlinx.coroutines.launch
import java.io.IOException

private val BROKER_URL = "ssl://44a41899400a4d2687717200b79f04cb.s1.eu.hivemq.cloud:8883"
private val CLIENT_ID = "Android_Client"
private lateinit var mqttHandler: MQTTViewModel


sealed interface ColorUiState {
    data class Success(val photos: String): ColorUiState
    object Loading: ColorUiState
    object Error: ColorUiState
}


class ColorViewModel: ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var marsUiState: ColorUiState by mutableStateOf(ColorUiState.Loading)
        private set

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getConexao()
        //subscribeToTopic()
        //publishMessage()
        getPeca()
        getMaquina()

    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     *  [List] [MutableList].
     */
    fun getPeca() {
        viewModelScope.launch{
            try {
            //    val listResult =
             //   marsUiState = ColorUiState.Success("Success: ${listResult.Cor1} Mars Photos retrieved")
            } catch(e: IOException) {
                ColorUiState.Error
            }
        }
    }

    fun getMaquina() {
        viewModelScope.launch{
            try {
              //  val listResult = ColorApi.retrofitService.getPeca()
              //  marsUiState = ColorUiState.Success("Success: ${listResult.Cor1} Mars Photos retrieved")
            } catch(e: IOException) {
                ColorUiState.Error
            }
        }
    }

    fun getConexao() {
        viewModelScope.launch{
            try {
                mqttHandler = MQTTViewModel()
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

                mqttHandler.publish(topic, message, nivelQos, retainedFlag)


            } catch(e: IOException) {
                ColorUiState.Error
            }
        }
    }






}