package dku.gyeongsotone.gulging.campusplogging.data.network.request

import com.squareup.moshi.Json

data class SendMailAuthRequest(
    @Json(name = "access_token")
    val accessToken: String,

    @Json(name = "student_id")
    val studentId: String
)
