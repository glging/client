package dku.gyeongsotone.gulging.campusplogging.ui.plogging

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.view.drawToBitmap
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dku.gyeongsotone.gulging.campusplogging.R
import dku.gyeongsotone.gulging.campusplogging.databinding.FragmentPloggingFinishBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream


class PloggingFinishFragment : Fragment() {
    companion object {
        private val TAG = this::class.java.name
    }

    private lateinit var binding: FragmentPloggingFinishBinding
    private val viewModel: PloggingViewModel by activityViewModels()
    private val uiScope = MainScope()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel.setBadges()
        uiScope.launch { viewModel.savePloggingData() }
        init(inflater, container)
        showTrashCount()
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
        binding.layoutPloggingDetail.btnMenu.isGone = true

        setClickListener()
        setObserver()
    }

    private fun setObserver() {
        setToastMsgObserver()
    }

    private fun setToastMsgObserver() {
        viewModel.toastMsg.observe(viewLifecycleOwner) { msg ->
            if (msg.isNotEmpty()) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    /** 클릭 리스너 설정 */
    private fun setClickListener() {
        binding.layoutPloggingDetail.btnShare.setOnClickListener { sharePlogging() }
        binding.layoutPloggingDetail.btnExit.setOnClickListener { requireActivity().finish() }
    }


    private fun sharePlogging() {
        val bitmap = getBitmapFromView(binding)
        val file = File.createTempFile("picture_", ".jpeg")
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

        binding.layoutPloggingDetail.btnShare.isVisible = false
        binding.layoutPloggingDetail.btnExit.isVisible = false
        bitmap = binding.layoutPloggingDetail.layout.drawToBitmap()
        binding.layoutPloggingDetail.btnShare.isVisible = true
        binding.layoutPloggingDetail.btnExit.isVisible = true

        return bitmap
    }


    /** 쓰레기 합계 계산 후 set text */
    private fun showTrashCount() {
        var total = 0
        total += viewModel.plastics.get()
        total += viewModel.vinyls.get()
        total += viewModel.glasses.get()
        total += viewModel.cans.get()
        total += viewModel.papers.get()
        total += viewModel.generals.get()
        binding.layoutPloggingDetail.tvTrashCount.text = resources.getString(R.string.count, total)
    }
}