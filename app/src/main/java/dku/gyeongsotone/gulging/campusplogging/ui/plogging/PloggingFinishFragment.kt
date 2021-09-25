package dku.gyeongsotone.gulging.campusplogging.ui.plogging

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.core.view.drawToBitmap
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dku.gyeongsotone.gulging.campusplogging.CampusPloggingApplication
import dku.gyeongsotone.gulging.campusplogging.R
import dku.gyeongsotone.gulging.campusplogging.data.local.dao.PloggingDao
import dku.gyeongsotone.gulging.campusplogging.data.local.model.Plogging
import dku.gyeongsotone.gulging.campusplogging.databinding.FragmentPloggingFinishBinding
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.SP_LEVEL
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.SP_TOTAL_DISTANCE
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.UNIV_DISTANCE
import dku.gyeongsotone.gulging.campusplogging.utils.PreferenceUtil.getSpDouble
import dku.gyeongsotone.gulging.campusplogging.utils.PreferenceUtil.getSpInt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import kotlin.math.floor


class PloggingFinishFragment : Fragment() {
    companion object {
        private val TAG = this::class.java.name
    }

    private lateinit var binding: FragmentPloggingFinishBinding
    private val viewModel: PloggingViewModel by activityViewModels()
    private lateinit var ploggingDao: PloggingDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        ploggingDao =
            (requireActivity().application as CampusPloggingApplication).database.ploggingDao()
        init(inflater, container)
        setClickListener()
        setBadge()
        setTrashCount()
        saveOnDatabase()
        (requireActivity() as PloggingActivity).setBackPressable(false)

        return binding.root
    }

    /** binding 설정 */
    private fun init(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_plogging_finish,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    /** 클릭 리스너 설정 */
    private fun setClickListener() {
        binding.btnShare.setOnClickListener { onShareBtnClick() }
        binding.btnExit.setOnClickListener { onExitBtnClick() }
    }

    private fun onShareBtnClick() {
        val bitmap = getBitmapFromView(binding)
        val file = File.createTempFile("picture_", ".png")
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)

        val uri =
            FileProvider.getUriForFile(requireContext(), "fileprovider", file)

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, uri)
            type = "image/jpeg"
        }

        val shareIntent = Intent.createChooser(sendIntent, "플로깅 기록을 공유하세요.")
        startActivity(shareIntent)
    }

    private fun getBitmapFromView(binding: FragmentPloggingFinishBinding): Bitmap {
        val bitmap: Bitmap?

        binding.btnShare.isVisible = false
        binding.btnExit.isVisible = false
        bitmap = binding.layout.drawToBitmap()
        binding.btnShare.isVisible = true
        binding.btnExit.isVisible = true

        return bitmap
    }

    private fun onExitBtnClick() {
        Log.d(TAG, "onExitBtnClicked!!!")
        requireActivity().finish()
    }

    private fun setBadge() {
        val preTotalDistance = getSpDouble(SP_TOTAL_DISTANCE)
        val curTotalDistance = preTotalDistance + viewModel.distance.get()

        val preLevel = getSpInt(SP_LEVEL)
        val curLevel = floor(curTotalDistance / UNIV_DISTANCE).toInt()

        viewModel.badge.set(curLevel - preLevel)
    }

    /** 쓰레기 합계 계산 후 set text */
    private fun setTrashCount() {
        var total = 0
        total += viewModel.plastics.get()
        total += viewModel.vinyls.get()
        total += viewModel.glasses.get()
        total += viewModel.cans.get()
        total += viewModel.papers.get()
        total += viewModel.generals.get()
        binding.tvTrashCount.text = resources.getString(R.string.count, total)
    }

    /** 플로깅 기록을 DB에 저장 */
    private fun saveOnDatabase() {
        val plogging = Plogging(
            startDate = viewModel.startDate.get(),
            endDate = viewModel.endDate.get(),
            distance = viewModel.distance.get(),
            time = viewModel.time.get(),
            badge = viewModel.badge.get(),
            picture = viewModel.picture!!,
            plastic = viewModel.plastics.get(),
            vinyl = viewModel.vinyls.get(),
            glass = viewModel.glasses.get(),
            can = viewModel.cans.get(),
            paper = viewModel.papers.get(),
            general = viewModel.generals.get()
        )

        CoroutineScope(Dispatchers.IO).launch {
            ploggingDao.insert(plogging)
        }
    }
}