package dku.gyeongsotone.gulging.campusplogging.ui.univcertification

import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.BulletSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dku.gyeongsotone.gulging.campusplogging.R
import dku.gyeongsotone.gulging.campusplogging.databinding.FragmentUnivCertStartBinding


class UnivCertStartFragment : Fragment() {
    companion object {
        private val TAG = this::class.java.name
    }

    private lateinit var binding: FragmentUnivCertStartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        init(inflater, container)
        setClickListener()

        return binding.root
    }

    /** binding, spannable text 설정 */
    private fun init(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_univ_cert_start,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner

        setSpannableText()
    }

    /** spannable text 설정 */
    private fun setSpannableText() {
        // 타이틀
        val titleTextViewSpannable = SpannableStringBuilder("당신의 학교는 어디인가요?")
        titleTextViewSpannable.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.main_yellow)),
            4,
            6,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        binding.tvTitle.text = titleTextViewSpannable

        // 학교 인증 안내 디테일
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            val infoDetailTextViewSpannable = SpannableStringBuilder(
                "학번, 웹메일 도용 등 올바르지 않은 경로를 통해 인증을 시도할 경우, 관련 법에 따라 법적 책임이 따를 수 있습니다. \n" +
                        "아래 두 가지 방법 중 하나를 선택하여 인증할 수 있습니다. 학교 포털 스크린을 이용한 인증은 위조 가능성이 높아 인증 수단으로 사용하지 않습니다. "
            )
            infoDetailTextViewSpannable.setSpan(
                BulletSpan(20, ContextCompat.getColor(requireContext(), R.color.black), 5),
                0,
                67,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            infoDetailTextViewSpannable.setSpan(
                BulletSpan(20, ContextCompat.getColor(requireContext(), R.color.black), 5),
                68,
                151,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            binding.tvInfoDetail.text = infoDetailTextViewSpannable
        }
    }

    /** 클릭 리스너 설정 */
    private fun setClickListener() {
        binding.btnUsingMail.setOnClickListener { onClickUsingMailBtn() }
        binding.btnUsingStudentCard.setOnClickListener { onClickUsingStudentCardBtn() }
    }


    /** 학교 메일로 인증하기 창으로 이동 */
    private fun onClickUsingMailBtn() {
        findNavController().navigate(
            UnivCertStartFragmentDirections.actionUnivCertStartFragmentToMailAuthFragment()
        )
    }


    /** 학생증으로 인증하기 창으로 이동 */
    private fun onClickUsingStudentCardBtn() {
//        findNavController().navigate(
//            UnivCertStartFragmentDirections.actionUnivCertStartFragmentToStudentCardAuthFragment()
//        )
        showToast("준비중입니다. 학교 메일로 인증해주세요.")
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}