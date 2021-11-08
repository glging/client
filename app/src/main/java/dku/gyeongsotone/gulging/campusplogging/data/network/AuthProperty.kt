package dku.gyeongsotone.gulging.campusplogging.data.network

import com.squareup.moshi.Json

// [회원가입] 아이디 중복 확인 응답
data class IdDupCheckResponse(
    @Json(name = "is_existing")
    val isExisting: Boolean? = null,
)

// [회원가입] 회원가입 요청
data class SignUpRequest(
    @Json(name = "user_id")
    val userId: String,

    @Json(name = "password")
    val password: String
)

// [회원가입] 회원가입 응답
data class SignUpResponse(
    @Json(name = "user")
    val user: UserProperty? = null,

    @Json(name = "description")
    val description: String? = null,

    @Json(name = "access_token")
    val token: String? = null,

    @Json(name = "success")
    val success: Boolean = false
)

// [로그인] 로그인 요청
data class SignInRequest(
    @Json(name = "user_id")
    val userId: String,

    @Json(name = "password")
    val password: String
)

// [로그인] 로그인 응답
data class SignInResponse(
    @Json(name = "user")
    val user: UserProperty? = null,

    @Json(name = "description")
    val description: String? = null,

    @Json(name = "access_token")
    val token: String? = null,

    @Json(name = "success")
    val success: Boolean = false
)

// [로그인] 토큰 로그인 응답
data class TokenLoginResponse(
    @Json(name = "user")
    val user: UserProperty? = null,

    @Json(name = "description")
    val description: String? = null,

    @Json(name = "success")
    val success: Boolean = false
)

// [학교 인증] 인증 메일 보내기 요청
data class SendMailAuthRequest(
    @Json(name = "access_token")
    val token: String,

    @Json(name = "student_id")
    val studentId: String
)

// [학교 인증] 인증 메일 보내기 응답
data class SendMailAuthResponse(
    @Json(name = "description")
    val description: String? = null,

    @Json(name = "success")
    val success: Boolean = false
)

// [학교 인증] 인증 번호 확인 요청
data class VerifyMailAuthRequest(
    @Json(name = "access_token")
    val token: String,

    @Json(name = "authentication_number")
    val verificationCode: String
)

// [학교 인증] 인증 번호 확인 응답
data class VerifyMailAuthResponse(
    @Json(name = "description")
    val description: String? = null,

    @Json(name = "success")
    val success: Boolean = false
)
