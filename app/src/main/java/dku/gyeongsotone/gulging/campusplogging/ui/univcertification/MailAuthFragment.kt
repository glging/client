package dku.gyeongsotone.gulging.campusplogging.ui.univcertification

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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dku.gyeongsotone.gulging.campusplogging.R
import dku.gyeongsotone.gulging.campusplogging.databinding.FragmentMailAuthBinding


class MailAuthFragment : Fragment() {
    companion object {
        private val TAG = this::class.java.name
    }

    private lateinit var binding: FragmentMailAuthBinding
    private val viewModel: MailAuthViewModel by viewModels()

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
            R.layout.fragment_mail_auth,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setSpannableText()
    }

    /** spannable text 설정 */
    private fun setSpannableText() {
        val titleTextViewSpannable = SpannableStringBuilder("학교 메일로 인증하기")
        titleTextViewSpannable.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.main_yellow)),
            0,
            5,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        binding.tvTitle.text = titleTextViewSpannable
    }

    /** 클릭 리스너 설정 */
    private fun setClickListener() {
        binding.btnFinish.setOnClickListener { onClickFinishBtn() }
    }

    /** 인증 완료 화면으로 이동 */
    private fun onClickFinishBtn() {
        findNavController().navigate(
            MailAuthFragmentDirections.actionMailAuthFragmentToUnivCertCompleteFragment()
        )
    }

    /** observer 설정 */
    private fun setObserver() {
        setKeyboardHideObserver()
        setToastMsgObserver()
    }

    private fun setKeyboardHideObserver() {
        viewModel.keyboardHide.observe(viewLifecycleOwner) { hide ->
            if (hide) {
                (requireActivity() as UnivCertificationActivity).keyboardHide()
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