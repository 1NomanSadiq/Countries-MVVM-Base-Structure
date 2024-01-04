package nom.mvvm.structure.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import nom.mvvm.structure.data.database.country.CountryDao
import nom.mvvm.structure.data.database.country.Country
import nom.mvvm.structure.data.database.country.Item
import nom.mvvm.structure.data.database.country.ItemDao

@Database(entities = [Country::class, Item::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao
    abstract fun itemDao(): ItemDao
}