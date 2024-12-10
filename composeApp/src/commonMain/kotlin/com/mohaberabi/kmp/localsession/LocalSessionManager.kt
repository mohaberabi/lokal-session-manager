package com.mohaberabi.kmp.localsession

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


const val TIMEOUT_DURATION = 2 * 60 * 1000L

sealed interface LocalSessionEvents {
    data object SessionEnded : LocalSessionEvents
}

interface LocalSessionManager {
    fun getLocalSessionEvents(): Flow<LocalSessionEvents>
    fun startSession()
    fun endSession()
}

class StubLocalSessionManager : LocalSessionManager {
    override fun getLocalSessionEvents(): Flow<LocalSessionEvents> = flowOf()
    override fun startSession() {}
    override fun endSession() {}
}

class DefaultLocalSessionManager : LocalSessionManager {
    private val channel = Channel<LocalSessionEvents>()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var sessionJob: Job? = null
    override fun getLocalSessionEvents(): Flow<LocalSessionEvents> =
        channel.receiveAsFlow().flowOn(Dispatchers.IO)

    override fun startSession() {
        sessionJob?.cancel()
        sessionJob = null
        println("LocalSessionManager : startSession")
        sessionJob = coroutineScope.launch {
            println("LocalSessionManager : delay started")
            delay(TIMEOUT_DURATION)
            channel.send(LocalSessionEvents.SessionEnded)
        }
    }


    override fun endSession() {
        sessionJob?.cancel()
        sessionJob = null
        coroutineScope.cancel()
        println("LocalSessionManager :canceled session")

    }
}

