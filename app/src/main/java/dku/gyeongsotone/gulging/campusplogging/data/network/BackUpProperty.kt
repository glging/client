package dku.gyeongsotone.gulging.campusplogging.data.network

import android.annotation.SuppressLint
import android.graphics.Bitmap
import com.squareup.moshi.Json
import com.squareup.picasso.Picasso
import dku.gyeongsotone.gulging.campusplogging.data.local.model.Plogging
import dku.gyeongsotone.gulging.campusplogging.utils.toDate
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


data class RestorePloggingDataResponse(
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


fun Plogging.toBackUpRequestMap(token: String): HashMap<String, RequestBody> =
    HashMap<String, RequestBody>().apply {
        put("access_token", token.toRequestBody())
        put("id", id.toRequestBody())
        put("startDate", startDate.toRequestBody())
        put("endDate", endDate.toRequestBody())
        put("distance", distance.toRequestBody())
        put("time", time.toRequestBody())
        put("badge", badge.toRequestBody())
        put("plastic", plastic.toRequestBody())
        put("vinyl", vinyl.toRequestBody())
        put("glass", glass.toRequestBody())
        put("can", can.toRequestBody())
        put("paper", paper.toRequestBody())
        put("general", general.toRequestBody())
    }


fun Bitmap.toMultipartBody(): MultipartBody.Part {
    val file = File.createTempFile("picture_", ".jpeg")
    val outputStream = FileOutputStream(file)
    this.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)

    val requestBody: RequestBody =
        RequestBody.create(MediaType.parse("image/jpeg"), file)

    return MultipartBody.Part.createFormData("picture", file.name, requestBody)
}

@SuppressLint("SimpleDateFormat")
fun Date.toRequestBody(): RequestBody {
    val strDate = SimpleDateFormat("yyyy-MM-dd").format(this)

    return RequestBody.create(MediaType.parse("text/plain"), strDate)
}

fun String.toRequestBody(): RequestBody =
    RequestBody.create(MediaType.parse("text/plain"), this)

fun Int.toRequestBody(): RequestBody =
    RequestBody.create(MediaType.parse("text/plain"), this.toString())

fun Double.toRequestBody(): RequestBody =
    RequestBody.create(MediaType.parse("text/plain"), this.toString())