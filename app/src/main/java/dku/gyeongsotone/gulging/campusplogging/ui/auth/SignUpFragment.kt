package dku.gyeongsotone.gulging.campusplogging.ui.auth

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
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
import dku.gyeongsotone.gulging.campusplogging.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    companion object {
        private val TAG = this::class.java.name
    }

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

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
            R.layout.fragment_sign_up,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setSpannableText()
    }

    /** spannable text 설정 */
    private fun setSpannableText() {
        val titleTextViewSpannable = SpannableStringBuilder("반갑습니다!\n캠퍼스플로깅을 시작해봐요")
        titleTextViewSpannable.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.main_yellow)),
            7,
            13,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        binding.tvTitle.text = titleTextViewSpannable
    }

    /** 클릭 리스너 설정 */
    private fun setClickListener() {
    }


    /** observer 설정 */
    private fun setObserver() {
        setSignUpResultObserver()
    }

    /** 회원가입 성공 시, 회원가입 완료 페이지로 이동 */
    private fun setSignUpResultObserver() {
        viewModel.signUpResult.observe(viewLifecycleOwner) { result ->
            if (result == SignUpStatus.SUCCESS) {
                findNavController().navigate(
                    SignUpFragmentDirections.actionSignUpFragmentToSignUpCompleteFragment()
                )
            }
        }
    }
}