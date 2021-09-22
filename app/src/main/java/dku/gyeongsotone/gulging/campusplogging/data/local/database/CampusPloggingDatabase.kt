package dku.gyeongsotone.gulging.campusplogging.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dku.gyeongsotone.gulging.campusplogging.data.local.dao.PloggingDao
import dku.gyeongsotone.gulging.campusplogging.data.local.database.Converters
import dku.gyeongsotone.gulging.campusplogging.data.local.model.Plogging

@Database(entities = [Plogging::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CampusPloggingDatabase : RoomDatabase() {
    abstract fun ploggingDao(): PloggingDao

    companion object {
        @Volatile
        private var INSTANCE: CampusPloggingDatabase? = null

        fun getDatabase(context: Context): CampusPloggingDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CampusPloggingDatabase::class.java,
                    "campus_plogging_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}