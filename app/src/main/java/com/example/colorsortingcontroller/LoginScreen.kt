package com.example.colorsortingcontroller

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType.Companion.Password
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.Executor

//Passar manipulação de dados para outra classe para seguir padrão viewModel
lateinit var executor: Executor
private lateinit var biometricPrompt: BiometricPrompt
private lateinit var promptInfo: BiometricPrompt.PromptInfo

class LoginScreen : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        executor = ContextCompat.getMainExecutor(this)

        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence){
                    super.onAuthenticationError(errorCode, errString)

                    //Verificar possíveis problemas dentro de função compose
                    Toast.makeText(applicationContext,"Erro de Autenticação: $errString",
                        Toast.LENGTH_SHORT).show()

                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult){
                    super.onAuthenticationSucceeded(result)

                    //Verificar possíveis problemas dentro de função compose
                    Toast.makeText(applicationContext,"Autenticação: realizada com sucesso !!!",
                        Toast.LENGTH_SHORT).show()

                    // Fecha a tela de login e segue para a tela principal
                    val intent = Intent(this@LoginScreen, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                }
                override fun onAuthenticationFailed(){
                    super.onAuthenticationFailed()
                    //Verificar possíveis problemas dentro de função compose
                    //Toast.makeText(applicationContext,"Autenticação falhou",
                    //Toast.LENGTH_SHORT).show()
                    //Criar tela de Login
                    setContent {
                        MaterialTheme {
                            ScaffoldLogin()
                        }
                    }
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder( )
            .setTitle("Login Biométrico")
            .setSubtitle("Logue usando sua credencial biométrica")
          //  .setNegativeButtonText("Usar senha de conta")
            .setConfirmationRequired(false)
            .setAllowedAuthenticators(BIOMETRIC_STRONG or BIOMETRIC_WEAK or DEVICE_CREDENTIAL)
            .build()

        setContent {
            MaterialTheme {
                ScaffoldLogin()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldLogin() {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = Color(0xFF0B737E),
                    titleContentColor = Color(0xFFFFFFFF),
                ),
                title = {
                    Text(
                        text = "Tela de Login",
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(Color(0xFFF5F5F5))
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var text by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var passwordVisibility by remember { mutableStateOf(false) }

            val icon = if (passwordVisibility)
                painterResource(id = R.drawable.design_icon_visibility)
            else
                painterResource(id = R.drawable.design_icon_visibility_off)

            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                placeholder = { Text(text = "Login") },
                label = { Text("Insira o seu login") }
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text(text = "Senha") },
                label = { Text("Insira a sua senha") },
                trailingIcon = {
                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        Icon(painter = icon, contentDescription = "Visibility Icon")
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = Password
                ),
                visualTransformation = if (passwordVisibility) VisualTransformation.None
                else PasswordVisualTransformation(),
                modifier = Modifier.padding(top = 16.dp)
            )
            Button(
                onClick = {
                    // nada ainda
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0B737E),
                    contentColor = Color.White
                ),
                modifier = Modifier.padding(top = 32.dp)
            ) {
                Text("Entrar")
            }

            Button(
                onClick = { AutenticacaoBiometria() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0B737E),
                    contentColor = Color.White
                ),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Sensor Biométrico")
            }
        }
    }
}

fun AutenticacaoBiometria() {
     biometricPrompt.authenticate(promptInfo)

}