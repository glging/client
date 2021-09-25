package dku.gyeongsotone.gulging.campusplogging.data.local.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "plogging")
data class Plogging(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val startDate: Long,
    val endDate: Long,
    val distance: Double,    // km
    val time: Int,           // minute
    val badge: Int = 0,      // count
    val picture: Bitmap,
    val plastic: Int = 0,
    val vinyl: Int = 0,
    val glass: Int = 0,
    val can: Int = 0,
    val paper: Int = 0,
    val general: Int = 0,
)
