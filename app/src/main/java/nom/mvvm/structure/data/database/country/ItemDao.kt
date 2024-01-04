package nom.mvvm.structure.data.database.country

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Insert
    suspend fun insert(item: Item)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<Item>)

    @Query("SELECT * FROM item")
    fun getAllItems(): Flow<List<Item>>
}