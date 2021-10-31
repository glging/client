package dku.gyeongsotone.gulging.campusplogging.ui.main.ranking

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dku.gyeongsotone.gulging.campusplogging.R
import dku.gyeongsotone.gulging.campusplogging.databinding.FragmentMainPloggingBinding
import dku.gyeongsotone.gulging.campusplogging.databinding.FragmentMainRankingBinding
import dku.gyeongsotone.gulging.campusplogging.ui.plogging.PloggingActivity


class MainRankingFragment : Fragment() {

    private lateinit var binding: FragmentMainRankingBinding
    private val viewModel: MainRankingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        init(inflater, container)
        setOnClickListener()

        return binding.root
    }

    /** binding 설정 */
    private fun init(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main_ranking,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    /** 클릭 리스너 설정 */
    private fun setOnClickListener() {
    }


    companion object {
        private val TAG = this::class.java.name
        private var INSTANCE: MainRankingFragment? = null

        fun getInstance(): MainRankingFragment {
            if (INSTANCE == null) INSTANCE = MainRankingFragment()
            return INSTANCE!!
        }
    }
}