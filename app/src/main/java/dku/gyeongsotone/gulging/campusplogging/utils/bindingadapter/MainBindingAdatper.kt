package dku.gyeongsotone.gulging.campusplogging.utils.bindingadapter

import android.graphics.Paint
import android.os.Build
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.BulletSpan
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import dku.gyeongsotone.gulging.campusplogging.R
import dku.gyeongsotone.gulging.campusplogging.data.local.model.Challenge
import dku.gyeongsotone.gulging.campusplogging.data.local.model.ChallengeStatus
import dku.gyeongsotone.gulging.campusplogging.data.local.model.ChallengeType
import dku.gyeongsotone.gulging.campusplogging.data.local.model.Plogging
import dku.gyeongsotone.gulging.campusplogging.ui.main.challenge.ChallengeSmallAdapter
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

@BindingAdapter("challenge")
fun RecyclerView.setChallenge(items: List<Challenge>) {
    if (items[0].type == ChallengeType.BY_GRADE) {
        if (adapter == null) {
            adapter = ChallengeSmallAdapter()
        }

        (adapter as ChallengeSmallAdapter).replaceAll(items)
        adapter!!.notifyDataSetChanged()
    }
}

@BindingAdapter("challengeStatus")
fun ViewGroup.setChallengeBackground(status: ChallengeStatus) {
    when (status) {
        ChallengeStatus.BEFORE -> setBackgroundResource(R.drawable.background_item_challenge_before)
        ChallengeStatus.NOW -> setBackgroundResource(R.drawable.background_item_challenge_now)
        ChallengeStatus.DONE -> setBackgroundResource(R.drawable.background_item_challenge_done)
    }
}

@BindingAdapter(value = ["challengeStatus", "challengeAchieved"], requireAll = false)
fun TextView.setTextStyle(status: ChallengeStatus, challengeAchieved: Boolean?) {
    Log.d(TAG, "text: $text, status: $status, achieved: $challengeAchieved")

    var bulletColor: Int = ContextCompat.getColor(context, R.color.black)
    if (status == ChallengeStatus.DONE) {
        bulletColor = ContextCompat.getColor(context, R.color.white)
        setTextColor(bulletColor)
    } else if (status == ChallengeStatus.NOW && challengeAchieved == true) {
        paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }

    if (challengeAchieved != null && Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
        text = SpannableStringBuilder(text.toString()).apply {
            setSpan(
                BulletSpan(24, bulletColor, 6),
                0, text.length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
        }
    }
}


