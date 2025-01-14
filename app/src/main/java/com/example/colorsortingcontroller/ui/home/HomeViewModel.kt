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
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private val _LoginState = MutableLiveData<String>()
    val loginState: LiveData<String> get() = _LoginState

    private val _BiometricState = MutableLiveData<String>()
    val biometricState: LiveData<String> get() = _BiometricState

    @RequiresApi(Build.VERSION_CODES.P)
    fun inicializarBiometria(context: Context) {

        executor = ContextCompat.getMainExecutor(context)

        biometricPrompt = BiometricPrompt(context as FragmentActivity, executor,
        object : BiometricPrompt.AuthenticationCallback() {
            // Caso autenticação biométrica tenha sucesso
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                _BiometricState.value = "Autenticação: realizada com sucesso!"
                Handler().postDelayed({
                    // Chama a próxima tela MainActivity
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                    (context).finish()
                }, 3000) // Delay de 3 segundos para a tela de loading
            }

            // Tratamento de erro da autenticação biométrica
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                _BiometricState.value = "Erro de Autenticação biométrica: $errString"

            }

            // Caso autenticação biométrica falhe
            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                _BiometricState.value = "Falha na autenticação biométrica"
            }
        })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Login Biométrico")
            .setSubtitle("Logue usando sua credencial biométrica")
            .setConfirmationRequired(false)
            .setAllowedAuthenticators(BIOMETRIC_STRONG or BIOMETRIC_WEAK or DEVICE_CREDENTIAL)
            .build()
    }

    // Ferramente do Firebase para autenticação email-senha

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
        _BiometricState.value = ""
    }

    override fun AutenticacaoBiometria() {
        biometricPrompt.authenticate(promptInfo)
    }
}