package dku.gyeongsotone.gulging.campusplogging.ui.main.history

import androidx.databinding.ObservableArrayList
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.math.floor

class MainHistoryViewModel(val user: User) : ViewModel() {
    private val repository = PloggingRepository

    val totalDistance = ObservableDouble(getSpDouble(SP_TOTAL_DISTANCE))
    val totalTrash = ObservableInt(getSpInt(SP_TOTAL_TRASH))
    val totalBadge = ObservableInt(getSpInt(SP_TOTAL_BADGE))

    val monthlyDistance = ObservableInt()
    val monthlyTime = ObservableInt()
    val monthlyTrash = ObservableInt()
    val monthlyPlogging = ObservableArrayList<Plogging>()

    private val calendar = Calendar.getInstance()
    val year = ObservableInt(calendar.get(Calendar.YEAR))
    val month = ObservableInt(calendar.get(Calendar.MONTH))


    init {
        viewModelScope.launch(Dispatchers.IO) {
            setTotalData()
            setMonthlyData()
        }
    }

    private suspend fun setTotalData() {
        val totalDistance: Double = repository.getTotalDistance()
        val totalTrash: Int = repository.getTotalTrash()
        val totalBadge: Int = repository.getTotalBadge()

        setSpDouble(SP_TOTAL_DISTANCE, totalDistance)
        setSpInt(SP_TOTAL_TRASH, totalTrash)
        setSpInt(SP_TOTAL_BADGE, totalBadge)

        withContext(Dispatchers.Main) {
            this@MainHistoryViewModel.totalDistance.set(totalDistance)
            this@MainHistoryViewModel.totalTrash.set(totalTrash)
            this@MainHistoryViewModel.totalBadge.set(totalBadge)
        }
    }

    private suspend fun setMonthlyData() {
        this.monthlyPlogging.clear()

        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val from = calendar.time

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DATE))
        val to = calendar.time

        val monthlyDistance = floor(repository.getMonthlyDistance(from, to)).toInt()
        val monthlyTime = floor(repository.getMonthlyTime(from, to)).toInt()
        val monthlyTrash = floor(repository.getMonthlyTrash(from, to)).toInt()
        val monthlyPlogging = repository.getMonthlyPlogging(from, to)

        withContext(Dispatchers.Main) {
            this@MainHistoryViewModel.monthlyDistance.set(monthlyDistance)
            this@MainHistoryViewModel.monthlyTime.set(monthlyTime)
            this@MainHistoryViewModel.monthlyTrash.set(monthlyTrash)
            this@MainHistoryViewModel.monthlyPlogging.addAll(monthlyPlogging)
        }
    }

    fun setPreMonth() {
        calendar.add(Calendar.MONTH, -1)
        year.set(calendar.get(Calendar.YEAR))
        month.set(calendar.get(Calendar.MONTH))

        viewModelScope.launch(Dispatchers.IO) {
            setMonthlyData()
        }
    }

    fun setNextMonth() {
        calendar.add(Calendar.MONTH, 1)
        year.set(calendar.get(Calendar.YEAR))
        month.set(calendar.get(Calendar.MONTH))


        viewModelScope.launch(Dispatchers.IO) {
            setMonthlyData()
        }
    }
}