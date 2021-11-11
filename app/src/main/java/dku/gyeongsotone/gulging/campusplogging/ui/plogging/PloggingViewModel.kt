package dku.gyeongsotone.gulging.campusplogging.ui.plogging

import android.graphics.Bitmap
import android.util.Log
import androidx.databinding.ObservableDouble
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dku.gyeongsotone.gulging.campusplogging.data.local.model.Plogging
import dku.gyeongsotone.gulging.campusplogging.data.repository.CamploRepository
import dku.gyeongsotone.gulging.campusplogging.data.repository.PloggingRepository
import dku.gyeongsotone.gulging.campusplogging.data.repository.Result
import dku.gyeongsotone.gulging.campusplogging.utils.Constant
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.SP_TOKEN
import dku.gyeongsotone.gulging.campusplogging.utils.PreferenceUtil
import dku.gyeongsotone.gulging.campusplogging.utils.PreferenceUtil.getSpString
import dku.gyeongsotone.gulging.campusplogging.utils.getCurrentDate
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.floor
import kotlin.math.roundToInt

class PloggingViewModel : ViewModel() {
    companion object {
        private val TAG = this::class.java.name
    }

    private val repository = PloggingRepository

    // 모든 데이터는 현재 플로깅 기준 (누적X, 모두 0부터 시작)
    val distance = ObservableDouble(0.0)
    val time = ObservableInt(0)
    val level = ObservableInt(0)
    val progress = ObservableInt(0)
    val badge = ObservableInt(0)

    // 플로깅 상태
    private val _ploggingStatus = MutableLiveData<PloggingStatus>()
    val ploggingStatus: LiveData<PloggingStatus> = _ploggingStatus

    // 날짜 (시간 포함)
    val startDate = ObservableField<Date>(getCurrentDate())
    val endDate = ObservableField<Date>()

    // 쓰레기 개수
    val plastics = ObservableInt(0)
    val vinyls = ObservableInt(0)
    val glasses = ObservableInt(0)
    val cans = ObservableInt(0)
    val papers = ObservableInt(0)
    val generals = ObservableInt(0)

    // 사진
    var picture: Bitmap? = null

    // 플로깅 -> 플로깅이 끝난 뒤 생성 (in PloggingFinishFragment)
    val plogging = ObservableField<Plogging>()

    // 토스트 메시지
    private val _toastMsg = MutableLiveData<String>()
    val toastMsg: LiveData<String> = _toastMsg

    /** distance 갱신하고 그에 따라서 level, progress도 갱신 */
    fun updateDistance(data: Double) {
        distance.set(data)
        data % Constant.UNIV_DISTANCE // 현재 레벨에서의 진행된 거리
        level.set(floor(data / Constant.UNIV_DISTANCE).toInt())
        progress.set((data / Constant.UNIV_DISTANCE * 100).roundToInt() % 100)

        Log.d(TAG, "distance: ${distance.get()}")
    }

    fun startPlogging() {
        _ploggingStatus.value = PloggingStatus.START_OR_RESUME
    }

    fun pausePlogging() {
        _ploggingStatus.value = PloggingStatus.PAUSE
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

    fun setBadges() {
        val preTotalDistance = PreferenceUtil.getSpDouble(Constant.SP_TOTAL_DISTANCE)
        val curTotalDistance = preTotalDistance + distance.get()

        val preLevel = PreferenceUtil.getSpInt(Constant.SP_LEVEL)
        val curLevel = floor(curTotalDistance / Constant.UNIV_DISTANCE).toInt()

        badge.set(curLevel - preLevel)
    }


    /**
     * save
     */
    fun savePloggingData() = viewModelScope.launch {
        val lPlogging = Plogging(
            startDate = startDate.get()!!,
            endDate = endDate.get()!!,
            distance = distance.get(),
            time = time.get(),
            badge = badge.get(),
            picture = picture!!,
            plastic = plastics.get(),
            vinyl = vinyls.get(),
            glass = glasses.get(),
            can = cans.get(),
            paper = papers.get(),
            general = generals.get()
        )

        plogging.set(lPlogging)
        joinAll(saveOnDatabase())   // , backUpPlogging()
    }

    /** 플로깅 기록을 DB에 저장 */
    private fun saveOnDatabase() = viewModelScope.launch {
        repository.insert(plogging.get()!!)
    }

    /**
     * 플로깅 기록을 서버에 백업
     */
//    private fun backUpPlogging() = viewModelScope.launch {
//        val token = getSpString(SP_TOKEN)!!
//        val response = CamploRepository.backUpPlogging(token, plogging.get()!!)
//
//        if (response is Result.Error) {
//            _toastMsg.value = response.message
//        }
//    }
}

enum class PloggingStatus {
    START_OR_RESUME, PAUSE, STOP
}