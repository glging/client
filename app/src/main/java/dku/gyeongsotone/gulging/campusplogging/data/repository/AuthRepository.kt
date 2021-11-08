package dku.gyeongsotone.gulging.campusplogging.data.repository

import android.util.Log
import dku.gyeongsotone.gulging.campusplogging.data.local.model.User
import dku.gyeongsotone.gulging.campusplogging.data.network.RestApi
import dku.gyeongsotone.gulging.campusplogging.data.network.SignInRequest
import dku.gyeongsotone.gulging.campusplogging.data.network.SignUpRequest
import dku.gyeongsotone.gulging.campusplogging.data.network.toUser
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.SP_TOKEN
import dku.gyeongsotone.gulging.campusplogging.utils.PreferenceUtil.setSpString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AuthRepository {
    private val TAG = this::class.java.name
    private val camploApi = RestApi.CamploApi

    /**
     * @return String: error message (null if success)
     * @return User: user(null if fail)
     */
    suspend fun signUp(userId: String, password: String): Pair<String?, User?> =
        withContext(Dispatchers.IO) {
            val request = SignUpRequest(userId, password)
            val response = camploApi.signUp(request)
            Log.d(TAG, "signUp response: \n${response}")
            Log.d(TAG, "signUp body: \n${response.body()}")

            return@withContext when (response.body()?.success) {
                true -> {
                    val body = response.body()!!
                    setSpString(SP_TOKEN, body.token!!)
                    Pair(null, body.user!!.toUser())
                }
                false -> Pair(response.body()!!.description, null)
                null -> Pair(response.message(), null)
            }
        }

    /**
     * @return String: error message (null if success)
     * @return User: user(null if fail)
     */
    suspend fun signIn(userId: String, password: String): Pair<String?, User?> =
        withContext(Dispatchers.IO) {
            val request = SignInRequest(userId, password)
            val response = camploApi.signIn(request)
            Log.d(TAG, "signIn response: \n${response}")
            Log.d(TAG, "signIn body: \n${response.body()}")

            return@withContext when (response.body()?.success) {
                true -> {
                    val body = response.body()!!
                    setSpString(SP_TOKEN, body.token!!)
                    Pair(null, body.user!!.toUser())
                }
                false -> Pair(response.body()!!.description, null)
                null -> Pair(response.message(), null)
            }
        }

    /**
     * @return String: error message (null if success)
     * @return Boolean: true if can use userId (null if fail)
     */
    suspend fun idDupCheck(userId: String): Pair<String?, Boolean?> = withContext(Dispatchers.IO) {
        val response = camploApi.idDupCheck(userId)
        Log.d(TAG, "idDupCheck response: \n${response}")
        Log.d(TAG, "idDupCheck body: \n${response.body()}")

        return@withContext when (response.body()?.isExisting?.let { !it }) {
            true -> Pair(null, true)
            false -> Pair("이미 존재하는 아이디입니다.", null)
            null -> Pair(response.message(), null)
        }
    }

    suspend fun tokenLogin(token: String): Pair<String?, User?> = withContext(Dispatchers.IO) {
        val response = camploApi.tokenLogin(token)
        Log.d(TAG, "tokenLogin response: \n${response}")
        Log.d(TAG, "tokenLogin body: \n${response.body()}")

        return@withContext when (response.body()?.success) {
            true -> Pair(null, response.body()!!.user!!.toUser())
            false -> Pair(response.body()!!.description, null)
            null -> Pair(response.message(), null)
        }
    }
}