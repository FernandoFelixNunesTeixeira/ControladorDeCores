package com.example.colorsortingcontroller

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Button
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

class MainActivity2 : FragmentActivity() {
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

                  //  val intent = Intent(this, MainActivity::class.java)
                  // applicationContext.startActivity(intent)

                }
                override fun onAuthenticationFailed(){
                    super.onAuthenticationFailed()
                    //Verificar possíveis problemas dentro de função compose
                  //  Toast.makeText(applicationContext,"Autenticação falhou",
                    //Toast.LENGTH_SHORT).show()
                    //Criar tela de Login
                    setContent {
                        MaterialTheme {
                            ScaffoldMonitoramento2()
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
                ScaffoldMonitoramento2()

            }
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldMonitoramento2() {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val context = LocalContext.current

    ModalNavigationDrawer(
        drawerState = drawerState,

        drawerContent = {
            Column(modifier = Modifier.fillMaxSize().background(Color.LightGray).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Card(modifier = Modifier, shape = RectangleShape, colors = CardDefaults.cardColors(
                    containerColor = Color.LightGray
                )) {
                    Text(
                        text = "Monitoramento",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                Card(modifier = Modifier, shape = RectangleShape,  colors = CardDefaults.cardColors(
                    containerColor = Color.LightGray
                )) {
                    Text(
                        modifier = Modifier.padding(top = 8.dp).clickable {
                            // Navega para a ParametrosActivity
                            val intent = Intent(context, ParametrosActivity::class.java)
                            context.startActivity(intent)
                        },
                        text = "Parâmetros",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                Card(modifier = Modifier, shape = RectangleShape, colors = CardDefaults.cardColors(
                    containerColor = Color.LightGray
                )) {
                    Text(
                        modifier = Modifier.padding(top = 8.dp).clickable {
                            // Navega para a EstatisticasActivity
                            val intent = Intent(context, EstatisticasActivity::class.java)
                            context.startActivity(intent)
                        },
                        text = "Estatísticas",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
    ) {
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
                modifier = Modifier.background(Color(0xFFF5F5F5)).fillMaxSize()
                    .padding(innerPadding)
                    .padding(innerPadding)
                    .padding(innerPadding)
                    .background(Color(0x00000000)
                    ),


                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
               // Text(
              //      modifier = Modifier.padding(8.dp),
               //     text = "nada ainda"
               // )
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
                    placeholder = { Text(text = "Login")},
                    label = { Text("Insira o seu login")}

                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text(text = "Senha")},
                    label = { Text("Insira a sua senha")},
                    trailingIcon = {
                        IconButton(onClick = { passwordVisibility = !passwordVisibility  }) {
                            Icon(painter = icon,  contentDescription = "Visibility Icon")
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = Password
                    ),
                    visualTransformation = if(passwordVisibility) VisualTransformation.None
                        else PasswordVisualTransformation()

                )
                Button(
                    onClick = {

                    }
                ) {
                    Text("Entrar")
                }

                Button(
                    onClick = { AutenticacaoBiometria()}
                ) {
                    Text("Sensor Biométrico")
                }


            }
        }
    }}




fun AutenticacaoBiometria() {
     biometricPrompt.authenticate(promptInfo)

}