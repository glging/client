package dku.gyeongsotone.gulging.campusplogging.ui.main.challenge

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dku.gyeongsotone.gulging.campusplogging.data.local.model.Challenge
import dku.gyeongsotone.gulging.campusplogging.data.local.model.ChallengeStatus
import dku.gyeongsotone.gulging.campusplogging.data.repository.PloggingRepository
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.CHALLENGE_LIST
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.SP_CHALLENGE_ID
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.SP_LEVEL
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.SP_TOTAL_TIME
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.SP_TOTAL_TRASH
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.SP_TRASH_KIND
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.UNIV_DISTANCE
import dku.gyeongsotone.gulging.campusplogging.utils.PreferenceUtil.getSpInt
import dku.gyeongsotone.gulging.campusplogging.utils.PreferenceUtil.setSpInt
import kotlinx.coroutines.launch
import kotlin.math.floor

class MainChallengeViewModel : ViewModel() {
    companion object {
        private val TAG = this::class.java.name
    }

    private val repository = PloggingRepository

    val time: Int = getSpInt(SP_TOTAL_TIME)
    val level: Int = getSpInt(SP_LEVEL)
    val trashKind: Int = getSpInt(SP_TRASH_KIND)
    val totalTrash: Int = getSpInt(SP_TOTAL_TRASH)

    val freshmanChallenges = ObservableField<List<Challenge>>()
    val sophomoreJuniorChallenges = ObservableField<List<Challenge>>()
    val seniorChallenges = ObservableField<List<Challenge>>()
    val graduationChallenge = ObservableField<Challenge>()

    private val allChallenge: List<Challenge>
        get() = freshmanChallenges.get()!! + sophomoreJuniorChallenges.get()!! + seniorChallenges.get()!! + graduationChallenge.get()!!

    /**
     * 학년 별 챌린지 설정
     */
    init {
        freshmanChallenges.set(CHALLENGE_LIST.subList(0, 2))
        sophomoreJuniorChallenges.set(CHALLENGE_LIST.subList(2, 6))
        seniorChallenges.set(CHALLENGE_LIST.subList(6, 8))
        graduationChallenge.set(CHALLENGE_LIST[8])

        updateChallengeStatuses()
    }

    /**
     * 챌린지 달성 여부 업데이트
     */
    fun updateChallengeStatuses() = viewModelScope.launch {
        val time = repository.getTotalTime()
        val level = floor(repository.getTotalDistance() / UNIV_DISTANCE).toInt()
        val trashKind = repository.getTrashKind()
        val totalTrash = repository.getTotalTrash()
        Log.d(TAG, "time: $time, level: $level, trashKind: $trashKind, totalTrash: $totalTrash")

        for (challenge in allChallenge) {
            Log.d(TAG, "current challenge: ${challenge.name}")
            if (challenge.isAchieved(time, level, trashKind, totalTrash)) {
                challenge.status = ChallengeStatus.DONE
            } else {
                challenge.status = ChallengeStatus.NOW
                setSpInt(SP_CHALLENGE_ID, challenge.id)
                break
            }
        }

        freshmanChallenges.notifyChange()
        sophomoreJuniorChallenges.notifyChange()
        seniorChallenges.notifyChange()
        graduationChallenge.notifyChange()

        setSpInt(SP_TOTAL_TIME, time)
        setSpInt(SP_LEVEL, level)
        setSpInt(SP_TRASH_KIND, trashKind)
        setSpInt(SP_TOTAL_TRASH, totalTrash)
    }

}