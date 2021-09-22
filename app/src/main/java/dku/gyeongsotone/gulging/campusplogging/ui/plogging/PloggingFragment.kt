package dku.gyeongsotone.gulging.campusplogging.ui.plogging

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dku.gyeongsotone.gulging.campusplogging.R
import dku.gyeongsotone.gulging.campusplogging.databinding.FragmentPloggingBinding
import dku.gyeongsotone.gulging.campusplogging.service.PloggingService
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.ACTION_PAUSE_SERVICE
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.ACTION_START_OR_RESUME_SERVICE
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.ACTION_STOP_SERVICE
import dku.gyeongsotone.gulging.campusplogging.utils.mToKm
import dku.gyeongsotone.gulging.campusplogging.utils.msToMinute
import java.util.concurrent.TimeUnit

class PloggingFragment : Fragment() {
    companion object {
        private val TAG = this::class.java.name
    }

    private lateinit var binding: FragmentPloggingBinding
    private val viewModel: PloggingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        init(inflater, container)
        setOnClickListener()
        setObserver()
        (requireActivity() as PloggingActivity).setBackPressable(false)

        return binding.root
    }

    /** binding 설정 */
    private fun init(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_plogging,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    /** 클릭 리스너 설정 */
    private fun setOnClickListener() {}

    /** observer 설정 */
    private fun setObserver() {
        setPloggingStatusObserver()
        setPloggingServiceTimeObserver()
        setPloggingServiceDistanceObserver()
    }

    /** 플로깅 상태에 따라 처리 */
    private fun setPloggingStatusObserver() {
        viewModel.ploggingStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                PloggingStatus.START_OR_RESUME -> {
                    sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
                    binding.btnPause.isVisible = true
                    binding.layoutStopAndResume.isVisible = false
                }
                PloggingStatus.PAUSE -> {
                    sendCommandToService(ACTION_PAUSE_SERVICE)
                    binding.btnPause.isVisible = false
                    binding.layoutStopAndResume.isVisible = true
                }
                PloggingStatus.STOP -> {
                    sendCommandToService(ACTION_STOP_SERVICE)
                    findNavController().navigate(
                        PloggingFragmentDirections.actionPloggingFragmentToTrashInputFragment()
                    )
                }
            }
        }
    }

    private fun setPloggingServiceDistanceObserver() {
        PloggingService.distanceInMeters.observe(viewLifecycleOwner) { meter ->
            viewModel.distance.set(meter.mToKm())
        }
    }

    private fun setPloggingServiceTimeObserver() {
        PloggingService.timeInMillis.observe(viewLifecycleOwner) { ms ->
            Log.d(TAG, "current seconds: ${TimeUnit.MILLISECONDS.toSeconds(ms)}")
            viewModel.time.set(ms.msToMinute().toInt())
        }
    }

    /** 서비스 상태 변경 */
    private fun sendCommandToService(action: String) {
        val intent = Intent(requireContext(), PloggingService::class.java)
        intent.action = action
        requireContext().startService(intent)
    }
}