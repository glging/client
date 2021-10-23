package dku.gyeongsotone.gulging.campusplogging.ui.main.challenge

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
        setClickListener()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.updateChallengeStatuses()
    }

    /** binding 설정 */
    private fun init(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main_challenge,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun setClickListener() {
        binding.layoutFreshmanChallenge.ivBtnSetHide.setOnClickListener { onFreshmanHideBtnClick() }
        binding.layoutSophomoreJuniorChallenge.ivBtnSetHide.setOnClickListener { onSophomoreJuniorHideBtnClick() }
        binding.layoutSeniorChallenge.ivBtnSetHide.setOnClickListener { onSeniorHideBtnClick() }
    }

    private fun onFreshmanHideBtnClick() {
        val curValue = viewModel.freshmanChallengeHide.get()
        viewModel.freshmanChallengeHide.set(!curValue)
    }

    private fun onSophomoreJuniorHideBtnClick() {
        val curValue = viewModel.sophomoreJuniorChallengeHide.get()
        viewModel.sophomoreJuniorChallengeHide.set(!curValue)
    }

    private fun onSeniorHideBtnClick() {
        val curValue = viewModel.seniorChallengeHide.get()
        viewModel.seniorChallengeHide.set(!curValue)
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