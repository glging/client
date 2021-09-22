package dku.gyeongsotone.gulging.campusplogging.ui.univcertification

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MailAuthViewModel : ViewModel() {
    companion object {
        private val TAG = this::class.java.name
    }

    // 사용자가 입력한 학번/인증번호
    val studentId = ObservableField<String>()
    val verificationCode = ObservableField<String>()

    // 학번/인증코드 입력 상태
    val studentIdStatus = ObservableField<StudentIdStatus>(StudentIdStatus.EMPTY)
    val verificationCodeStatus =
        ObservableField<VerificationCodeStatus>(VerificationCodeStatus.BEFORE_SEND)

    // keyboard hide 여부
    private val _keyboardHide = MutableLiveData<Boolean>()
    val keyboardHide: LiveData<Boolean> = _keyboardHide


    /** 인증번호 전송 */
    fun onClickSendVerificationCodeBtn() {
        // 보냈다고 가정
        studentIdStatus.set(StudentIdStatus.WAIT)
        verificationCodeStatus.set(VerificationCodeStatus.WAIT)
    }

    /** 인증하기 */
    fun onClickVerifyCodeBtn() {
        _keyboardHide.value = true

        // 인증 성공했다고 가정
        if (verificationCodeStatus.get() == VerificationCodeStatus.WAIT) {
            studentIdStatus.set(StudentIdStatus.SUCCESS)
            verificationCodeStatus.set(VerificationCodeStatus.SUCCESS)
        }
    }

    /** text 입력 후에 조건에 따라 입력 상태 변경 */
    fun onStudentIdTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        when {
            verificationCodeStatus.get() == VerificationCodeStatus.BEFORE_SEND && s.length == 8 ->
                studentIdStatus.set(StudentIdStatus.BEFORE_SEND)

            verificationCodeStatus.get() == VerificationCodeStatus.WAIT && s.length == 8 ->
                studentIdStatus.set(StudentIdStatus.WAIT)

            verificationCodeStatus.get() == VerificationCodeStatus.WAIT && s.length != 8 ->
                studentIdStatus.set(StudentIdStatus.WAIT_EMPTY)

            else -> studentIdStatus.set(StudentIdStatus.EMPTY)
        }
    }

}

enum class StudentIdStatus {
    EMPTY, BEFORE_SEND, WAIT, WAIT_EMPTY, SUCCESS
}

enum class VerificationCodeStatus {
    BEFORE_SEND, WAIT, SUCCESS
}