package dku.gyeongsotone.gulging.campusplogging.data.repository

import dku.gyeongsotone.gulging.campusplogging.data.local.dao.PloggingDao
import dku.gyeongsotone.gulging.campusplogging.data.local.model.Plogging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

object PloggingRepository {
    private lateinit var dao: PloggingDao

    fun initPloggingRepository(ploggingDao: PloggingDao) {
        dao = ploggingDao
    }

    suspend fun insert(item: Plogging) = withContext(Dispatchers.IO) {
        dao.insert(item)
    }

    suspend fun update(item: Plogging) = withContext(Dispatchers.IO) {
        dao.update(item)
    }

    suspend fun delete(item: Plogging) = withContext(Dispatchers.IO) {
        dao.delete(item)
    }

    suspend fun deleteById(id: Int) = withContext(Dispatchers.IO) {
        dao.deleteById(id)
    }

    suspend fun getPlogging(id: Int): Plogging? = withContext(Dispatchers.IO) {
        return@withContext dao.getPlogging(id)
    }

    suspend fun getTotalDistance(): Double = withContext(Dispatchers.IO) {
        return@withContext dao.getTotalDistance() ?: 0.0
    }

    suspend fun getTotalTime(): Int = withContext(Dispatchers.IO) {
        return@withContext dao.getTotalTime() ?: 0
    }

    suspend fun getTotalBadge(): Int = withContext(Dispatchers.IO) {
        return@withContext dao.getTotalBadge() ?: 0
    }

    suspend fun getTotalTrash(): Int = withContext(Dispatchers.IO) {
        return@withContext dao.getTotalTrash() ?: 0
    }

    suspend fun getTrashKind(): Int = withContext(Dispatchers.IO) {
        return@withContext dao.getTrashKind() ?: 0
    }

    suspend fun getMonthlyDistance(from: Date, to: Date): Double = withContext(Dispatchers.IO) {
        return@withContext dao.getMonthlyDistance(from, to) ?: 0.0
    }

    suspend fun getMonthlyTime(from: Date, to: Date): Double = withContext(Dispatchers.IO) {
        return@withContext dao.getMonthlyTime(from, to) ?: 0.0
    }

    suspend fun getMonthlyTrash(from: Date, to: Date): Double = withContext(Dispatchers.IO) {
        return@withContext dao.getMonthlyTrash(from, to) ?: 0.0
    }

    suspend fun getMonthlyPlogging(
        from: Date,
        to: Date
    ): List<Plogging> = withContext(Dispatchers.IO) {

        return@withContext dao.getMonthlyPlogging(from, to) ?: listOf()
    }
}