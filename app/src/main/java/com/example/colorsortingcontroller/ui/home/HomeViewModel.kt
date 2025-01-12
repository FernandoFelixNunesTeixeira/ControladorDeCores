package com.example.colorsortingcontroller.ui.home

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler

import androidx.annotation.RequiresApi


import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.colorsortingcontroller.MainActivity


import com.google.firebase.auth.FirebaseAuth


import java.util.concurrent.Executor



class HomeViewModel(private val auth: FirebaseAuth) : ViewModel(), SensorBiometrico {

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: androidx.biometric.BiometricPrompt
    private lateinit var promptInfo: androidx.biometric.BiometricPrompt.PromptInfo
    private val _LoginState = MutableLiveData<String>()
    val loginState: LiveData<String> get() = _LoginState

    private val _LoginState2 = MutableLiveData<String>()
    val loginState2: LiveData<String> get() = _LoginState2




    @RequiresApi(Build.VERSION_CODES.P)
    fun inicializarBiometria(context: Context) {

    executor = ContextCompat.getMainExecutor(context)
    biometricPrompt = androidx.biometric.BiometricPrompt(context as FragmentActivity, executor,
    object : androidx.biometric.BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            _LoginState2.value = "Autenticação: realizada com sucesso!!!"
            Handler().postDelayed({
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
                (context as FragmentActivity).finish()
            }, 3000) // Delay de 3 segundos
        }

        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)
            _LoginState2.value = "Erro de Autenticação biométrica: $errString"

        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
            _LoginState2.value = "Falha na autenticação biométrica"
        }
    })

promptInfo = androidx.biometric.BiometricPrompt.PromptInfo.Builder()
    .setTitle("Login Biométrico")
    .setSubtitle("Logue usando sua credencial biométrica")
    .setConfirmationRequired(false)
    .setAllowedAuthenticators(BIOMETRIC_STRONG or BIOMETRIC_WEAK or DEVICE_CREDENTIAL)
    .build()
}

fun autenticacaoFirebase(email: String, password:String){
auth.signInWithEmailAndPassword(email, password)
    .addOnCompleteListener { task ->
        if(task.isSuccessful) {
            _LoginState.value = "Login realizado com sucesso!"
        } else {
            _LoginState.value = "Erro: ${task.exception?.message}"
        }
    }
}

fun resetString(){
_LoginState.value = ""
_LoginState2.value = ""
}

override fun AutenticacaoBiometria() {
biometricPrompt.authenticate(promptInfo)
}


}