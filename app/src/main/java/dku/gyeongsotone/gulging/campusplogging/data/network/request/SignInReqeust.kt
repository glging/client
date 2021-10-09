package dku.gyeongsotone.gulging.campusplogging.data.network.request

import com.squareup.moshi.Json

data class SignInRequest(
    @Json(name = "user_id")
    val userId: String,

    @Json(name = "password")
    val password: String
)
