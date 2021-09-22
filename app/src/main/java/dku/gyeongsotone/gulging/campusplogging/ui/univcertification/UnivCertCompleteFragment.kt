package dku.gyeongsotone.gulging.campusplogging.ui.univcertification

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import dku.gyeongsotone.gulging.campusplogging.R
import dku.gyeongsotone.gulging.campusplogging.databinding.FragmentUnivCertCompleteBinding
import dku.gyeongsotone.gulging.campusplogging.databinding.FragmentUnivCertStartBinding
import dku.gyeongsotone.gulging.campusplogging.ui.main.MainActivity

class UnivCertCompleteFragment : Fragment() {
    companion object {
        private val TAG = this::class.java.name
    }

    private lateinit var binding: FragmentUnivCertCompleteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        init(inflater, container)
        setOnClickListener()

        return binding.root
    }

    /** binding, spannable text 설정 */
    private fun init(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_univ_cert_complete,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner

        setSpannableText()
    }

    /** spannable text 설정 */
    private fun setSpannableText() {
        val titleTextViewSpannable = SpannableStringBuilder("재학생 인증 완료!")
        titleTextViewSpannable.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.main_yellow)),
            7,
            10,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        binding.tvTitle.text = titleTextViewSpannable
    }

    /** 클릭 리스너 설정 */
    private fun setOnClickListener() {
        binding.btnStart.setOnClickListener { onClickStartBtn() }
    }

    /** 시작하기 버튼 클릭 시, 메인 액티비티로 이동하고 현재 액티비티 닫기*/
    private fun onClickStartBtn() {
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}