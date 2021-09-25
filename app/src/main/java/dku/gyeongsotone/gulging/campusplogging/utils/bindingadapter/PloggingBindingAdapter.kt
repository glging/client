package dku.gyeongsotone.gulging.campusplogging.utils.bindingadapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import dku.gyeongsotone.gulging.campusplogging.R
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.UNIV_DISTANCE

@BindingAdapter("ploggingDistance")
fun TextView.setPloggingDistance(distanceInKm: Double) {
    val level = distanceInKm / UNIV_DISTANCE
    text = resources.getString(R.string.plogging_distance, level, distanceInKm)
}

@BindingAdapter("ploggingTime")
fun TextView.setPloggingTime(timeInMin: Int) {
    val hour = timeInMin / 60
    val minute = timeInMin % 60

    text = resources.getString(R.string.plogging_time, hour, minute)
}

