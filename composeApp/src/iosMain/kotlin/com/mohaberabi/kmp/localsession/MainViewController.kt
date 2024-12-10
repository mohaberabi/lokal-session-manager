package com.mohaberabi.kmp.localsession

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIGestureRecognizer
import platform.UIKit.UIViewController


private val sessionManager by lazy {
    DefaultLocalSessionManager()
}


fun MainViewController(
): UIViewController {
    val tapRecognizerDelegate = InteractionListenableDelegate(
        onUserInteraction = {
            sessionManager.startSession()
        },
    )
    val tapRecognizer = UIGestureRecognizer().apply {
        delegate = tapRecognizerDelegate
    }
    val composeController = ComposeUIViewController(
        content = {
            Box(
                Modifier.fillMaxSize().pointerInput(Unit) {
                    detectTapGestures(

                        onTap = {
                            sessionManager.startSession()
                        },

                        )
                },
            ) {
                App(
                    sessionManager = sessionManager,
                )
            }

        },
    ).apply {
        view.addGestureRecognizer(tapRecognizer)
    }
    return composeController
}



