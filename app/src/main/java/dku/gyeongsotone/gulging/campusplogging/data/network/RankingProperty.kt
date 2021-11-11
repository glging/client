package dku.gyeongsotone.gulging.campusplogging.data.network

import com.squareup.moshi.Json
import com.squareup.picasso.Picasso
import dku.gyeongsotone.gulging.campusplogging.data.local.model.RankingInfo
import dku.gyeongsotone.gulging.campusplogging.data.local.model.RankingUser

// [랭킹 불러오기] 랭킹 조회 응답
data class GetRankingResponse(
    @Json(name = "success")
    val success: Boolean = false,

    @Json(name = "all_user_count")
    val allUserCount: Int = -1,

    @Json(name = "all_badge_count")
    val allBadgeCount: Int = -1,

    @Json(name = "ranking")
    val ranking: List<RankingUserProperty> = listOf(),

    @Json(name = "my_ranking")
    val myRanking: Int = -1,

    @Json(name = "my_profile")
    val myProfile: String = "",

    @Json(name = "my_nickname")
    val myNickname: String = "",

    @Json(name = "my_badge")
    val myBadge: Int = -1,

    @Json(name = "description")
    val description: String = ""
)

fun GetRankingResponse.toRankingInfo() = RankingInfo(
    allUserCount = allUserCount,
    allBadgeCount = allBadgeCount,
    ranking = ranking.map { it.toRankingUser() },
    myRanking = RankingUser(
        badge = myBadge,
        profileImage = Picasso.get().load(myProfile).get(),
        ranking = myRanking,
        nickname = myNickname
    )
)

// [랭킹 불러오기] 랭킹 유저 프로퍼티
data class RankingUserProperty(
    @Json(name = "badge")
    val badge: Int,

    @Json(name = "ranking")
    val ranking: Int,

    @Json(name = "nickname")
    val nickname: String,

    @Json(name = "profile_image")
    val profileImage: String,
)

fun RankingUserProperty.toRankingUser() = RankingUser(
    badge = badge,
    ranking = ranking,
    nickname = nickname,
    profileImage = Picasso.get().load(profileImage).get()
)
