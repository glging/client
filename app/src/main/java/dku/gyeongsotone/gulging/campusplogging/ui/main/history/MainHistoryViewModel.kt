package dku.gyeongsotone.gulging.campusplogging.ui.main.history

import android.util.Log
import androidx.databinding.ObservableDouble
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dku.gyeongsotone.gulging.campusplogging.data.local.model.Plogging
import dku.gyeongsotone.gulging.campusplogging.data.local.model.User
import dku.gyeongsotone.gulging.campusplogging.data.repository.PloggingRepository
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.SP_TOTAL_BADGE
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.SP_TOTAL_DISTANCE
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.SP_TOTAL_TRASH
import dku.gyeongsotone.gulging.campusplogging.utils.PreferenceUtil.getSpDouble
import dku.gyeongsotone.gulging.campusplogging.utils.PreferenceUtil.getSpInt
import dku.gyeongsotone.gulging.campusplogging.utils.PreferenceUtil.setSpDouble
import dku.gyeongsotone.gulging.campusplogging.utils.PreferenceUtil.setSpInt
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.floor

class MainHistoryViewModel(val user: User) : ViewModel() {
    companion object {
        private val TAG = this::class.java.name
    }

    private val repository = PloggingRepository

    val totalDistance = ObservableDouble(getSpDouble(SP_TOTAL_DISTANCE))
    val totalTrash = ObservableInt(getSpInt(SP_TOTAL_TRASH))
    val totalBadge = ObservableInt(getSpInt(SP_TOTAL_BADGE))

    val monthlyDistance = ObservableInt()
    val monthlyTime = ObservableInt()
    val monthlyTrash = ObservableInt()
    val monthlyPlogging = ObservableField<List<Plogging>>()

    private val calendar = Calendar.getInstance()
    val year = ObservableInt(calendar.get(Calendar.YEAR))
    val month = ObservableInt(calendar.get(Calendar.MONTH) + 1)


    init {
        updateData()
    }

    fun updateData() {
        viewModelScope.launch {
            setTotalData()
            setMonthlyData()
        }
    }

    private suspend fun setTotalData() {
        val lTotalDistance: Double = repository.getTotalDistance()
        val lTotalTrash: Int = repository.getTotalTrash()
        val lTotalBadge: Int = repository.getTotalBadge()

        setSpDouble(SP_TOTAL_DISTANCE, lTotalDistance)
        setSpInt(SP_TOTAL_TRASH, lTotalTrash)
        setSpInt(SP_TOTAL_BADGE, lTotalBadge)

        totalDistance.set(lTotalDistance)
        totalTrash.set(lTotalTrash)
        totalBadge.set(lTotalBadge)
    }

    private suspend fun setMonthlyData() {
        Log.d(TAG, "setMonthlyData() called!! month: ${month.get()}")
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val from = calendar.time

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DATE))
        val to = calendar.time

        val lMonthlyDistance = floor(repository.getMonthlyDistance(from, to)).toInt()
        val lMonthlyTime = floor(repository.getMonthlyTime(from, to)).toInt()
        val lMonthlyTrash = floor(repository.getMonthlyTrash(from, to)).toInt()
        val lMonthlyPlogging = repository.getMonthlyPlogging(from, to)

        Log.d(TAG, "monthly plogging: $lMonthlyPlogging")

        monthlyPlogging.set(lMonthlyPlogging)
        monthlyDistance.set(lMonthlyDistance)
        monthlyTime.set(lMonthlyTime)
        monthlyTrash.set(lMonthlyTrash)
    }

    fun setPreMonth() {
        calendar.add(Calendar.MONTH, -1)
        year.set(calendar.get(Calendar.YEAR))
        month.set(calendar.get(Calendar.MONTH) + 1)

        Log.d(TAG, "viewModelScope: $viewModelScope")
        viewModelScope.launch {
            setMonthlyData()
        }
    }


    fun setNextMonth() {
        calendar.add(Calendar.MONTH, 1)
        year.set(calendar.get(Calendar.YEAR))
        month.set(calendar.get(Calendar.MONTH) + 1)

        Log.d(TAG, "viewModelScope: $viewModelScope")
        viewModelScope.launch {
            setMonthlyData()
        }
    }
}