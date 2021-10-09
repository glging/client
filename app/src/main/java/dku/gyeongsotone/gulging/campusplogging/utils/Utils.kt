package dku.gyeongsotone.gulging.campusplogging.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import dku.gyeongsotone.gulging.campusplogging.CampusPloggingApplication
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

fun MutableLiveData<*>.notifyDataChanged() {
    this.value = this.value
}

fun getApplication(activity: Activity) =
    activity.application as CampusPloggingApplication

fun Long.msToMinute() = TimeUnit.MILLISECONDS.toMinutes(this)
fun Long.msToSecond() = TimeUnit.MILLISECONDS.toSeconds(this)
fun Double.mToKm() = this / 1000

fun Int.pxToDp() : Int = (this / Resources.getSystem().displayMetrics.density).toInt()
fun Int.dpToPx() : Int = (this * Resources.getSystem().displayMetrics.density).toInt()