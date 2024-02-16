package com.example.emergencyalert

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.emergencyalert.routes.Screens
import com.example.emergencyalert.userauth.dto.UserLogin
import com.example.emergencyalert.userauth.services.AuthService
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(navController: NavController,context:Context) {
    val authService = AuthService.create()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Surface(
        color = Color.Unspecified,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp),
    ) {
        Column() {
            TextBold("Login")
            Spacer(modifier = Modifier.height(5.dp))
            email = Mytextfield(
                labelvalue = "Email",
                painterResource(id = R.drawable.gmail)
            )
            Spacer(modifier = Modifier.height(10.dp))
            password = PasswordMytextfield(
                labelvalue = "Password",
                painterResource = painterResource(id = R.drawable.lock_outline)
            )
            Spacer(modifier = Modifier.height(50.dp))
            Buttoncomponent(
                value = "Login"
            ) {
                val userLogin = UserLogin(email = email, password = password)
                scope.launch {
                    val authToken = authService.loginUser(userLogin)
                    Log.d("LoginScreen", "LoginScreen: $authToken")
                    if(authToken.success){
                        context.dataStore.edit { preferences ->
                            preferences[stringPreferencesKey("auth_counter")] = authToken.authToken
                        }
                        navController.navigate(Screens.Home.route){
                            popUpTo(0)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            ClickableLoginTextComponent(tryingToLogin = false, navController)
        }
    }
}

