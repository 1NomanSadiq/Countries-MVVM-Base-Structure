package nom.mvvm.structure.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.truebilling.truechargecapture.drs.data.database.providerfacility.CountryDao
import nom.mvvm.structure.data.database.providerfacility.Country

@Database(entities = [Country::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao
}