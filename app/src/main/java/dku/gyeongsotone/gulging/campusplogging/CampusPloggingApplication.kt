package dku.gyeongsotone.gulging.campusplogging

import android.app.Application
import dku.gyeongsotone.gulging.campusplogging.data.local.database.CampusPloggingDatabase
import dku.gyeongsotone.gulging.campusplogging.data.local.model.User
import dku.gyeongsotone.gulging.campusplogging.utils.PreferenceUtil.initSharedPreference

class CampusPloggingApplication : Application() {
    val database: CampusPloggingDatabase by lazy { CampusPloggingDatabase.getDatabase(this) }
    val user: User? = null

    override fun onCreate() {
        super.onCreate()

        initSharedPreference(this)
    }
}