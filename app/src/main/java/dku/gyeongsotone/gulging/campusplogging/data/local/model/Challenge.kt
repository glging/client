package dku.gyeongsotone.gulging.campusplogging.data.local.model

import dku.gyeongsotone.gulging.campusplogging.utils.addTimeUnit

data class Challenge(
    val id: Int,
    val type: ChallengeType,
    var status: ChallengeStatus,
    val name: String,

    val time: Int = 0,
    val level: Int = 0,
    val trashKind: Int = 0,
    val totalTrash: Int = 0
) {

    fun isTimeAchieved(time: Int): Boolean = time >= this.time

    fun isLevelAchieved(level: Int): Boolean = level >= this.level

    fun isTrashKindAchieved(trashKind: Int): Boolean = trashKind >= this.trashKind

    fun isTotalTrashAchieved(totalTrash: Int): Boolean = totalTrash >= this.totalTrash

    fun isAchieved(time: Int, level: Int, trashKind: Int, totalTrash: Int): Boolean =
        isTimeAchieved(time)
                && isLevelAchieved(level)
                && isTrashKindAchieved(trashKind)
                && isTotalTrashAchieved(totalTrash)


    fun getLevelContent(): String = "${level}학교 달성"

    fun getTimeContent(): String = "${time.addTimeUnit()} 이상 러닝"

    fun getTrashKindContent(): String = "쓰레기 ${trashKind}종류 이상 줍기"

    fun getTotalTrashContent(): String = "쓰레기 ${totalTrash}개 이상 줍기"

    fun getFullContent(): String {
        var content = String()

        if (level > 0) content += getLevelContent() + "\n"
        if (time > 0) content += getTimeContent() + "\n"
        if (trashKind > 0) content += getTrashKindContent() + "\n"
        if (totalTrash > 0) content += getTotalTrashContent()

        return content
    }
}


enum class ChallengeType {
    BY_GRADE, CUSTOM, SPECIAL_LECTURE
}

enum class ChallengeStatus {
    BEFORE, NOW, DONE
}