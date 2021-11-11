package dku.gyeongsotone.gulging.campusplogging.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import dku.gyeongsotone.gulging.campusplogging.APP
import dku.gyeongsotone.gulging.campusplogging.data.network.toMultipartBody
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.MAX_IMAGE_SIZE
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun MutableLiveData<*>.notifyDataChanged() {
    this.value = this.value
}

fun getApplication(activity: Activity) =
    activity.application as APP

fun Long.msToMinute() = TimeUnit.MILLISECONDS.toMinutes(this)
fun Long.msToSecond() = TimeUnit.MILLISECONDS.toSeconds(this)
fun Double.mToKm() = this / 1000

fun Int.addTimeUnit(): String =
    if (this >= 60) "${this / 60}시간" else "${this}분"


fun Int.pxToDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()
fun Int.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

@SuppressLint("SimpleDateFormat")
fun String.toDate(): Date = SimpleDateFormat("yyyy-MM-dd").parse(this)!!
fun getCurrentDate(): Date = Date(System.currentTimeMillis())

fun Bitmap.compress(): Bitmap {
    val sizeMB = this.byteCount / 1024 / 1024
    Log.d("SIZE_TEST", "before sizeMB: ${this.byteCount.toDouble() / 1024 / 1024}")

    if (sizeMB <= MAX_IMAGE_SIZE) {
        return this
    }

    val quality = (MAX_IMAGE_SIZE * 100.0 / sizeMB).toInt()
    val outputStream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)

    val byteArray = outputStream.toByteArray()
    val compressedBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)

    Log.d("SIZE_TEST", "compressed sizeMB: ${compressedBitmap.byteCount / 1024 / 1024}, quality: $quality")
    return compressedBitmap
}
fun showToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

