package nom.mvvm.structure.data

import android.content.Context
import android.content.SharedPreferences
import nom.mvvm.structure.utils.Constants

class SharedPreference (context: Context) : SharedPreferenceRepository {
    private val prefs: SharedPreferences = context.getSharedPreferences(Constants.PREFS_FILENAME, 0)

    override var email: String
        get() = prefs.getString(Constants.EMAIL, "") ?: ""
        set(value) {
            prefs.edit().putString(Constants.EMAIL, value).apply()
        }


    override fun clear() {
        prefs.edit().clear().apply()
    }
}