package dku.gyeongsotone.gulging.campusplogging.utils

object Constant {
    /** main activity 관련 상수 */
    val MAIN_TAB_NAMES = listOf("플로깅")

    /** 학교 관련 상수 */
    const val UNIV_NAME = "단국대학교"
    const val UNIV_DISTANCE = 6.4

    /** 플러깅 관련 상수 */
    val TRASH_TYPES_ENG = listOf("plastic", "vinyl", "glass", "can", "paper", "general")
    val TRASH_TYPES_KOR = listOf("플라스틱", "비닐", "유리", "캔", "종이", "일반쓰레기")

    /** 권한 관련 상수 */
    const val REQUEST_CODE_LOCATION_PERMISSION = 101

    /** plogging service 관련 상수 */
    const val ACTION_START_OR_RESUME_SERVICE = "ACTION_START_OR_RESUME_SERVICE"
    const val ACTION_PAUSE_SERVICE = "ACTION_PAUSE_SERVICE"
    const val ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE"

    const val LOCATION_UPDATE_INTERVAL = 5000L
    const val FASTEST_LOCATION_INTERVAL = 2000L
    const val TIMER_UPDATE_INTERVAL = 500L

    /** notification 관련 상수 */
    const val NOTIFICATION_CHANNEL_ID = "plogging_channel"
    const val NOTIFICATION_CHANNEL_NAME = "플로깅"
    const val NOTIFICATION_ID = 1
}