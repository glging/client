package dku.gyeongsotone.gulging.campusplogging.data.local.dao

import androidx.room.*
import dku.gyeongsotone.gulging.campusplogging.data.local.model.Plogging
import java.util.*

@Dao
interface PloggingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Plogging)

    @Update
    suspend fun update(item: Plogging)

    @Delete
    suspend fun delete(item: Plogging)

    @Query("SELECT * FROM plogging WHERE id = :id LIMIT 1")
    suspend fun getPlogging(id: Int): Plogging?

    @Query("SELECT sum(distance) FROM plogging")
    suspend fun getTotalDistance(): Double?

    @Query("SELECT sum(time) FROM plogging")
    suspend fun getTotalTime(): Int?

    @Query("SELECT sum(badge) FROM plogging")
    suspend fun getTotalBadge(): Int?

    @Query("SELECT sum(plastic + vinyl + can + paper + glass + general) FROM plogging")
    suspend fun getTotalTrash(): Int?

    @Query("SELECT (sum(plastic) > 0) + (sum(vinyl) > 0) + (sum(can) > 0) + (sum(paper) > 0) + (sum(glass) > 0) + (sum(general) > 0) FROM plogging")
    suspend fun getTrashKind(): Int?

    @Query("SELECT avg(distance) FROM plogging WHERE startDate BETWEEN :from AND :to")
    suspend fun getMonthlyDistance(from: Date, to: Date): Double?

    @Query("SELECT avg(time) FROM plogging WHERE startDate BETWEEN :from AND :to")
    suspend fun getMonthlyTime(from: Date, to: Date): Double?

    @Query("SELECT avg(plastic + vinyl + can + paper + glass + general) FROM plogging WHERE startDate BETWEEN :from AND :to")
    suspend fun getMonthlyTrash(from: Date, to: Date): Double?

    @Query("SELECT * FROM plogging WHERE startDate BETWEEN :from AND :to")
    suspend fun getMonthlyPlogging(from: Date, to: Date): List<Plogging>?

}
