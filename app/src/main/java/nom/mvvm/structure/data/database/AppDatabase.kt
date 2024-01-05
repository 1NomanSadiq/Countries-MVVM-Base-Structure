package nom.mvvm.structure.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import nom.mvvm.structure.data.database.country.Item
import nom.mvvm.structure.data.database.country.ItemDao

@Database(entities = [Item::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}