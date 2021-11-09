package dku.gyeongsotone.gulging.campusplogging.data.repository

import android.util.Log
import dku.gyeongsotone.gulging.campusplogging.data.local.model.Plogging
import dku.gyeongsotone.gulging.campusplogging.data.local.model.RankingInfo
import dku.gyeongsotone.gulging.campusplogging.data.local.model.User
import dku.gyeongsotone.gulging.campusplogging.data.network.*
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.SP_TOKEN
import dku.gyeongsotone.gulging.campusplogging.utils.PreferenceUtil
import dku.gyeongsotone.gulging.campusplogging.utils.PreferenceUtil.getSpString
import dku.gyeongsotone.gulging.campusplogging.utils.PreferenceUtil.setSpString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ApiRepository {
    private val TAG = this::class.java.name
    private val camploApi = RestApi.CamploApi

    /**
     * 회원 가입
     *
     * @return [String] error message (null if success)
     * @return [User] user(null if fail)
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
     * 로그인
     *
     * @return [String] error message (null if success)
     * @return [User] user (null if fail)
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
     * 아이디 중복 체크
     *
     * @return [String] error message (null if success)
     * @return [Boolean] true if can use userId (null if fail)
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

    /**
     * 토큰 로그인
     *
     * @return [String] error message (null if success)
     * @return [User] user (null if fail)
     */
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

    /**
     * 재학생 인증 메일 보내기
     *
     * @return [String] error message (null if success)
     */
    suspend fun sendMailAuth(studentId: String): String? = withContext(Dispatchers.IO) {
        val token = getSpString(SP_TOKEN)!!
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
     * 재학생 인증 코드 확인
     *
     * @return [String] error message (null if success)
     */
    suspend fun verifyMailAuth(token: String, verificationCode: String): String? =
        withContext(Dispatchers.IO) {
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

    /**
     * 랭킹 가져오기
     *
     * @return [Result<Ranking>] 랭킹 정보
     */
    suspend fun getRanking(token: String): Result<RankingInfo> = withContext(Dispatchers.IO) {
        val response = camploApi.getRanking(token)

        return@withContext when (response.body()?.success) {
            true -> Result.Success(response.body()!!.toRankingInfo())
            false -> Result.Error(response.body()!!.description)
            else -> Result.Error(response.message())
        }

    }

    suspend fun getPloggingHistory(token: String): Result<List<Plogging>> = withContext(Dispatchers.IO) {
        val response = camploApi.getPloggingHistory(token)

        return@withContext when (response.isSuccessful) {
            true -> Result.Success(response.body()!!.history.map { it.toPlogging() })
            false -> Result.Error(response.message())
        }
    }
}

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
}