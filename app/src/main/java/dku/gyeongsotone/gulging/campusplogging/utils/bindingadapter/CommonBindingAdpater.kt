package dku.gyeongsotone.gulging.campusplogging.utils.bindingadapter

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

/**
 * 공통적인 속성과 관련된 binding adpater
 *
 * */

@BindingAdapter("android:visibility")
fun View.setVisibility(visibility: Boolean) {
    isVisible = visibility
}