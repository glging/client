package dku.gyeongsotone.gulging.campusplogging.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dku.gyeongsotone.gulging.campusplogging.R
import dku.gyeongsotone.gulging.campusplogging.databinding.FragmentSignInBinding
import dku.gyeongsotone.gulging.campusplogging.ui.univcertification.UnivCertificationActivity

class SignInFragment : Fragment() {
    companion object {
        private val TAG = this::class.java.name
    }

    private lateinit var binding: FragmentSignInBinding
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        init(inflater, container)
        setClickListener()
        setObserver()

        return binding.root
    }

    /** binding, view model, spannable text 설정 */
    private fun init(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_sign_in,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setSpannableText()
    }

    /** spannable text 설정 */
    private fun setSpannableText() {
        val signUpBtnSpannable = SpannableStringBuilder("혹시 처음 오셨나요? 회원가입하러 가기")
        signUpBtnSpannable.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.blue_link)),
            12,
            21,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        signUpBtnSpannable.setSpan(UnderlineSpan(), 0, 21, 0)
        binding.btnSignUp.text = signUpBtnSpannable
    }

    /** 클릭 리스너 설정 */
    private fun setClickListener() {
        binding.btnSignUp.setOnClickListener { onSignUpBtnClick() }
    }

    /** 회원가입 버튼 클릭 시, 회원가입 프래그먼트로 이동 */
    private fun onSignUpBtnClick() {
        findNavController().navigate(
            SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
        )
    }

    /** observer 설정 */
    private fun setObserver() {
        setLoginResultObserver()
    }

    /** 로그인 성공 시 메인화면으로 이동 후, 현재 액티비티 종료 */
    private fun setLoginResultObserver() {
        viewModel.signInResult.observe(viewLifecycleOwner) { result ->
            Log.d(TAG, "login result: $result")
            if (result == SignInStatus.SUCCESS) {

                // 학교 인증 안 되어있다고 가정
                val intent = Intent(context, UnivCertificationActivity::class.java)
                startActivity(intent)
                requireActivity().finish()

                // 학교 인증 되어있으면 메인으로 이동
//                val intent = Intent(context, MainActivity::class.java)
//                startActivity(intent)
//                requireActivity().finish()
            }
        }
    }
}