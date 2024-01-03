package nom.mvvm.structure.data

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


val Context.prefs: SharedPreference get() = SharedPreference(this)
val Fragment.prefs: SharedPreference get() = SharedPreference(requireActivity())

class SharedPreference(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE)

    val sariyaRate by prefs.double()
    val pipeRate by prefs.double()
    val chadarChorasRate by prefs.double()
    val chadarGolTikki by prefs.double()
    val chadarRing by prefs.double()
    val hich by prefs.double()


    fun clearAll() {
        prefs.edit().clear().apply()
    }


    companion object {
        private const val SETTINGS_NAME = "default_settings"
    }
}

fun SharedPreferences.string(
    defaultValue: String = "",
    key: String? = null
): ReadWriteProperty<Any, String> =
    object : ReadWriteProperty<Any, String> {
        override fun getValue(thisRef: Any, property: KProperty<*>): String =
            getString(key ?: property.name, defaultValue) ?: ""

        override fun setValue(thisRef: Any, property: KProperty<*>, value: String) =
            edit().putString(key ?: property.name, value).apply()
    }

fun SharedPreferences.int(defaultValue: Int = 0, key: String? = null): ReadWriteProperty<Any, Int> =
    object : ReadWriteProperty<Any, Int> {
        override fun getValue(thisRef: Any, property: KProperty<*>): Int =
            getInt(key ?: property.name, defaultValue)

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) =
            edit().putInt(key ?: property.name, value).apply()
    }

fun SharedPreferences.boolean(
    defaultValue: Boolean = false,
    key: String? = null
): ReadWriteProperty<Any, Boolean> =
    object : ReadWriteProperty<Any, Boolean> {
        override fun getValue(thisRef: Any, property: KProperty<*>): Boolean =
            getBoolean(key ?: property.name, defaultValue)

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) =
            edit().putBoolean(key ?: property.name, value).apply()
    }

inline fun <reified T> SharedPreferences.objectModel(
    defaultValue: T? = null,
    key: String? = null
): ReadWriteProperty<Any, T?> =
    object : ReadWriteProperty<Any, T?> {
        override fun getValue(thisRef: Any, property: KProperty<*>): T? =
            Gson().fromJson(
                getString(key ?: property.name, ""),
                T::class.java
            ) ?: defaultValue

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) =
            edit().putString(key ?: property.name, Gson().toJson(value)).apply()
    }

inline fun <reified T> SharedPreferences.collections(
    defaultValue: T? = null,
    key: String? = null
): ReadWriteProperty<Any, T?> =
    object : ReadWriteProperty<Any, T?> {
        override fun getValue(thisRef: Any, property: KProperty<*>): T? =
            Gson().fromJson(
                getString(key ?: property.name, ""),
                object : TypeToken<T>() {}.type
            ) ?: defaultValue

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) =
            edit().putString(key ?: property.name, Gson().toJson(value)).apply()
    }


fun SharedPreferences.float(
    defaultValue: Float = 0f,
    key: String? = null
): ReadWriteProperty<Any, Float> =
    object : ReadWriteProperty<Any, Float> {
        override fun getValue(thisRef: Any, property: KProperty<*>): Float =
            getFloat(key ?: property.name, defaultValue)

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Float) =
            edit().putFloat(key ?: property.name, value).apply()
    }

fun SharedPreferences.double(
    defaultValue: Double = 0.0,
    key: String? = null
): ReadWriteProperty<Any, Double> =
    object : ReadWriteProperty<Any, Double> {
        override fun getValue(thisRef: Any, property: KProperty<*>): Double =
            getString(key ?: property.name, defaultValue.toString())?.toDouble() ?: defaultValue

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Double) =
            edit().putString(key ?: property.name, value.toString()).apply()
    }

fun SharedPreferences.long(
    defaultValue: Long = 0L,
    key: String? = null
): ReadWriteProperty<Any, Long> =
    object : ReadWriteProperty<Any, Long> {
        override fun getValue(thisRef: Any, property: KProperty<*>): Long =
            getLong(key ?: property.name, defaultValue)

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Long) =
            edit().putLong(key ?: property.name, value).apply()
    }