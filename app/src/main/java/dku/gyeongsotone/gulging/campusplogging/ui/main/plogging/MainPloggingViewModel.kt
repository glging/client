package dku.gyeongsotone.gulging.campusplogging.ui.main.plogging

import androidx.databinding.ObservableDouble
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dku.gyeongsotone.gulging.campusplogging.data.repository.PloggingRepository
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.CHALLENGE_LIST
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.SP_CHALLENGE_ID
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.SP_LEVEL
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.SP_PROGRESS
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.SP_REMAIN_DISTANCE
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.SP_TOTAL_DISTANCE
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.UNIV_DISTANCE
import dku.gyeongsotone.gulging.campusplogging.utils.PreferenceUtil.getSpDouble
import dku.gyeongsotone.gulging.campusplogging.utils.PreferenceUtil.getSpInt
import dku.gyeongsotone.gulging.campusplogging.utils.PreferenceUtil.setSpDouble
import dku.gyeongsotone.gulging.campusplogging.utils.PreferenceUtil.setSpInt
import kotlinx.coroutines.launch
import kotlin.math.floor
import kotlin.math.roundToInt

class MainPloggingViewModel : ViewModel() {
    private val repository = PloggingRepository

    val level = ObservableInt(getSpInt(SP_LEVEL))  // n학교
    val remainDistance = ObservableDouble(getSpDouble(SP_REMAIN_DISTANCE)) // km
    val progress = ObservableInt(getSpInt(SP_PROGRESS)) // 진행 비율

    val challengeName = ObservableField<String>()  // 진행중인 챌린지 제목
    val challengeContent = ObservableField<String>()  // 진행중인 챌린지 내용

    init {
        updateData()
    }

    /**
     * 화면의 데이터 갱신
     */
    fun updateData() {
        updateChallenge()
        updatePloggingProgress()

    }

    /**
     * 챌린지 갱신
     */
    private fun updateChallenge() {
        val challenge = CHALLENGE_LIST[getSpInt(SP_CHALLENGE_ID)]
        challengeName.set(challenge.name)
        challengeContent.set(challenge.getFullContent())
    }

    /**
     * 거리 관련 플로깅 진행도 갱신
     *
     * */
    private fun updatePloggingProgress() {
        viewModelScope.launch {
            val totalDistance: Double = repository.getTotalDistance()
            val curDistance: Double = totalDistance % UNIV_DISTANCE // 현재 레벨에서의 진행된 거리
            val curLevel: Int = floor(totalDistance / UNIV_DISTANCE).toInt()
            val curProgress: Int = (curDistance / UNIV_DISTANCE * 100).roundToInt() % 100
            val curRemainDistance: Double = UNIV_DISTANCE - curDistance

            level.set(curLevel)
            progress.set(curProgress)
            remainDistance.set(curRemainDistance)

            setSpDouble(SP_TOTAL_DISTANCE, totalDistance)
            setSpInt(SP_LEVEL, curLevel)
            setSpInt(SP_PROGRESS, curProgress)
            setSpDouble(SP_REMAIN_DISTANCE, curRemainDistance)
        }
    }
}