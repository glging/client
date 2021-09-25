package dku.gyeongsotone.gulging.campusplogging.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

object PreferenceUtil {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    fun initSharedPreference(context: Context) {
        sharedPreferences =
            context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        editor.apply()
    }

    fun getSpInt(key: String) =
        sharedPreferences.getInt(key, 0)

    fun getSpLong(key: String) =
        sharedPreferences.getLong(key, 0)

    fun getSpDouble(key: String) =
        sharedPreferences.getDouble(key, 0.0)

    fun getSpFloat(key: String) =
        sharedPreferences.getFloat(key, 0f)

    fun getSpBoolean(key: String) =
        sharedPreferences.getBoolean(key, false)

    fun getSpString(key: String) =
        sharedPreferences.getString(key, null)


    fun setSpInt(key: String, value: Int) {
        editor.putInt(key, value)
        editor.apply()
    }

    fun setSpLong(key: String, value: Long) {
        editor.putLong(key, value)
        editor.apply()
    }

    fun setSpDouble(key: String, value: Double) {
        editor.putDouble(key, value)
        editor.apply()
    }

    fun setSpFloat(key: String, value: Float) {
        editor.putFloat(key, value)
        editor.apply()
    }

    fun setSpBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun setSpString(key: String, value: String) {
        editor.putString(key, value)
        editor.apply()
    }


    private fun SharedPreferences.Editor.putDouble(
        key: String,
        double: Double
    ): SharedPreferences.Editor =
        putLong(key, java.lang.Double.doubleToRawLongBits(double))

    private fun SharedPreferences.getDouble(key: String, default: Double) =
        java.lang.Double.longBitsToDouble(
            getLong(
                key,
                java.lang.Double.doubleToRawLongBits(default)
            )
        )
}
