package dku.gyeongsotone.gulging.campusplogging.utils

import android.Manifest
import dku.gyeongsotone.gulging.campusplogging.data.local.model.Challenge
import dku.gyeongsotone.gulging.campusplogging.data.local.model.ChallengeStatus
import dku.gyeongsotone.gulging.campusplogging.data.local.model.ChallengeType

object Constant {
    const val FILE_PROVIDER = "dku.gyeongsotone.gulging.campusplogging.fileprovider"

    /** intent 관련 상수 */
    const val EXTRA_PLOGGING_ID = "EXTRA_PLOGGING_ID"

    /** shared preference 관련 상수*/
    const val SP_TOKEN = "SP_TOKEN"
    const val SP_TOTAL_DISTANCE = "SP_TOTAL_DISTANCE"
    const val SP_TOTAL_BADGE = "SP_TOTAL_BADGE"
    const val SP_TOTAL_TRASH = "SP_TOTAL_TRASH"
    const val SP_TOTAL_TIME = "SP_TOTAL_TIME"
    const val SP_TRASH_KIND = "SP_TOTAL_TIME"
    const val SP_LEVEL = "SP_LEVEL"
    const val SP_REMAIN_DISTANCE = "SP_REMAIN_DISTANCE"
    const val SP_PROGRESS = "SP_PROGRESS"
    const val SP_CHALLENGE_ID = "SP_CHALLENGE_ID"  // 진행중인 플로깅


    /** main activity 관련 상수 */
    val MAIN_TAB_NAMES = listOf("플로깅", "챌린지", "기록", "랭킹")

    /** 학교 관련 상수 */
    const val UNIV_NAME = "단국대학교"
    const val UNIV_DISTANCE = 0.8 // 6.4

    /** 플로깅 관련 상수 */
    val TRASH_TYPES_ENG = listOf("plastic", "vinyl", "glass", "can", "paper", "general")
    val TRASH_TYPES_KOR = listOf("플라스틱", "비닐", "유리", "캔", "종이", "일반쓰레기")

    const val TRASH_COUNT_MIN = 0
    const val TRASH_COUNT_MAX = 99

    /** 권한 관련 상수 */
    const val REQUEST_CODE_LOCATION_PERMISSION = 101
    val REQUIRE_PERMISSIONS =
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )


    /** plogging service 관련 상수 */
    const val ACTION_START_OR_RESUME_SERVICE = "ACTION_START_OR_RESUME_SERVICE"
    const val ACTION_PAUSE_SERVICE = "ACTION_PAUSE_SERVICE"
    const val ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE"

    const val ACTION_SHOW_PLOGGING_FRAGMENT = "ACTION_SHOW_PLOGGING_FRAGMENT"

    const val LOCATION_UPDATE_INTERVAL = 10_000L
    const val FASTEST_LOCATION_INTERVAL = 10_000L
    const val TIMER_UPDATE_INTERVAL = 500L


    /** notification 관련 상수 */
    const val NOTIFICATION_CHANNEL_ID = "plogging_channel"
    const val NOTIFICATION_CHANNEL_NAME = "플로깅"
    const val NOTIFICATION_ID = 1


    /** 챌린지 관련 상수 */
    val CHALLENGE_LIST = listOf(
        Challenge(
            id = 0,
            type = ChallengeType.BY_GRADE,
            status = ChallengeStatus.BEFORE,
            name = "1학년 1학기",
            time = 10,
            level = 0,
            trashKind = 1,
            totalTrash = 0
        ), Challenge(
            id = 1,
            type = ChallengeType.BY_GRADE,
            status = ChallengeStatus.BEFORE,
            name = "1학년 2학기",
            time = 0,
            level = 1,
            trashKind = 2,
            totalTrash = 0
        ), Challenge(
            id = 2,
            type = ChallengeType.BY_GRADE,
            status = ChallengeStatus.BEFORE,
            name = "2학년 1학기",
            time = 0,
            level = 3,
            trashKind = 3,
            totalTrash = 0
        ),
        Challenge(
            id = 3,
            type = ChallengeType.BY_GRADE,
            status = ChallengeStatus.BEFORE,
            name = "2학년 2학기",
            time = 0,
            level = 5,
            trashKind = 4,
            totalTrash = 0
        ),
        Challenge(
            id = 4,
            type = ChallengeType.BY_GRADE,
            status = ChallengeStatus.BEFORE,
            name = "3학년 1학기",
            time = 0,
            level = 7,
            trashKind = 5,
            totalTrash = 0
        ),
        Challenge(
            id = 5,
            type = ChallengeType.BY_GRADE,
            status = ChallengeStatus.BEFORE,
            name = "3학년 2학기",
            time = 0,
            level = 9,
            trashKind = 0,
            totalTrash = 6
        ), Challenge(
            id = 6,
            type = ChallengeType.BY_GRADE,
            status = ChallengeStatus.BEFORE,
            name = "4학년 1학기",
            time = 30,
            level = 12,
            trashKind = 6,
            totalTrash = 0
        ),
        Challenge(
            id = 7,
            type = ChallengeType.BY_GRADE,
            status = ChallengeStatus.BEFORE,
            name = "4학년 2학기",
            time = 40,
            level = 15,
            trashKind = 0,
            totalTrash = 6
        ), Challenge(
            id = 8,
            type = ChallengeType.BY_GRADE,
            status = ChallengeStatus.BEFORE,
            name = "졸업시험",
            time = 100,
            level = 30,
            trashKind = 6,
            totalTrash = 0
        )
    )

}