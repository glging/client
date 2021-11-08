package dku.gyeongsotone.gulging.campusplogging.data.local.model

import com.squareup.moshi.Json
import dku.gyeongsotone.gulging.campusplogging.data.network.RankingUserProperty

data class RankingInfo(
    val allUserCount: Int,
    val allBadgeCount: Int,
    val ranking: List<RankingUser>,
    val myRanking: RankingUser
)
