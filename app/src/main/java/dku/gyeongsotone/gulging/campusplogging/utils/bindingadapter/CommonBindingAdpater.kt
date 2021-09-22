package dku.gyeongsotone.gulging.campusplogging.utils.bindingadapter

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 * 공통적인 속성과 관련된 binding adpater
 *
 * */

@BindingAdapter("android:visibility")
fun View.setVisibility(visibility: Boolean) {
    isVisible = visibility
}

@BindingAdapter("android:src")
fun ImageView.setSrc(bitmap: Bitmap?) {
    if (bitmap != null)
        setImageBitmap(bitmap)
}