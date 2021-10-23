package dku.gyeongsotone.gulging.campusplogging.data.local.model

data class Challenge(
    val type: ChallengeType,
    var status: ChallengeStatus,
    val name: String,

    val time: Int = 0,
    val level: Int = 0,
    val trashKind: Int = 0,
    val totalTrash: Int = 0,

    var isTimeAchieved: Boolean = false,
    var isLevelAchieved: Boolean = false,
    var isTrashKindAchieved: Boolean = false,
    var isTotalTrashAchieved: Boolean = false,
) {

    fun isAchieved(time: Int, level: Int, trashKind: Int, totalTrash: Int): Boolean {
        isTimeAchieved = time >= this.time
        isLevelAchieved = level >= this.level
        isTrashKindAchieved = trashKind >= this.trashKind
        isTotalTrashAchieved = totalTrash >= this.totalTrash

        return isTimeAchieved && isLevelAchieved && isTrashKindAchieved && isTotalTrashAchieved
    }
}


enum class ChallengeType {
    BY_GRADE, CUSTOM, SPECIAL_LECTURE
}

enum class ChallengeStatus {
    BEFORE, NOW, DONE
}