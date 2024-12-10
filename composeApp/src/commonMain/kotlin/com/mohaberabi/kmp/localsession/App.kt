package com.mohaberabi.kmp.localsession

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import localsession.composeapp.generated.resources.Res
import localsession.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App(
    sessionManager: LocalSessionManager
) {

    var showLoginScreen by remember { mutableStateOf(true) }
    DisposableEffect(
        Unit,
    ) {
        sessionManager.startSession()
        onDispose { sessionManager.endSession() }
    }

    LaunchedEffect(
        sessionManager.getLocalSessionEvents(),
    ) {

        sessionManager.getLocalSessionEvents().collect { event ->
            when (event) {
                LocalSessionEvents.SessionEnded -> {
                    showLoginScreen = true
                }
            }
        }
    }
    MaterialTheme {


        Box(
            Modifier.fillMaxSize(),
        ) {
            HomeScreen(
                modifier = Modifier.fillMaxSize().padding(20.dp),
            )
            if (showLoginScreen) {
                LoginScreen(
                    modifier = Modifier.fillMaxSize().padding(20.dp),
                    onLogin = {
                        showLoginScreen = false
                    },
                )
            }


        }

    }
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLogin: () -> Unit,
) {
    Scaffold { padding ->
        Column(
            modifier = modifier.padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text("Welcome Login Screen")
            Button(
      
                onClick = onLogin,
            ) {
                Text("Login")
            }
        }
    }

}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Scaffold { padding ->
        Column(
            modifier = modifier.padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text("Welcome Home Screen")
            Button(

                onClick = { println("doing  anything") },
            ) {
                Text("Do anything ")
            }
        }
    }

}
