package dku.gyeongsotone.gulging.campusplogging.data.local.model

/**
 * indicates the status of enrollment certification
 *
 */
enum class UnivCertStatus {
    DONE, DOING, TO_DO
}

/**
 * user info
 *
 */
data class User(
    val userId: String,
    val password: String,
    val nickname: String,
    val studentId: String,
    val UnivCertStatus: UnivCertStatus,
)
