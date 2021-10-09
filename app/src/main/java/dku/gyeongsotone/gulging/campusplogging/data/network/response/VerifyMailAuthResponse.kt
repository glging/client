package dku.gyeongsotone.gulging.campusplogging.data.network.response

import com.squareup.moshi.Json

data class VerifyMailAuthResponse(
    @Json(name = "description")
    val description: String? = null,

    @Json(name = "success")
    val success: Boolean = false
)
