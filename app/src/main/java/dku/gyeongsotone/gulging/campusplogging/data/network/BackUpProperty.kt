package dku.gyeongsotone.gulging.campusplogging.data.network

import android.os.Build
import androidx.annotation.RequiresApi
import com.squareup.moshi.Json
import com.squareup.picasso.Picasso
import dku.gyeongsotone.gulging.campusplogging.data.local.model.Plogging
import dku.gyeongsotone.gulging.campusplogging.utils.toDate


data class GetPloggingHistory(
    @Json(name = "history")
    val history: List<PloggingProperty>
)

data class PloggingProperty(
    val id: Int = 0,
    val startDate: String,
    val endDate: String,
    val distance: Double,    // km
    val time: Int,           // minute
    val badge: Int = 0,      // count
    val picture: String,
    val plastic: Int = 0,
    val vinyl: Int = 0,
    val glass: Int = 0,
    val can: Int = 0,
    val paper: Int = 0,
    val general: Int = 0,
)

fun PloggingProperty.toPlogging() = Plogging(
    id = id,
    startDate = startDate.toDate(),
    endDate = endDate.toDate(),
    distance = distance,
    time = time,
    badge = badge,
    picture = Picasso.get().load(picture).get(),
    plastic = plastic,
    vinyl = vinyl,
    glass = glass,
    can = can,
    paper = paper,
    general = general
)