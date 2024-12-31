package com.example.colorsortingcontroller

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType.Companion.Password
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.Executor
import com.example.colorsortingcontroller.ui.theme.ColorSortingControllerTheme
import kotlinx.coroutines.delay
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.ui.platform.LocalContext

//Passar manipulação de dados para outra classe para seguir padrão viewModel
lateinit var executor: Executor
private lateinit var biometricPrompt: BiometricPrompt
private lateinit var promptInfo: BiometricPrompt.PromptInfo

interface SensorBiometrico {
    fun AutenticacaoBiometria()
}


class LoginScreen : FragmentActivity(), SensorBiometrico {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        setContent {
            ColorSortingControllerTheme {
                LoadingScreen()
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            // Depois de 3 segundos, exibe a tela de login
            setContent {
                ColorSortingControllerTheme {
                    ScaffoldLogin(auth = auth)
                }
            }
        }, 3000)  // Tempo de delay (3 segundos)

        // Inicializa o biometric prompt
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(applicationContext, "Erro de Autenticação: $errString", Toast.LENGTH_SHORT).show()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(applicationContext, "Autenticação: realizada com sucesso !!!", Toast.LENGTH_SHORT).show()

                    // Fecha a tela de login e segue para a tela principal
                    val intent = Intent(this@LoginScreen, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext, "Falha na autenticação biométrica", Toast.LENGTH_SHORT).show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Login Biométrico")
            .setSubtitle("Logue usando sua credencial biométrica")
            .setConfirmationRequired(false)
            .setAllowedAuthenticators(BIOMETRIC_STRONG or BIOMETRIC_WEAK or DEVICE_CREDENTIAL)
            .build()
    }

    override fun AutenticacaoBiometria() {

        //withContext(Dispatchers.IO) {

        //   val biometricManager = BiometricManager.from(applicationContext)
        /*  when(biometricManager.canAuthenticate(BIOMETRIC_STRONG or BIOMETRIC_WEAK or DEVICE_CREDENTIAL)){
        BiometricManager.BIOMETRIC_SUCCESS ->
           // biometricPrompt.authenticate(promptInfo)
            Log.d("Sensor_Biometrico", "O aplicativo pode utilizar autenticação biométrica")
            //    Snackbar.make(this, "",  )
         //   biometricPrompt.authenticate(promptInfo)

        BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> //{
            Log.e(
                "Sensor_Biometrico",
                "Nenhum recurso biométrico disponível neste dispositivo."
            )
           // Toast.makeText(
           //     applicationContext, "Nenhum recurso biométrico disponível neste dispositivo.",
          //      Toast.LENGTH_SHORT
           // ).show()
      //  }
        BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> //{
            Log.e(
                "Sensor_Biometrico",
                "Os recursos biométricos não estão disponíveis no momento."
            )
         //   Toast.makeText(
          //      applicationContext, "Os recursos biométricos não estão disponíveis no momento.",
         //       Toast.LENGTH_SHORT
          //  ).show()
       // }
    //    BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
      //      val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply{
      //          putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
     //               BIOMETRIC_STRONG or BIOMETRIC_WEAK or DEVICE_CREDENTIAL)
       //     }
       //     startActivityForResult(enrollIntent, 1)
       // }



    } }*/
        biometricPrompt.authenticate(promptInfo)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldLogin(auth: FirebaseAuth) {
    val context = LocalContext.current // Obtém o Contexto válido do Compose

    ColorSortingControllerTheme {
        Scaffold(
            topBar = {
                TopAppBar(
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
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var email by rememberSaveable { mutableStateOf("") }
                var password by rememberSaveable { mutableStateOf("") }
                var passwordVisibility by rememberSaveable { mutableStateOf(false) }
                val chamadaDeFuncao: SensorBiometrico = LoginScreen()

                val icon = if (passwordVisibility)
                    painterResource(id = R.drawable.design_icon_visibility)
                else
                    painterResource(id = R.drawable.design_icon_visibility_off)

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text(text = "Email") },
                    label = { Text("Insira o seu email") }
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
                        auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // Login bem-sucedido
                                    Toast.makeText(
                                        context,
                                        "Login realizado com sucesso!",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    // Redireciona para a tela principal
                                    val intent = Intent(context, MainActivity::class.java)
                                    context.startActivity(intent)
                                } else {
                                    // Falha no login
                                    Toast.makeText(
                                        context,
                                        "Erro: ${task.exception?.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    },
                    modifier = Modifier.padding(top = 32.dp)
                ) {
                    Text("Entrar")
                }
                Button(
                    onClick = { chamadaDeFuncao.AutenticacaoBiometria() },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Sensor Biométrico")
                }
            }
        }
    }
}

@Composable
fun LoadingScreen() {

    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(3000)
        isLoading = false
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LoginScreen()
    }
}


//class BateriaScreen : FragmentActivity() {

//@RequiresApi(Build.VERSION_CODES.TIRAMISU)
// override fun quantidadeBateria()  {
//  val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
//      this.registerReceiver(null
//      , ifilter)

//   }


//  batteryPct = batteryStatus?.let { intent ->
//     val level: Int = BatteryManager.EXTRA_LEVEL.toInt()
//      val scale: Int = BatteryManager.EXTRA_SCALE.toInt()
//      level * 100 / scale.toFloat()
//  }
//   return batteryPct

//  }
//}







