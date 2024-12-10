package com.mohaberabi.kmp.localsession

import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIGestureRecognizer
import platform.UIKit.UIGestureRecognizerDelegateProtocol
import platform.UIKit.UIPress
import platform.UIKit.UITouch
import platform.darwin.NSObject

class InteractionListenableDelegate(
    private val onUserInteraction: () -> Unit
) : UIGestureRecognizerDelegateProtocol, NSObject() {

    override fun gestureRecognizer(
        gestureRecognizer: UIGestureRecognizer,
        shouldReceiveTouch: UITouch
    ): Boolean {
        onUserInteraction()
        return true
    }

    override fun gestureRecognizer(
        gestureRecognizer: UIGestureRecognizer,
        shouldReceivePress: UIPress
    ): Boolean {
        onUserInteraction()
        return true
    }
}