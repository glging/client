package dku.gyeongsotone.gulging.campusplogging.utils.bindingadapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import dku.gyeongsotone.gulging.campusplogging.R
import dku.gyeongsotone.gulging.campusplogging.ui.univcertification.StudentIdStatus
import dku.gyeongsotone.gulging.campusplogging.ui.univcertification.VerificationCodeStatus

/**
 * UnivCertificationActivity 와 관련된 bindingAdapter
 *
 */

/** 학교 메일로 인증 - 코드 전송 버튼 */
@BindingAdapter("sendVerificationCodeBtn")
fun TextView.setSendVerificationCodeBtn(status: StudentIdStatus) {
    isClickable = when (status) {
        StudentIdStatus.EMPTY -> {
            text = "인증번호 전송"
            setBackgroundResource(R.drawable.background_btn_default_before)
            false
        }
        StudentIdStatus.BEFORE_SEND -> {
            text = "인증번호 전송"
            setBackgroundResource(R.drawable.background_btn_default_after)
            true
        }
        StudentIdStatus.WAIT -> {
            text = "다시 전송"
            setBackgroundResource(R.drawable.background_btn_default_after)
            true
        }
        StudentIdStatus.WAIT_EMPTY -> {
            text = "다시 전송"
            setBackgroundResource(R.drawable.background_btn_default_before)
            false
        }
        StudentIdStatus.SUCCESS -> {
            text = "인증 완료"
            setBackgroundResource(R.drawable.background_btn_default_before)
            false
        }
    }
}

/** 학교 메일로 인증 - 인증하기 버튼 */
@BindingAdapter("verifyCodeBtn")
fun TextView.setVerifyCodeBtn(status: VerificationCodeStatus) {
    isClickable = when(status) {
        VerificationCodeStatus.BEFORE_SEND -> {
            text = "인증 하기"
            setBackgroundResource(R.drawable.background_btn_default_before)
            false
        }
        VerificationCodeStatus.WAIT -> {
            text = "인증 하기"
            setBackgroundResource(R.drawable.background_btn_default_after)
            true
        }
        VerificationCodeStatus.SUCCESS -> {
            text = "인증 완료"
            setBackgroundResource(R.drawable.background_btn_default_before)
            false
        }
    }
}
