package dku.gyeongsotone.gulging.campusplogging.data.local.dao

import androidx.room.*
import dku.gyeongsotone.gulging.campusplogging.data.local.model.Plogging
import kotlinx.coroutines.flow.Flow

@Dao
interface PloggingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Plogging)

    @Update
    suspend fun update(item: Plogging)

    @Delete
    suspend fun delete(item: Plogging)

    @Query("SELECT sum(distance) FROM plogging")
    suspend fun getTotalDistance(): Double?

    @Query("SELECT sum(time) FROM plogging")
    suspend fun getTotalTime(): Int?

    @Query("SELECT sum(badge) FROM plogging")
    suspend fun getTotalBadge(): Int?
}