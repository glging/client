package dku.gyeongsotone.gulging.campusplogging.data.network.request

import com.squareup.moshi.Json

data class SendMailAuthRequest(
    @Json(name = "access_token")
    val token: String,

    @Json(name = "student_id")
    val studentId: String
)
