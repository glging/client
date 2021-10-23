package dku.gyeongsotone.gulging.campusplogging.data.repository

import androidx.room.Query
import dku.gyeongsotone.gulging.campusplogging.data.local.dao.PloggingDao
import dku.gyeongsotone.gulging.campusplogging.data.local.model.Plogging
import java.util.*

object PloggingRepository {
    lateinit var dao: PloggingDao

    fun initPloggingRepository(ploggingDao: PloggingDao) {
        dao = ploggingDao
    }

    suspend fun insert(item: Plogging) {
        dao.insert(item)
    }

    suspend fun update(item: Plogging) {
        dao.update(item)
    }

    suspend fun delete(item: Plogging) {
        dao.delete(item)
    }

    suspend fun getPlogging(id: Int): Plogging? {
        return dao.getPlogging(id)
    }

    suspend fun getTotalDistance(): Double {
        return dao.getTotalDistance() ?: 0.0
    }

    suspend fun getTotalTime(): Int {
        return dao.getTotalTime() ?: 0
    }

    suspend fun getTotalBadge(): Int {
        return dao.getTotalBadge() ?: 0
    }

    suspend fun getTotalTrash(): Int {
        return dao.getTotalTrash() ?: 0
    }

    suspend fun getMonthlyDistance(from: Date, to: Date): Double {
        return dao.getMonthlyDistance(from, to) ?: 0.0
    }

    suspend fun getMonthlyTime(from: Date, to: Date): Double {
        return dao.getMonthlyTime(from, to) ?: 0.0
    }

    suspend fun getMonthlyTrash(from: Date, to: Date): Double {
        return dao.getMonthlyTrash(from, to) ?: 0.0
    }

    suspend fun getMonthlyPlogging(from: Date, to: Date): ArrayList<Plogging> {
        val arrayList = arrayListOf<Plogging>()
        arrayList.addAll(dao.getMonthlyPlogging(from, to) ?: arrayOf())

        return arrayList
    }
}