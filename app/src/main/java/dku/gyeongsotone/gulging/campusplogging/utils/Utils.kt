package dku.gyeongsotone.gulging.campusplogging.utils

import androidx.lifecycle.MutableLiveData
import java.util.concurrent.TimeUnit

fun MutableLiveData<*>.notifyDataChanged() {
    this.value = this.value
}

fun Long.msToMinute() = TimeUnit.MILLISECONDS.toMinutes(this)

fun Double.mToKm() = this / 1000