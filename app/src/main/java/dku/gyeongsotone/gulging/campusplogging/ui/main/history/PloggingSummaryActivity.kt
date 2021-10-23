package dku.gyeongsotone.gulging.campusplogging.ui.main.history

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.drawToBitmap
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import dku.gyeongsotone.gulging.campusplogging.R
import dku.gyeongsotone.gulging.campusplogging.data.repository.PloggingRepository
import dku.gyeongsotone.gulging.campusplogging.databinding.ActivityPloggingSummaryBinding
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.EXTRA_PLOGGING_ID
import dku.gyeongsotone.gulging.campusplogging.utils.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class PloggingSummaryActivity : AppCompatActivity() {
    companion object {
        private val TAG = this::class.java.name
    }

    private lateinit var binding: ActivityPloggingSummaryBinding
    private val repository = PloggingRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
        processIntent()
        setClickListener()
    }

    private fun init() {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.activity_plogging_summary,
            null,
            false
        )

        setContentView(binding.root)
    }

    /** intent에서 플로깅의 id 받고, 플로깅 set */
    private fun processIntent() {
        val ploggingId = intent.extras!!.getInt(EXTRA_PLOGGING_ID)

        CoroutineScope(Dispatchers.IO).launch {
            val plogging = repository.getPlogging(ploggingId)
            Log.d(TAG, "received plogging: $plogging")

            withContext(Dispatchers.Main) {
                if (plogging == null) {
                    showToast(this@PloggingSummaryActivity, "유효하지 않은 플로깅 데이터입니다.")
                    finish()
                    return@withContext
                }

                binding.plogging = plogging
            }
        }
    }


    /** 클릭 리스너 설정 */
    private fun setClickListener() {
        binding.layoutPloggingSummary.btnShare.setOnClickListener { onShareBtnClick() }
        binding.layoutPloggingSummary.btnExit.setOnClickListener { onExitBtnClick() }
    }

    private fun onShareBtnClick() {
        val bitmap = getBitmapFromView(binding)
        val file = File.createTempFile("picture_", ".png")
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)

        val uri =
            FileProvider.getUriForFile(this, "fileprovider", file)

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, uri)
            type = "image/jpeg"
        }

        val shareIntent = Intent.createChooser(sendIntent, "플로깅 기록을 공유하세요.")
        startActivity(shareIntent)
    }

    private fun getBitmapFromView(binding: ActivityPloggingSummaryBinding): Bitmap {
        val bitmap: Bitmap?

        binding.layoutPloggingSummary.btnShare.isVisible = false
        binding.layoutPloggingSummary.btnExit.isVisible = false
        bitmap = binding.layoutPloggingSummary.layout.drawToBitmap()
        binding.layoutPloggingSummary.btnShare.isVisible = true
        binding.layoutPloggingSummary.btnExit.isVisible = true

        return bitmap
    }

    private fun onExitBtnClick() {
        Log.d(TAG, "onExitBtnClicked!!!")
        finish()
    }
}