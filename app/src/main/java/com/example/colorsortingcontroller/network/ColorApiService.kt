package com.example.colorsortingcontroller.network



private val BROKER_URL = "ssl://44a41899400a4d2687717200b79f04cb.s1.eu.hivemq.cloud:8883"
private val CLIENT_ID = "Android_Client"
///private val retrofit = Retrofit.Builder().addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
 //   .baseUrl(BASE_URL)
 //   .build()

//class MarsApiService {

interface ColorApiService {
    //@GET("photos")
    //suspend fun getPeca(): Peca
   // suspend fun getMaquina(): Maquina
}

object ColorApi {
   // val retrofitService: ColorApiService by lazy {
      //  retrofit.create(ColorApiService::class.java)
   // }
}