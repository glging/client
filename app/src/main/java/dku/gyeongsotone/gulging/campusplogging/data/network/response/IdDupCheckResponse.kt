package dku.gyeongsotone.gulging.campusplogging.data.network.response

import com.squareup.moshi.Json

data class IdDupCheckResponse(
    @Json(name = "is_existing")
    val isExisting: Boolean? = null,
)
