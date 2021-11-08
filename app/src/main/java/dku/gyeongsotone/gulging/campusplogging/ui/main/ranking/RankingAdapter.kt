package dku.gyeongsotone.gulging.campusplogging.ui.main.ranking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dku.gyeongsotone.gulging.campusplogging.R
import dku.gyeongsotone.gulging.campusplogging.data.local.model.RankingUser
import dku.gyeongsotone.gulging.campusplogging.databinding.ItemRankingBinding
import dku.gyeongsotone.gulging.campusplogging.utils.bindingadapter.setRanking

class RankingAdapter : RecyclerView.Adapter<RankingAdapter.ViewHolder>() {
    private val items = mutableListOf<RankingUser>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])

        // TOP3는 겉에 테두리
        if (position < 3) {
            holder.binding.container.setBackgroundResource(R.drawable.background_item_ranking_top)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun replaceAll(items: List<RankingUser>) {
        this.items.clear()
        this.items.addAll(items)
    }

    class ViewHolder private constructor(val binding: ItemRankingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RankingUser) {
            binding.item = item
            binding.isMyRanking = false
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemRankingBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}