package dku.gyeongsotone.gulging.campusplogging.data.local.database

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.math.floor

class Converters {

    /** Date <-> Long */
    @TypeConverter
    fun fromTimestamp(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time.toLong()
    }


    /** Bitmap <-> ByteArray */
    @TypeConverter
    fun toBitmap(bytes: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }
    @TypeConverter
    fun fromBitmap(bitmap: Bitmap): ByteArray {
        val sizeMB = bitmap.byteCount.toDouble() / 1024 / 1024

        var quality = 100.0
        if (sizeMB > 10) {
            quality = 1000.0 / sizeMB
        }

        Log.d("SIZE_TEST", "raw sizeMB: $sizeMB, quality: $quality")
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, floor(quality).toInt(), outputStream)

        Log.d("SIZE_TEST", "compressed sizeMB: ${bitmap.byteCount.toDouble() / 1024 / 1024}")
        return outputStream.toByteArray()
    }
}