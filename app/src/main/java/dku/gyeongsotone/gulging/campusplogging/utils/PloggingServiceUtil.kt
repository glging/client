package dku.gyeongsotone.gulging.campusplogging.utils

import android.content.Context
import android.content.Intent
import dku.gyeongsotone.gulging.campusplogging.service.PloggingService

object PloggingServiceUtil {

    fun sendCommandToService(action: String, context: Context) {
        val intent = Intent(context, PloggingService::class.java)
        intent.action = action
        context.startService(intent)
    }
}