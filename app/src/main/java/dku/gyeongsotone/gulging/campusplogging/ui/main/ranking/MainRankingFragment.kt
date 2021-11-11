package dku.gyeongsotone.gulging.campusplogging.ui.main.ranking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dku.gyeongsotone.gulging.campusplogging.R
import dku.gyeongsotone.gulging.campusplogging.databinding.FragmentMainRankingBinding
import dku.gyeongsotone.gulging.campusplogging.ui.custom.LoadingDialog
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class MainRankingFragment : Fragment() {

    private lateinit var binding: FragmentMainRankingBinding
    private val viewModel: MainRankingViewModel by viewModels()
    private val uiScope = MainScope()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        init(inflater, container)
        getRankingInfo()

        return binding.root
    }


    private fun getRankingInfo() {
        uiScope.launch {
            LoadingDialog.showWhileDoJob(
                requireContext(),
                viewModel.updateData(),
                "랭킹 정보를 불러오고 있습니다"
            )
        }
    }


    override fun onResume() {
        super.onResume()

        viewModel.updateData()
    }


    /**
     * 화면 초기화
     */
    private fun init(inflater: LayoutInflater, container: ViewGroup?) {
        // binding 설정
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main_ranking,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setObserver()   // Observer 설정
    }


    /**
     * observer 설정
     */
    private fun setObserver() {
        setToastMsgObserver()
    }


    /**
     * 토스트 메시지 observer 설정
     */
    private fun setToastMsgObserver() {
        viewModel.toastMsg.observe(viewLifecycleOwner) { msg ->
            if (msg.isNotEmpty()) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }
        }
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