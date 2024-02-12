package com.gsc.app.data

import kotlinx.coroutines.flow.Flow


interface DataStorePreferenceRepository {
    val email: Flow<String>
    suspend fun setEmail(value: String)
    suspend fun clear()
}