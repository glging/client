package dku.gyeongsotone.gulging.campusplogging.ui.main.plogging

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dku.gyeongsotone.gulging.campusplogging.APP
import dku.gyeongsotone.gulging.campusplogging.R
import dku.gyeongsotone.gulging.campusplogging.databinding.FragmentMainPloggingBinding
import dku.gyeongsotone.gulging.campusplogging.ui.plogging.PloggingActivity


class MainPloggingFragment : Fragment() {

    private lateinit var binding: FragmentMainPloggingBinding
    private val viewModel: MainPloggingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        init(inflater, container)
        setOnClickListener()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateData()
    }

    /** binding 설정 */
    private fun init(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main_plogging,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    /** 클릭 리스너 설정 */
    private fun setOnClickListener() {
        binding.btnStartPlogging.setOnClickListener { onClickStartPloggingBtn() }
    }

    /** 플로깅 액티비티로 이동 */
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