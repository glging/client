package dku.gyeongsotone.gulging.campusplogging.data.local.model

import android.graphics.Bitmap

data class RankingUser(
    val badge: Int,
    val profileImage: Bitmap,
    val nickname: String,
    val ranking: Int,
)
