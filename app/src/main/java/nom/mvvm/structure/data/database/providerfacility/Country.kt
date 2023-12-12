package nom.mvvm.structure.data.database.providerfacility

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import nom.mvvm.structure.data.database.Converters
import nom.mvvm.structure.network.model.response.countries.CoatOfArms
import nom.mvvm.structure.network.model.response.countries.Demonyms
import nom.mvvm.structure.network.model.response.countries.Flags
import nom.mvvm.structure.network.model.response.countries.Idd
import nom.mvvm.structure.network.model.response.countries.Maps
import nom.mvvm.structure.network.model.response.countries.Name

@Entity
data class Country(
    @PrimaryKey(autoGenerate = true) val _id: Int,
    val area: Double?,
    @TypeConverters(Converters::class) val capital: List<String>?,
    @TypeConverters(Converters::class) val coatOfArms: CoatOfArms?,
    @TypeConverters(Converters::class) val continents: List<String>?,
    @TypeConverters(Converters::class) val demonyms: Demonyms?,
    @TypeConverters(Converters::class) val flags: Flags?,
    @TypeConverters(Converters::class) val idd: Idd?,
    @TypeConverters(Converters::class) val latlng: List<Double>?,
    @TypeConverters(Converters::class) val maps: Maps?,
    val population: Int?,
    val subregion: String?,
    @TypeConverters(Converters::class) val timezones: List<String>?,
    @TypeConverters(Converters::class) val name: Name?
) {
    val map get() = "${maps?.googleMaps}\n${maps?.openStreetMaps}"
    val capitals get() = capital?.joinToString(", ")
    val continent get() = continents?.joinToString(", ")
    val timezone get() = timezones?.joinToString(", ")
    val latlngStr get() = latlng?.joinToString(", ")
    val nameStr get() = "Common: ${name?.common}\nOfficial: ${name?.official}"
    val demonym: String
        get() {
            var finalText = ""
            if (demonyms?.eng?.m.isNullOrBlank()
                    .not()
            ) finalText += "Male: ${demonyms?.eng?.m} (eng)\n"
            if (demonyms?.fra?.m.isNullOrBlank()
                    .not()
            ) finalText += "Male: ${demonyms?.fra?.m} (fra)\n"
            if (demonyms?.eng?.f.isNullOrBlank()
                    .not()
            ) finalText += "Female: ${demonyms?.eng?.f} (eng)\n"
            if (demonyms?.fra?.f.isNullOrBlank()
                    .not()
            ) finalText += "Female: ${demonyms?.fra?.f} (fra)\n"
            return finalText.removeSuffix("\n")
        }
    val countryCode get() = "${idd?.root}${idd?.suffixes?.firstOrNull()}"
}