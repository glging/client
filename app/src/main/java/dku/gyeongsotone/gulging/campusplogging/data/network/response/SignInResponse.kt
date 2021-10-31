package dku.gyeongsotone.gulging.campusplogging.data.network.response

import com.squareup.moshi.Json

data class SignInResponse(
    @Json(name = "user")
    val user: UserResponse? = null,

    @Json(name = "description")
    val description: String? = null,

    @Json(name = "access_token")
    val token: String? = null,

    @Json(name = "success")
    val success: Boolean = false
)
