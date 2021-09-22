package dku.gyeongsotone.gulging.campusplogging.ui.plogging

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dku.gyeongsotone.gulging.campusplogging.R
import dku.gyeongsotone.gulging.campusplogging.databinding.FragmentWaitingBinding


class WaitingFragment : Fragment() {
    companion object {
        private val TAG = this::class.java.name
    }

    private lateinit var binding: FragmentWaitingBinding
    private lateinit var timer: CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        init(inflater, container)
        setCountDownTimer()
        (requireActivity() as PloggingActivity).setBackPressable(true)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer.cancel()
    }

    /** binding 설정 */
    private fun init(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_waiting,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
    }

    /** count down 타이머 설정 */
    private fun setCountDownTimer() {
        timer = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (millisUntilFinished != 0L) {
                    binding.tvCount.text = (millisUntilFinished / 1000 + 1).toInt().toString()
                }
            }

            override fun onFinish() {
                navigateToPloggingFragment()
            }
        }
        timer.start()
    }

    /** 플로깅 프래그먼트로 이동 */
    private fun navigateToPloggingFragment() {
        findNavController().navigate(
            WaitingFragmentDirections.actionWaitingFragmentToPloggingFragment()
        )
    }
}