package com.gsc.app.network.model.response.countries

data class Country(
    val area: Double?,
    val capital: List<String>?,
    val coatOfArms: CoatOfArms?,
    val continents: List<String>?,
    val demonyms: Demonyms?,
    val flags: Flags?,
    val idd: Idd?,
    val latlng: List<Double>?,
    val maps: Maps?,
    val population: Int?,
    val subregion: String?,
    val timezones: List<String>?,
    val name: Name?
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
            if (demonyms?.eng?.m.isNullOrBlank().not()) finalText += "Male: ${demonyms?.eng?.m} (eng)\n"
            if (demonyms?.fra?.m.isNullOrBlank().not()) finalText += "Male: ${demonyms?.fra?.m} (fra)\n"
            if (demonyms?.eng?.f.isNullOrBlank().not()) finalText += "Female: ${demonyms?.eng?.f} (eng)\n"
            if (demonyms?.fra?.f.isNullOrBlank().not()) finalText += "Female: ${demonyms?.fra?.f} (fra)\n"
            return finalText.removeSuffix("\n")
        }
    val countryCode get() = "${idd?.root}${idd?.suffixes?.firstOrNull()}"
}