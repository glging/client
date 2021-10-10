package dku.gyeongsotone.gulging.campusplogging.utils

import android.content.Context
import android.content.Intent
import dku.gyeongsotone.gulging.campusplogging.service.PloggingService
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.ACTION_PAUSE_SERVICE
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.ACTION_START_OR_RESUME_SERVICE
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.ACTION_STOP_SERVICE

object PloggingServiceUtil {

    fun startPloggingService(context: Context) {
        val intent = Intent(context, PloggingService::class.java)
        intent.action = ACTION_START_OR_RESUME_SERVICE
        context.startService(intent)
    }

    fun pausePloggingService(context: Context) {
        val intent = Intent(context, PloggingService::class.java)
        intent.action = ACTION_PAUSE_SERVICE
        context.startService(intent)
    }

    fun stopPloggingService(context: Context) {
        val intent = Intent(context, PloggingService::class.java)
        intent.action = ACTION_STOP_SERVICE
        context.startService(intent)
    }
}