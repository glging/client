package dku.gyeongsotone.gulging.campusplogging.ui.main.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dku.gyeongsotone.gulging.campusplogging.APP
import dku.gyeongsotone.gulging.campusplogging.R
import dku.gyeongsotone.gulging.campusplogging.databinding.FragmentMainHistoryBinding

class MainHistoryFragment : Fragment() {

    private lateinit var binding: FragmentMainHistoryBinding
    private val viewModel: MainHistoryViewModel by viewModels() {
        MainHistoryViewModelFactory(getApplication().user!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        init(inflater, container)

        return binding.root
    }

    /** binding 설정 */
    private fun init(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main_history,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun getApplication(): APP {
        return requireActivity().application as APP
    }

    companion object {
        private val TAG = this::class.java.name
        private var INSTANCE: MainHistoryFragment? = null

        fun getInstance(): MainHistoryFragment {
            if (INSTANCE == null) INSTANCE = MainHistoryFragment()
            return INSTANCE!!
        }
    }
}