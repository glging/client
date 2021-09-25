package dku.gyeongsotone.gulging.campusplogging.utils

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

fun MutableLiveData<*>.notifyDataChanged() {
    this.value = this.value
}

fun Long.msToMinute() = TimeUnit.MILLISECONDS.toMinutes(this)
fun Double.mToKm() = this / 1000

fun Int.pxToDp() : Int = (this / Resources.getSystem().displayMetrics.density).toInt()
fun Int.dpToPx() : Int = (this * Resources.getSystem().displayMetrics.density).toInt()