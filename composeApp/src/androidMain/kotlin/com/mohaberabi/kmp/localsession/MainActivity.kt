package com.mohaberabi.kmp.localsession

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {

    private lateinit var sessionManager: LocalSessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManager = DefaultLocalSessionManager().also {
            it.startSession()
        }
        setContent {
            App(
                sessionManager = sessionManager
            )
        }
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        sessionManager.startSession()
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(
        sessionManager = StubLocalSessionManager(),
    )
}