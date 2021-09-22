package dku.gyeongsotone.gulging.campusplogging.ui.main.plogging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dku.gyeongsotone.gulging.campusplogging.data.local.dao.PloggingDao

class MainPloggingViewModelFactory(private val ploggingDao: PloggingDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainPloggingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainPloggingViewModel(ploggingDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}