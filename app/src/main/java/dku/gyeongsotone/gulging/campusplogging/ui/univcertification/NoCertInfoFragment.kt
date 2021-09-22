package dku.gyeongsotone.gulging.campusplogging.ui.univcertification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dku.gyeongsotone.gulging.campusplogging.R
import dku.gyeongsotone.gulging.campusplogging.databinding.FragmentNoCertInfoBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class NoCertInfoFragment : Fragment() {
    companion object {
        private val TAG = this::class.java.name
    }

    private lateinit var binding: FragmentNoCertInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        init(inflater, container)
        setClickListener()

        return binding.root
    }

    /** binding 설정 */
    private fun init(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_no_cert_info,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
    }

    /** 클릭 리스너 설정 */
    private fun setClickListener() {
        binding.btnStart.setOnClickListener { onClickStartBtn() }
    }

    /** 학교 인증창으로 이동 */
    private fun onClickStartBtn() {
        findNavController().navigate(
            NoCertInfoFragmentDirections.actionNoCertInfoFragmentToUnivCertStartFragment()
        )
    }
}
