package dku.gyeongsotone.gulging.campusplogging.ui.main.history

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.drawToBitmap
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import dku.gyeongsotone.gulging.campusplogging.R
import dku.gyeongsotone.gulging.campusplogging.data.repository.CamploRepository
import dku.gyeongsotone.gulging.campusplogging.data.repository.PloggingRepository
import dku.gyeongsotone.gulging.campusplogging.databinding.ActivityPloggingDetailBinding
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.EXTRA_PLOGGING_ID
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.SP_TOKEN
import dku.gyeongsotone.gulging.campusplogging.utils.PreferenceUtil.getSpString
import dku.gyeongsotone.gulging.campusplogging.utils.showToast
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream

class PloggingDetailActivity : AppCompatActivity() {
    companion object {
        private val TAG = this::class.java.name
    }

    private lateinit var binding: ActivityPloggingDetailBinding
    private val repository = PloggingRepository
    private val uiScope = MainScope()
    private var ploggingId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
        processIntent()
    }

    /**
     * 초기 설정
     */
    private fun init() {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.activity_plogging_detail,
            null,
            false
        )
        binding.layoutPloggingDetail.btnShare.isGone = true

        setContentView(binding.root)
        setClickListener()
    }

    /**
     * intent에서 플로깅의 id 받고, 플로깅 set
     */
    private fun processIntent() {
        ploggingId = intent.extras!!.getInt(EXTRA_PLOGGING_ID)

        uiScope.launch {
            val plogging = repository.getPlogging(ploggingId!!)
            Log.d(TAG, "received plogging: $plogging")

            if (plogging == null) {
                showToast(this@PloggingDetailActivity, "유효하지 않은 플로깅 데이터입니다.")
                finish()
                return@launch
            }

            binding.plogging = plogging
        }
    }


    /**
     * 클릭 리스너 설정
     */
    private fun setClickListener() {
        binding.layoutPloggingDetail.btnMenu.setOnClickListener { onMenuBtnClick() }
        binding.layoutPloggingDetail.btnExit.setOnClickListener { finish() }
    }

    /**
     * 햄버거 버튼 클릭 시, 메뉴 보여주기
     */
    private fun onMenuBtnClick() {
        val popupMenu = PopupMenu(this, binding.layoutPloggingDetail.btnMenu)
        popupMenu.inflate(R.menu.menu_plogging_detail)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.share -> sharePlogging()
                R.id.delete -> deletePlogging()
            }
            false
        }
        popupMenu.show()
    }

    /**
     * 플로깅 디테일 화면을 bitmap으로 변환해서 공유
     */
    private fun sharePlogging() {
        val bitmap = getBitmapFromView(binding)
        val file = File.createTempFile("picture_", ".jpeg")
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

    /**
     * 플로깅 데이터 삭제
     */
    private fun deletePlogging() = uiScope.launch {
        val token = getSpString(SP_TOKEN)!!

        repository.deleteById(ploggingId!!)
        CamploRepository.deletePlogging(token, ploggingId!!)
        showToast(this@PloggingDetailActivity, "삭제되었습니다")
        finish()
    }

    /**
     * 현재 화면을 bitmpa으로 변환해서 리턴
     */
    private fun getBitmapFromView(binding: ActivityPloggingDetailBinding): Bitmap {
        val bitmap: Bitmap?

        binding.layoutPloggingDetail.btnMenu.isVisible = false
        binding.layoutPloggingDetail.btnExit.isVisible = false
        bitmap = binding.layoutPloggingDetail.layout.drawToBitmap()
        binding.layoutPloggingDetail.btnMenu.isVisible = true
        binding.layoutPloggingDetail.btnExit.isVisible = true

        return bitmap
    }
}