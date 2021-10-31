package dku.gyeongsotone.gulging.campusplogging.data.network.request

import com.squareup.moshi.Json

data class VerifyMailAuthRequest(
    @Json(name = "access_token")
    val token: String,

    @Json(name = "authentication_number")
    val verificationCode: String
)
