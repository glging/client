package dku.gyeongsotone.gulging.campusplogging.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import dku.gyeongsotone.gulging.campusplogging.R
import dku.gyeongsotone.gulging.campusplogging.databinding.ViewTrashCountBinding

class TrashCountView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    val binding: ViewTrashCountBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.view_trash_count,
        this,
        true
    )
}