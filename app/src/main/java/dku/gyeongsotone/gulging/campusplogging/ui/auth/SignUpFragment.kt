package dku.gyeongsotone.gulging.campusplogging.ui.auth

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dku.gyeongsotone.gulging.campusplogging.APP
import dku.gyeongsotone.gulging.campusplogging.R
import dku.gyeongsotone.gulging.campusplogging.databinding.FragmentSignUpBinding
import dku.gyeongsotone.gulging.campusplogging.utils.getApplication

class SignUpFragment : Fragment() {
    companion object {
        private val TAG = this::class.java.name
    }

    private lateinit var application: APP
    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        init(inflater, container)

        return binding.root
    }

    /**
     * 초기 설정
     */
    private fun init(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_sign_up,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        application = getApplication(requireActivity())

        setSpannableText()
        setObserver()
    }

    /**
     * pannable text 설정
     */
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


    /**
     * observer 설정
     */
    private fun setObserver() {
        setSignUpResultObserver()
        setToastMsgObserver()
    }

    /**
     * 회원가입 성공 시, 유저를 application에 넣고 회원가입 완료 페이지로 이동
     */
    private fun setSignUpResultObserver() {
        viewModel.signUpResult.observe(viewLifecycleOwner) { result ->
            if (result == SignUpStatus.SUCCESS) {
                application.user = viewModel.user
                findNavController().navigate(
                    SignUpFragmentDirections.actionSignUpFragmentToSignUpCompleteFragment()
                )
            }
        }
    }

    private fun setToastMsgObserver() {
        viewModel.toastMsg.observe(viewLifecycleOwner) { msg ->
            if (msg.isNotEmpty()) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }
        }
    }
}