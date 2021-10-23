package dku.gyeongsotone.gulging.campusplogging.utils.bindingadapter

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

/**
 * 공통적인 속성과 관련된 binding adpater
 *
 */

private val TAG = "CommonBindingAdapter"

@BindingAdapter("android:visibility")
fun View.setVisibility(visibility: Boolean) {
    isVisible = visibility
}

@BindingAdapter("android:src")
fun ImageView.setSrc(bitmap: Bitmap?) {
    if (bitmap == null) return

    Glide.with(this).load(bitmap).into(this)
}

@BindingAdapter("android:src")
fun ImageView.setSrc(byteArray: ByteArray?) {
    if (byteArray == null) return

    Glide.with(this).load(byteArray).into(this)

}

@BindingAdapter("date")
fun TextView.setDate(date: Date?) {
    if (date == null) return

    val dateFormat = SimpleDateFormat("MM월 dd일 EE", Locale.getDefault())
    text = dateFormat.format(date)
}
