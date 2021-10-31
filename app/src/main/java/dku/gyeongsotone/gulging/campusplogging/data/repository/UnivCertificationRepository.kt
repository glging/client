package dku.gyeongsotone.gulging.campusplogging.data.repository

import android.util.Log
import dku.gyeongsotone.gulging.campusplogging.data.network.RestApi
import dku.gyeongsotone.gulging.campusplogging.data.network.request.SendMailAuthRequest
import dku.gyeongsotone.gulging.campusplogging.data.network.request.VerifyMailAuthRequest
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.SP_ACCESS_TOKEN
import dku.gyeongsotone.gulging.campusplogging.utils.PreferenceUtil.getSpString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object UnivCertificationRepository {
    private val TAG = this::class.java.name
    private val camploApi = RestApi.CamploApi


    /**
     *
     * @return String: error message (null if success)
     */
    suspend fun sendMailAuth(studentId: String): String? = withContext(Dispatchers.IO) {
        val token = getSpString(SP_ACCESS_TOKEN)!!
        val request = SendMailAuthRequest(token, studentId)
        val response = camploApi.sendMailAuth(request)
        Log.d(TAG, "sendMailAuth response: \n${response}")
        Log.d(TAG, "sendMailAuth body: \n${response.body()}")

        return@withContext when (response.body()?.success) {
            true -> null
            false -> response.body()!!.description
            null -> response.message()
        }
    }

    /**
     *
     * @return String: error message (null if success)
     */
    suspend fun verifyMailAuth(verificationCode: String): String? = withContext(Dispatchers.IO) {
        val token = getSpString(SP_ACCESS_TOKEN)!!
        val request = VerifyMailAuthRequest(token, verificationCode)
        val response = camploApi.verifyMailAuth(request)
        Log.d(TAG, "verifyMailAuth response: \n${response}")
        Log.d(TAG, "verifyMailAuth body: \n${response.body()}")

        return@withContext when (response.body()?.success) {
            true -> null
            false -> response.body()!!.description
            null -> response.message()
        }
    }
}