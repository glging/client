package dku.gyeongsotone.gulging.campusplogging.data.network.response

import com.squareup.moshi.Json

data class SignUpResponse(
    @Json(name = "user")
    val user: UserResponse? = null,

    @Json(name = "description")
    val description: String? = null,

    @Json(name = "access_token")
    val accessToken: String? = null,

    @Json(name = "success")
    val success: Boolean = false
)
