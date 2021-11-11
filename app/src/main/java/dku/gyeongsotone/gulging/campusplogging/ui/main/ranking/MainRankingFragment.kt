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

        return binding.root
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

        // swipe refresh 설정
        binding.swipeRefreshLayout.setOnRefreshListener {
            uiScope.launch {
                viewModel.updateData().join()
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }

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