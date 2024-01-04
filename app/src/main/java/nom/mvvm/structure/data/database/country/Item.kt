package nom.mvvm.structure.data.database.country

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item(
    val name: String = "",
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val ratePerKg: Int = 0
)