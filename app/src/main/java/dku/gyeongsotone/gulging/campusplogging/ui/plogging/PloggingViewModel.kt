package dku.gyeongsotone.gulging.campusplogging.ui.plogging

import android.graphics.Bitmap
import androidx.databinding.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dku.gyeongsotone.gulging.campusplogging.utils.Constant
import kotlin.math.floor
import kotlin.math.roundToInt

class PloggingViewModel : ViewModel() {
    // 모든 데이터는 현재 플로깅 기준 (누적X, 모두 0부터 시작)
    val distance = ObservableDouble(0.0)
    val time = ObservableInt(0)
    val level = ObservableInt(0)
    val progress = ObservableInt(0)
    val badge = ObservableInt(0)

    // 플로깅 상태
    private val _ploggingStatus = MutableLiveData<PloggingStatus>(PloggingStatus.START_OR_RESUME)
    val ploggingStatus: LiveData<PloggingStatus> = _ploggingStatus

    // 날짜 (시간 포함)
    val startDate = ObservableLong(System.currentTimeMillis())
    val endDate = ObservableLong()

    // 쓰레기 개수
    val plastics = ObservableInt(0)
    val vinyls = ObservableInt(0)
    val glasses = ObservableInt(0)
    val cans = ObservableInt(0)
    val papers = ObservableInt(0)
    val generals = ObservableInt(0)

    // 사진
    var picture: Bitmap? = null

    /** distance 갱신하고 그에 따라서 leve, progress도 갱신 */
    fun updateDistance(data: Double) {
        distance.set(data)
        data % Constant.UNIV_DISTANCE // 현재 레벨에서의 진행된 거리
        level.set(floor(data / Constant.UNIV_DISTANCE).toInt())
        progress.set((data / Constant.UNIV_DISTANCE * 100).roundToInt() % 100)
    }


    /** pause, resume, stop 버튼 클릭 */
    fun onClickPauseBtn() {
        _ploggingStatus.value = PloggingStatus.PAUSE
    }

    fun onClickResumeBtn() {
        _ploggingStatus.value = PloggingStatus.START_OR_RESUME
    }

    fun onClickStopBtn() {
        _ploggingStatus.value = PloggingStatus.STOP
    }
}

enum class PloggingStatus {
    START_OR_RESUME, PAUSE, STOP
}