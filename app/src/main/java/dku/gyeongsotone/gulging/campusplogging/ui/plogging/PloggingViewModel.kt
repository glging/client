package dku.gyeongsotone.gulging.campusplogging.ui.plogging

import androidx.databinding.ObservableDouble
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PloggingViewModel : ViewModel() {
    // 모든 데이터는 현재 플로깅 기준 (누적X, 모두 0부터 시작)
    val distance = ObservableDouble(0.0)
    val time = ObservableInt(0)
    val level = ObservableInt(0)
    val progress = ObservableInt(0)

    // 플로깅 상태
    private val _ploggingStatus = MutableLiveData<PloggingStatus>(PloggingStatus.START_OR_RESUME)
    val ploggingStatus: LiveData<PloggingStatus> = _ploggingStatus


    // 쓰레기 개수
    val plastics = ObservableInt(0)
    val vinyls = ObservableInt(0)
    val glasses = ObservableInt(0)
    val cans = ObservableInt(0)
    val pagers = ObservableInt(0)
    val generals = ObservableInt(0)


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