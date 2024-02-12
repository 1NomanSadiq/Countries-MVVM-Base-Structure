package com.gsc.app.utils.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

interface DispatchersProviders {
    fun getIO(): CoroutineDispatcher
    fun getMain(): CoroutineDispatcher
    fun getMainImmediate(): CoroutineDispatcher
    fun getDefault(): CoroutineDispatcher
}