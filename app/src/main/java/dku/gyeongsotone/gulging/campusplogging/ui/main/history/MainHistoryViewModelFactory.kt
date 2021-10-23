package dku.gyeongsotone.gulging.campusplogging.ui.main.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dku.gyeongsotone.gulging.campusplogging.data.local.model.User

class MainHistoryViewModelFactory(private val user: User) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainHistoryViewModel::class.java)) {

            @Suppress("UNCHECKED_CAST")
            return MainHistoryViewModel(user) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

