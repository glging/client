package dku.gyeongsotone.gulging.campusplogging.data.network.response

import com.squareup.moshi.Json
import dku.gyeongsotone.gulging.campusplogging.data.local.model.UnivCertStatus
import dku.gyeongsotone.gulging.campusplogging.data.local.model.User

data class UserResponse(
    @Json(name = "user_id")
    val userId: String,

    @Json(name = "nickname")
    val nickname: String? = null,

    @Json(name = "student_id")
    val studentId: String? = null,

    @Json(name = "univ_cert_status")
    val univCertStatus: Int
)

fun UserResponse.toUser() =
    User(userId, nickname, studentId, UnivCertStatus.values()[univCertStatus])
