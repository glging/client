package dku.gyeongsotone.gulging.campusplogging.ui.plogging

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import dku.gyeongsotone.gulging.campusplogging.R
import dku.gyeongsotone.gulging.campusplogging.databinding.FragmentPloggingBinding
import dku.gyeongsotone.gulging.campusplogging.databinding.FragmentPloggingFinishBinding
import dku.gyeongsotone.gulging.campusplogging.ui.custom.TrashCountView
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.TRASH_TYPES_KOR


class PloggingFinishFragment : Fragment() {
    companion object {
        private val TAG = this::class.java.name
    }

    private lateinit var binding: FragmentPloggingFinishBinding
    private val viewModel: PloggingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        init(inflater, container)
        (requireActivity() as PloggingActivity).setBackPressable(false)

        return binding.root
    }

    /** binding 설정 */
    private fun init(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_plogging_finish,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }
}