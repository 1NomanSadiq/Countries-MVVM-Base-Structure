package nom.mvvm.structure.utils.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

interface DispatchersProviders {
    fun getIO(): CoroutineDispatcher
    fun getMain(): CoroutineDispatcher
    fun getMainImmediate(): CoroutineDispatcher
    fun getDefault(): CoroutineDispatcher
}