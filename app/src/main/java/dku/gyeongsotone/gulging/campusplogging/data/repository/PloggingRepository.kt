package dku.gyeongsotone.gulging.campusplogging.data.repository

import dku.gyeongsotone.gulging.campusplogging.data.local.dao.PloggingDao
import dku.gyeongsotone.gulging.campusplogging.data.local.model.Plogging

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

    suspend fun getTotalDistance(): Double? =
        dao.getTotalDistance()

    suspend fun getTotalTime(): Int? =
        dao.getTotalTime()

    suspend fun getTotalBadge(): Int? =
        dao.getTotalBadge()
}