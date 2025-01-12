package com.example.colorsortingcontroller.ui.home

import android.widget.Toast
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType.Companion.Password
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.colorsortingcontroller.R
import com.example.colorsortingcontroller.network.MQTTHandler

import com.example.colorsortingcontroller.theme.ColorSortingControllerTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }

    val loginState by viewModel.loginState.observeAsState("")
    val loginState2 by viewModel.loginState2.observeAsState("")



    LaunchedEffect(loginState){
        if(loginState != "" && loginState.isNotBlank()) {
            isLoading = false
            Toast.makeText(
                context,
                loginState,
                Toast.LENGTH_SHORT
            ).show()

            if(isLoading == true){
                isLoading = false
            }

        if(loginState == "Login realizado com sucesso!" ) {
            // Iniciar autenticação biométrica
            isLoading = false

            viewModel.AutenticacaoBiometria()
        } }


    }

    LaunchedEffect(loginState2){
        if(loginState2 != "" && loginState.isNotBlank()) {

            Toast.makeText(
                context,
                loginState2,
                Toast.LENGTH_SHORT
            ).show()

            isLoading = true

            if(loginState2 != "Autenticação: realizada com sucesso!!!" ) {
                // Iniciar autenticação biométrica
                isLoading = false

                //viewModel.AutenticacaoBiometria()
            }
        }





    }

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
                        if (email.isEmpty() || password.isEmpty()) {
                            Toast.makeText(
                                context,
                                "Por favor, preencha ambos os campos de email e senha.",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {

                            isLoading = true
                            viewModel.autenticacaoFirebase(email, password)


                            viewModel.resetString()




                        }
                        },

                    modifier = Modifier.padding(top = 32.dp)
                ) {
                    Text("Entrar")
                }

                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.padding(top = 20.dp))
                }
            }
        }
    }
}

@Composable
fun LoadingScreen(isLoading: Boolean) {
    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        HomeFragmentActivity()
    }
}