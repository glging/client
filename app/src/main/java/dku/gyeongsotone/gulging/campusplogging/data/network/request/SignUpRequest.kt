package dku.gyeongsotone.gulging.campusplogging.data.network.request

import com.squareup.moshi.Json

data class SignUpRequest(
    @Json(name = "user_id")
    val userId: String,

    @Json(name = "password")
    val password: String
)
