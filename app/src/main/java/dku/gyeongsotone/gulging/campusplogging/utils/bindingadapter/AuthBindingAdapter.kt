package dku.gyeongsotone.gulging.campusplogging.utils.bindingadapter

import android.text.InputType
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import dku.gyeongsotone.gulging.campusplogging.R
import dku.gyeongsotone.gulging.campusplogging.ui.auth.UserIdStatus
import dku.gyeongsotone.gulging.campusplogging.ui.auth.Password1CheckStatus
import dku.gyeongsotone.gulging.campusplogging.ui.auth.Password2CheckStatus

/**
 * AuthActivity 와 관련된 bindingAdapter
 *
 */

/** 로그인 - 아이디, 비밀번호 입력 여부 확인 */
@BindingAdapter(value = ["userId", "password"], requireAll = false)
fun TextView.setSignInBtn(userId: String? = null, password: String? = null) {
    val signInAllInfoInput = !userId.isNullOrEmpty() && !password.isNullOrEmpty()
    if (signInAllInfoInput) {
        isClickable = true
        setBackgroundResource(R.drawable.background_btn_default_after)
    } else {
        isClickable = false
        setBackgroundResource(R.drawable.background_btn_default_before)
    }
}

/** 로그인, 회원가입 - 비밀번호 show */
@BindingAdapter("passwordVisible")
fun EditText.setPasswordVisible(show: Boolean) {
    val curCursorPosition = selectionEnd
    inputType =
        if (show) InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        else InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
    setSelection(curCursorPosition)
}

/** 회원가입 - 아이디 체크 */
@BindingAdapter("userIdDescription")
fun TextView.setUserIdDescription(status: UserIdStatus) {
    text = when (status) {
        UserIdStatus.EMPTY -> ""
        UserIdStatus.IMPOSSIBLE -> {
            setTextColor(ContextCompat.getColor(context, R.color.alert_red))
            "사용 불가능한 아이디입니다\n*8~16자의 영문 소문자 및 숫자 혼용만 가능"
        }
        UserIdStatus.BEFORE_CHECK -> {
            setTextColor(ContextCompat.getColor(context, R.color.alert_red))
            "아이디 중복 확인해주세요"
        }
        UserIdStatus.POSSIBLE -> {
            setTextColor(ContextCompat.getColor(context, R.color.alert_green))
            "사용 가능한 아이디입니다"
        }
    }
}

/** 회원가입 - 아이디 체크 */
@BindingAdapter("userIdDupBtn")
fun TextView.setUserIdDupBtn(status: UserIdStatus) {
    isClickable = when (status) {
        UserIdStatus.EMPTY, UserIdStatus.IMPOSSIBLE, UserIdStatus.POSSIBLE -> {
            setBackgroundResource(R.drawable.background_btn_default_before)
            false
        }
        UserIdStatus.BEFORE_CHECK -> {
            setBackgroundResource(R.drawable.background_btn_default_after)
            true
        }
    }
}

/** 회원가입 - 비밀번호1 체크 */
@BindingAdapter("password1Description")
fun TextView.setPassword1Description(status: Password1CheckStatus) {
    text = when (status) {
        Password1CheckStatus.EMPTY, Password1CheckStatus.POSSIBLE -> ""
        Password1CheckStatus.IMPOSSIBLE -> "*8~16자의 영문 소문자 및 숫자 혼용만 가능"
    }
}

/** 회원가입 - 비밀번호2 체크 */
@BindingAdapter("password2Description")
fun TextView.setPassword2Description(status: Password2CheckStatus) {
    text = when (status) {
        Password2CheckStatus.EMPTY, Password2CheckStatus.SAME -> ""
        Password2CheckStatus.DIFFERENT -> "비밀번호가 일치하지 않습니다"
    }
}

/** 회원가입 - 아이디, 비밀번호1, 비밀번호2 체크 */
@BindingAdapter(value = ["userIdStats", "password1Status", "password2Status"], requireAll = true)
fun TextView.setSignUpBtn(
    userIdStatus: UserIdStatus,
    password1Status: Password1CheckStatus,
    password2Status: Password2CheckStatus
) {
    val signUpAllInfoInput =
        userIdStatus == UserIdStatus.POSSIBLE && password1Status == Password1CheckStatus.POSSIBLE && password2Status == Password2CheckStatus.SAME

    if (signUpAllInfoInput) {
        isClickable = true
        setBackgroundResource(R.drawable.background_btn_default_after)
    } else {
        isClickable = false
        setBackgroundResource(R.drawable.background_btn_default_before)
    }
}