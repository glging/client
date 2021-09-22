package dku.gyeongsotone.gulging.campusplogging.ui.main.plogging

import android.graphics.Bitmap
import android.util.Log
import androidx.databinding.ObservableDouble
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dku.gyeongsotone.gulging.campusplogging.data.local.dao.PloggingDao
import dku.gyeongsotone.gulging.campusplogging.data.local.model.Plogging
import dku.gyeongsotone.gulging.campusplogging.utils.Constant
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.UNIV_DISTANCE
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.floor
import kotlin.math.roundToInt

class MainPloggingViewModel(private val ploggingDao: PloggingDao) : ViewModel() {
    var totalDistance: Double = 0.0
    val level = ObservableInt()  // n학교
    val remainDistance = ObservableDouble() // km
    val progress = ObservableInt() // 진행 비율

    init {
        setPloggingProgress()
    }

    /**
     * DB에서 총 거리 가져와서 진행도 관련 데이터 처리
     */
    private fun setPloggingProgress() {
        viewModelScope.launch {
            totalDistance = ploggingDao.getTotalDistance() ?: 0.0

            val currentDistance: Double = totalDistance % UNIV_DISTANCE // 현재 레벨에서의 진행된 거리
            level.set(floor(totalDistance / UNIV_DISTANCE).toInt())
            progress.set((currentDistance / UNIV_DISTANCE * 100).roundToInt())
            remainDistance.set(UNIV_DISTANCE - currentDistance)
        }
    }
}