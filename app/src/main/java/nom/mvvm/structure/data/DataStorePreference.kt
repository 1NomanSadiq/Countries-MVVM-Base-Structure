package nom.mvvm.structure.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import nom.mvvm.structure.utils.Constants
import nom.mvvm.structure.utils.extensions.common.dataStore

class DataStorePreference(context: Context) :
    DataStorePreferenceRepository {
    private val dataStore = context.dataStore
    override val email: Flow<String>
        get() {
            val emailKey = stringPreferencesKey(Constants.EMAIL)
            return dataStore.data.map { preferences ->
                preferences[emailKey] ?: ""
            }
        }

    override suspend fun setEmail(value: String) {

        val emailKey = stringPreferencesKey(Constants.EMAIL)
        dataStore.edit { preferences ->
            preferences[emailKey] = value
        }
    }

    override suspend fun clear() {
        dataStore.edit { it.clear() }
    }
}
