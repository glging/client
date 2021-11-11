package dku.gyeongsotone.gulging.campusplogging.ui.main.ranking

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dku.gyeongsotone.gulging.campusplogging.data.local.model.RankingInfo
import dku.gyeongsotone.gulging.campusplogging.data.local.model.RankingUser
import dku.gyeongsotone.gulging.campusplogging.data.repository.CamploRepository
import dku.gyeongsotone.gulging.campusplogging.data.repository.Result
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.SP_TOKEN
import dku.gyeongsotone.gulging.campusplogging.utils.PreferenceUtil.getSpString
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class MainRankingViewModel : ViewModel() {
    private val repository = CamploRepository

    val allUserCount = ObservableInt()
    val allBadgeCount = ObservableInt()
    val ranking = ObservableField<List<RankingUser>>()
    val myRanking = ObservableField<RankingUser>()

    // 토스트 메시지
    private val _toastMsg = MutableLiveData<String>()
    val toastMsg: LiveData<String> = _toastMsg

    init {
        updateData()
    }


    /**
     * 데이터 갱신
     */
    fun updateData(): Job = viewModelScope.launch {
        val updateRankingJob = updateRankingInfo()

        joinAll(updateRankingJob)
    }

    /**
     * 랭킹 정보 업데이트
     */
    private fun updateRankingInfo() = viewModelScope.launch {
        val token = getSpString(SP_TOKEN)!!
        val response = repository.getRanking(token)

        // 오류가 발생했을 경우, 에러 메시지 띄운 후 리턴
        if (response is Result.Error) {
            _toastMsg.value = response.message
            return@launch
        }

        val rankingInfo = (response as Result.Success<RankingInfo>).data
        allUserCount.set(rankingInfo.allUserCount)
        allBadgeCount.set(rankingInfo.allBadgeCount)
        ranking.set(rankingInfo.ranking)
        myRanking.set(rankingInfo.myRanking)
    }
}