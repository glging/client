package dku.gyeongsotone.gulging.campusplogging.utils.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import dku.gyeongsotone.gulging.campusplogging.data.local.model.Plogging
import dku.gyeongsotone.gulging.campusplogging.ui.main.history.PloggingHistoryAdapter

/**
 * MainActivity와 관련된 binding adpater
 *
 */

private val TAG = "MainBindingAdapter"

@BindingAdapter("ploggingHistory")
fun RecyclerView.setPloggingHistory(items: ArrayList<Plogging>) {
    if (adapter == null) {
        adapter = PloggingHistoryAdapter()
    }

    (adapter as PloggingHistoryAdapter).replaceAll(items)
    adapter!!.notifyDataSetChanged()
}