package dku.gyeongsotone.gulging.campusplogging.data.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "http://18.119.6.206:8001"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface CamploRepositoryService {
    @POST("/login")
    suspend fun signIn(@Body signInRequest: SignInRequest): Response<SignInResponse>

    @POST("/join")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<SignUpResponse>

    @GET("/join/id")
    suspend fun idDupCheck(@Query("user_id") userId: String): Response<IdDupCheckResponse>

    @POST("/school-authentication/mail/student-id")
    suspend fun sendMailAuth(@Body sendMailAuthRequest: SendMailAuthRequest): Response<SendMailAuthResponse>

    @POST("/school-authentication/mail/authentication")
    suspend fun verifyMailAuth(@Body verifyMailAuthRequest: VerifyMailAuthRequest): Response<VerifyMailAuthResponse>

    @GET("/login")
    suspend fun tokenLogin(@Query("access_token") token: String): Response<TokenLoginResponse>

    @GET("/ranking")
    suspend fun getRanking(@Query("access_token") token: String): Response<GetRankingResponse>

    @GET("/plogging-history")
    suspend fun restorePloggingData(@Query("access_token") token: String): Response<RestorePloggingDataResponse>

    @Multipart
    @POST("/plogging-result")
    suspend fun backUpPlogging(@PartMap params: Map<String, @JvmSuppressWildcards RequestBody>): Response<Unit>

    @DELETE
    suspend fun deletePlogging(
        @Query("access_token") token: String,
        @Query("id") id: Int
    ): Response<Unit>
}

object CamploApi {
    val client: CamploRepositoryService by lazy { retrofit.create(CamploRepositoryService::class.java) }
}