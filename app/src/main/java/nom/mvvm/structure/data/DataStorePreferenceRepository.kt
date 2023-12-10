package nom.mvvm.structure.data

import kotlinx.coroutines.flow.Flow


interface DataStorePreferenceRepository {
    val email: Flow<String>
    suspend fun setEmail(value: String)
    suspend fun clear()
}