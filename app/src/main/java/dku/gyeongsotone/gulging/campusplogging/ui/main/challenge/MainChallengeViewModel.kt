package dku.gyeongsotone.gulging.campusplogging.ui.main.challenge

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dku.gyeongsotone.gulging.campusplogging.data.local.model.Challenge
import dku.gyeongsotone.gulging.campusplogging.data.local.model.ChallengeStatus
import dku.gyeongsotone.gulging.campusplogging.data.local.model.ChallengeType
import dku.gyeongsotone.gulging.campusplogging.data.repository.PloggingRepository
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.CHALLENGE_FRESHMAN
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.CHALLENGE_SENIOR
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.CHALLENGE_SOPHOMORE_JUNIOR
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

    val freshmanChallenges = ObservableArrayList<Challenge>()
    val freshmanChallengeHide = ObservableBoolean(false)
    val sophomoreJuniorChallenges = ObservableArrayList<Challenge>()
    val sophomoreJuniorChallengeHide = ObservableBoolean(false)
    val seniorChallenges = ObservableArrayList<Challenge>()
    val seniorChallengeHide = ObservableBoolean(false)


    init {
        freshmanChallenges.addAll(CHALLENGE_FRESHMAN)
        sophomoreJuniorChallenges.addAll(CHALLENGE_SOPHOMORE_JUNIOR)
        seniorChallenges.addAll(CHALLENGE_SENIOR)

        updateChallengeStatuses()
    }

    fun updateChallengeStatuses() {
        viewModelScope.launch {
            val time = repository.getTotalTime()
            val level = floor(repository.getTotalDistance() / UNIV_DISTANCE).toInt()
            val trashKind = repository.getTrashKind()
            val totalTrash = repository.getTotalTrash()
            Log.d(TAG, "time: $time, level: $level, trashKind: $trashKind, totalTrash: $totalTrash")

            for (challenge in freshmanChallenges + sophomoreJuniorChallenges + seniorChallenges) {
                if (challenge.isAchieved(time, level, trashKind, totalTrash)) {
                    challenge.status = ChallengeStatus.DONE
                } else {
                    challenge.status = ChallengeStatus.NOW
                    break
                }
            }

            freshmanChallenges.notifyDatasetChanged()
            sophomoreJuniorChallenges.notifyDatasetChanged()
            seniorChallenges.notifyDatasetChanged()

            setSpInt(SP_TOTAL_TIME, time)
            setSpInt(SP_LEVEL, level)
            setSpInt(SP_TRASH_KIND, trashKind)
            setSpInt(SP_TOTAL_TRASH, totalTrash)
        }
    }
}

fun ObservableArrayList<Challenge>.notifyDatasetChanged() {
    this.add(Challenge(-1, ChallengeType.BY_GRADE, ChallengeStatus.BEFORE, ""))
    this.removeLast()
}