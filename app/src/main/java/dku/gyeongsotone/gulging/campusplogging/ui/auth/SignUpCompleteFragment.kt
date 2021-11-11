package dku.gyeongsotone.gulging.campusplogging.ui.auth

import android.content.Intent
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
import dku.gyeongsotone.gulging.campusplogging.R
import dku.gyeongsotone.gulging.campusplogging.databinding.FragmentSignUpCompleteBinding
import dku.gyeongsotone.gulging.campusplogging.ui.main.MainActivity
import dku.gyeongsotone.gulging.campusplogging.ui.univcertification.UnivCertificationActivity

class SignUpCompleteFragment : Fragment() {

    companion object {
        private val TAG = this::class.java.name
    }

    private lateinit var binding: FragmentSignUpCompleteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        init(inflater, container)
        setOnClickListener()

        return binding.root
    }


    /**
     * 초기 설정
     */
    private fun init(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_sign_up_complete,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner

        setSpannableText()
    }

    /**
     * spannable text 설정
     */
    private fun setSpannableText() {
        val titleTextViewSpannable = SpannableStringBuilder("회원가입 완료!")
        titleTextViewSpannable.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.main_yellow)),
            5,
            8,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        binding.tvTitle.text = titleTextViewSpannable
    }

    /**
     *  클릭 리스너 설정
     */
    private fun setOnClickListener() {
        binding.btnStart.setOnClickListener { onClickStartBtn() }
    }

    /**
     * 시작하기 버튼 클릭 시, 학교 인증 액티비티로 이동하고 현재 액티비티 닫기
     */
    private fun onClickStartBtn() {
        val intent = Intent(context, UnivCertificationActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}