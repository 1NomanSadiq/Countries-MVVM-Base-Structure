package nom.mvvm.structure.data.database

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import nom.mvvm.structure.network.model.response.countries.CoatOfArms
import nom.mvvm.structure.network.model.response.countries.Demonyms
import nom.mvvm.structure.network.model.response.countries.Flags
import nom.mvvm.structure.network.model.response.countries.Idd
import nom.mvvm.structure.network.model.response.countries.Maps
import nom.mvvm.structure.network.model.response.countries.Name

@TypeConverters
class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromStringToList(value: String?): List<String>? =
        fromJson<String?>(value, true) as List<String>?

    @TypeConverter
    fun fromListToString(list: List<String>?): String? = toJson(list)

    @TypeConverter
    fun fromLatLng(latLng: List<Double>?): String? = toJson(latLng)

    @TypeConverter
    fun toLatLng(json: String?): List<Double>? = fromJson<Double?>(json, true) as List<Double>?

    @TypeConverter
    fun fromStringToCoatOfArms(value: String?): CoatOfArms? =
        fromJson<CoatOfArms>(value, false) as CoatOfArms?

    @TypeConverter
    fun fromCoatOfArmsToString(coatOfArms: CoatOfArms?): String? = toJson(coatOfArms)

    @TypeConverter
    fun fromDemonyms(demonym: Demonyms?): String? = toJson(demonym)

    @TypeConverter
    fun toDemonyms(json: String?): Demonyms? = fromJson<Demonyms>(json) as Demonyms?

    @TypeConverter
    fun fromFlags(flags: Flags?): String? = toJson(flags)

    @TypeConverter
    fun toFlags(json: String?): Flags? = fromJson<Flags>(json) as Flags?

    @TypeConverter
    fun fromIdd(idd: Idd?): String? = toJson(idd)

    @TypeConverter
    fun toIdd(json: String?): Idd? = fromJson<Idd>(json) as Idd?

    @TypeConverter
    fun fromMaps(maps: Maps?): String? = toJson(maps)

    @TypeConverter
    fun toMaps(json: String?): Maps? = fromJson<Maps>(json) as Maps?

    @TypeConverter
    fun fromName(name: Name?): String? = toJson(name)

    @TypeConverter
    fun toName(json: String?): Name? = fromJson<Name>(json) as Name?

    inline fun <reified T> fromJson(json: String?, withList: Boolean = false): Any? {
        if (json.isNullOrEmpty()) {
            return null
        }
        return try {
            if (withList) {
                val listType = object : TypeToken<List<T>>() {}.type
                Gson().fromJson(json, listType) as List<T>
            } else {
                Gson().fromJson(json, T::class.java)
            }
        } catch (e: Exception) {
            null
        }
    }

    inline fun <reified T> toJson(data: T?, withList: Boolean = false): String? {
        if (data == null) {
            return null
        }
        return try {
            if (withList && data is List<*>) {
                val listType = object : TypeToken<List<T>>() {}.type
                Gson().toJson(data, listType)
            } else {
                Gson().toJson(data)
            }
        } catch (e: Exception) {
            null
        }
    }

}

