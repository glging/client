package dku.gyeongsotone.gulging.campusplogging.data.network

import com.squareup.moshi.Json
import com.squareup.picasso.Picasso
import dku.gyeongsotone.gulging.campusplogging.data.local.model.UnivCertStatus
import dku.gyeongsotone.gulging.campusplogging.data.local.model.User

data class UserProperty(
    @Json(name = "user_id")
    val userId: String,

    @Json(name = "nickname")
    val nickname: String? = null,

    @Json(name = "student_id")
    val studentId: String? = null,

    @Json(name = "univ_cert_status")
    val univCertStatus: Int,

    @Json(name = "profile_image")
    val profileImage: String
)

fun UserProperty.toUser() = User(
    userId = userId,
    nickname = nickname,
    studentId = studentId,
    univCertStatus = UnivCertStatus.values()[univCertStatus],
    profileImage = Picasso.get().load(profileImage).get()
)


