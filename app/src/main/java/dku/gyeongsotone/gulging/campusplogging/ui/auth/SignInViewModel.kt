package dku.gyeongsotone.gulging.campusplogging.ui.auth

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignInViewModel : ViewModel() {
    companion object {
        private val TAG = this::class.java.name
    }

    // 사용자가 입력한 아이디/비밀번호 정보
    val userId = ObservableField<String>()
    val password = ObservableField<String>()

    // 비밀번호 보이게/안보이게 여부
    val showPassword = ObservableBoolean(false)

    // 로그인 성공/실패 여부
    private val _signInResult = MutableLiveData<SignInStatus>()
    val signInResult: LiveData<SignInStatus> = _signInResult


    /**
     * 비밀번호 입력창 옆 눈 아이콘 클릭 시 비밀번호 보이게/안보이게 여부 반전
     */
    fun onClickShowPasswordBtn() {
        showPassword.set(!showPassword.get())
    }

    /**
     * 로그인 성공 여부 판단해서 결과 저장
     */
    fun onClickLoginBtn() {
        // 일단 성공했다고 가정
        _signInResult.value = SignInStatus.SUCCESS
    }

}

enum class SignInStatus {
    SUCCESS, FAILED
}
