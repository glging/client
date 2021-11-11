package dku.gyeongsotone.gulging.campusplogging.ui.main.plogging

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dku.gyeongsotone.gulging.campusplogging.R
import dku.gyeongsotone.gulging.campusplogging.databinding.FragmentMainPloggingBinding
import dku.gyeongsotone.gulging.campusplogging.ui.custom.LoadingDialog
import dku.gyeongsotone.gulging.campusplogging.ui.plogging.PloggingActivity
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.SP_USER_ID
import dku.gyeongsotone.gulging.campusplogging.utils.PreferenceUtil.getSpString
import dku.gyeongsotone.gulging.campusplogging.utils.PreferenceUtil.setSpString
import dku.gyeongsotone.gulging.campusplogging.utils.getApplication
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class MainPloggingFragment : Fragment() {

    private lateinit var binding: FragmentMainPloggingBinding
    private val viewModel: MainPloggingViewModel by viewModels()
    private val uiScope = MainScope()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        init(inflater, container)
        restorePloggingDataIfNeed()

        return binding.root
    }


    override fun onResume() {
        super.onResume()

        viewModel.updateData()
    }


    /**
     * 초기 설정
     */
    private fun init(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main_plogging,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setOnClickListener()
        setObserver()
    }


    /**
     * Observer 설정
     */
    private fun setObserver() {
        setToastMsgObserver()
    }


    /**
     * 토스트 메시지 Observer 설정
     */
    private fun setToastMsgObserver() {
        viewModel.toastMsg.observe(viewLifecycleOwner) { msg ->
            if (msg.isNotEmpty()) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    /** 클릭 리스너 설정 */
    private fun setOnClickListener() {
        binding.btnStartPlogging.setOnClickListener { onClickStartPloggingBtn() }
    }


    /**
     * 이전과 다른 userId로 로그인 시,
     * 내장 DB 초기화 후, 새로 데이터 불러오기
     */
    private fun restorePloggingDataIfNeed() {
        val userId = getApplication(requireActivity()).user!!.userId
        if (userId == getSpString(SP_USER_ID)) return

        setSpString(SP_USER_ID, userId)

        uiScope.launch {
            LoadingDialog.showWhileDoJob(
                requireContext(),
                viewModel.restorePloggingData(),
                "플로깅 데이터를 가져오고 있습니다"
            )

            // 새로 가져온 데이터를 기반으로 화면 갱신
            viewModel.updateData()
        }
    }


    /**
     * 플로깅 액티비티로 이동
     */
    private fun onClickStartPloggingBtn() {
        val intent = Intent(requireContext(), PloggingActivity::class.java)
        startActivity(intent)
    }


    companion object {
        private val TAG = this::class.java.name
        private var INSTANCE: MainPloggingFragment? = null

        fun getInstance(): MainPloggingFragment {
            if (INSTANCE == null) INSTANCE = MainPloggingFragment()
            return INSTANCE!!
        }
    }
}