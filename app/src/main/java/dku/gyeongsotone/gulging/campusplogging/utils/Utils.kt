package dku.gyeongsotone.gulging.campusplogging.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import dku.gyeongsotone.gulging.campusplogging.APP
import java.util.*
import java.util.concurrent.TimeUnit

fun MutableLiveData<*>.notifyDataChanged() {
    this.value = this.value
}

fun getApplication(activity: Activity) =
    activity.application as APP

fun Long.msToMinute() = TimeUnit.MILLISECONDS.toMinutes(this)
fun Long.msToSecond() = TimeUnit.MILLISECONDS.toSeconds(this)
fun Double.mToKm() = this / 1000

fun Int.addTimeUnit(): String =
    if (this >= 60) "${this / 60}시간" else "${this}분"


fun Int.pxToDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()
fun Int.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun getCurrentDate(): Date = Date(System.currentTimeMillis())

fun showToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

