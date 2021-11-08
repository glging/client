package dku.gyeongsotone.gulging.campusplogging.ui.main.challenge

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dku.gyeongsotone.gulging.campusplogging.R
import dku.gyeongsotone.gulging.campusplogging.databinding.FragmentMainChallengeBinding


class MainChallengeFragment : Fragment() {

    private lateinit var binding: FragmentMainChallengeBinding
    private val viewModel: MainChallengeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        init(inflater, container)

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.updateChallengeStatuses()
    }

    /**
     * 화면 초기화
     */
    private fun init(inflater: LayoutInflater, container: ViewGroup?) {
        // 바인딩 설정
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main_challenge,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // 클릭 리스너 설정
        setClickListener()
    }

    /**
     * 클릭 리스너 등록
     */
    private fun setClickListener() {
        binding.btnMore.setOnClickListener { showEventListActivity() }
    }

    /**
     * 특강 목록을 보여주는 액티비티 띄우기
     */
    private fun showEventListActivity() {
        val intent = Intent(requireContext(), EventListActivity::class.java)
        startActivity(intent)
    }


    companion object {
        private val TAG = this::class.java.name
        private var INSTANCE: MainChallengeFragment? = null

        fun getInstance(): MainChallengeFragment {
            if (INSTANCE == null) INSTANCE = MainChallengeFragment()
            return INSTANCE!!
        }
    }
}