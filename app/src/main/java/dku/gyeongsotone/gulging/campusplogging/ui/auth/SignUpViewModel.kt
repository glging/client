package dku.gyeongsotone.gulging.campusplogging.ui.auth

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dku.gyeongsotone.gulging.campusplogging.data.local.model.User
import dku.gyeongsotone.gulging.campusplogging.data.repository.CamploRepository
import dku.gyeongsotone.gulging.campusplogging.data.repository.Result
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {
    companion object {
        private val TAG = this::class.java.name
        val regex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}\$")
    }

    private val repository = CamploRepository

    // 사용자가 입력한 아이디/비밀번호 정보
    val userId = ObservableField<String>()
    val password1 = ObservableField<String>()
    val password2 = ObservableField<String>()

    // 아이디/비밀번호 입력 상태
    val userIdStatus = ObservableField<UserIdStatus>(UserIdStatus.EMPTY)
    val password1Status = ObservableField<Password1CheckStatus>(Password1CheckStatus.EMPTY)
    val password2Status = ObservableField<Password2CheckStatus>(Password2CheckStatus.EMPTY)

    // 비밀번호 보이게/안보이게 여부
    val showPassword1 = ObservableBoolean(false)
    val showPassword2 = ObservableBoolean(false)

    // 회원가입 성공/실패 여부
    private val _signUpResult = MutableLiveData<SignUpStatus>()
    val signUpResult: LiveData<SignUpStatus> = _signUpResult

    var user: User? = null

    // 토스트 메시지
    private val _toastMsg = MutableLiveData<String>()
    val toastMsg: LiveData<String> = _toastMsg


    /**
     * 비밀번호 입력창 옆 눈 아이콘 클릭 시 비밀번호 보이게/안보이게 여부 반전
     */
    fun onClickShowPassword1Btn() {
        showPassword1.set(!showPassword1.get())
    }

    fun onClickShowPassword2Btn() {
        showPassword2.set(!showPassword2.get())
    }

    /**
     * text 입력 후에 조건에 따라 입력 상태 변경
     */
    fun onUserIdTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        when {
            count == 0 -> userIdStatus.set(UserIdStatus.EMPTY)
            regex.matches(s) -> userIdStatus.set(UserIdStatus.BEFORE_CHECK)
            else -> userIdStatus.set(UserIdStatus.IMPOSSIBLE)
        }
    }

    fun onPassword1TextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        when {
            count == 0 -> password1Status.set(Password1CheckStatus.EMPTY)
            regex.matches(s) -> password1Status.set(Password1CheckStatus.POSSIBLE)
            else -> password1Status.set(Password1CheckStatus.IMPOSSIBLE)
        }
        if (s.toString() == password2.get()) password2Status.set(Password2CheckStatus.SAME)
    }

    fun onPassword2TextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        when {
            count == 0 -> password2Status.set(Password2CheckStatus.EMPTY)
            password1.get() == s.toString() -> password2Status.set(Password2CheckStatus.SAME)
            else -> password2Status.set(Password2CheckStatus.DIFFERENT)
        }
    }

    /**
     * 아이디 중복 체크
     */
    fun onClickUserIdDupCheckBtn() {
        viewModelScope.launch {
            val response = repository.idDupCheck(userId.get()!!)

            // 오류가 발생했을 경우, 에러 메시지 띄운 후 리턴
            if (response is Result.Error) { // error
                _toastMsg.value = response.message
                return@launch
            }

            if (userIdStatus.get() == UserIdStatus.BEFORE_CHECK) {
                userIdStatus.set(UserIdStatus.POSSIBLE)
            }
        }
    }

    /**
     * 회원가입 서버에 요청
     */
    fun onClickSignUpBtn() {
        viewModelScope.launch {
            val response = repository.signUp(userId.get()!!, password1.get()!!)

            // 오류가 발생했을 경우, 에러 메시지 띄운 후 리턴
            if (response is Result.Error) { // error
                _toastMsg.value = response.message
                return@launch
            }

            user = (response as Result.Success<User>).data
            _signUpResult.value = SignUpStatus.SUCCESS
        }
    }

}

enum class Password1CheckStatus {
    EMPTY, IMPOSSIBLE, POSSIBLE
}

enum class Password2CheckStatus {
    EMPTY, DIFFERENT, SAME
}

enum class UserIdStatus {
    EMPTY, IMPOSSIBLE, BEFORE_CHECK, POSSIBLE
}

enum class SignUpStatus {
    SUCCESS, FAILED
}