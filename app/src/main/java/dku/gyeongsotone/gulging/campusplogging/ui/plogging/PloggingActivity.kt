package dku.gyeongsotone.gulging.campusplogging.ui.plogging

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import dku.gyeongsotone.gulging.campusplogging.R
import dku.gyeongsotone.gulging.campusplogging.databinding.ActivityPloggingBinding
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.ACTION_SHOW_PLOGGING_FRAGMENT
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.ACTION_STOP_SERVICE
import dku.gyeongsotone.gulging.campusplogging.utils.PloggingServiceUtil.sendCommandToService

class PloggingActivity : AppCompatActivity() {
    companion object {
        private val TAG = this::class.java.name
    }

    private lateinit var binding: ActivityPloggingBinding
    private var backPressable = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.activity_plogging,
            null,
            false
        )

        setContentView(binding.root)
        processIntent(intent)
    }

    /** 플로깅 종료 여부 묻는 다이얼로그 띄우고 결과에 따라 처리 */
    override fun onBackPressed() {
        if (!backPressable) return

        AlertDialog.Builder(this)
            .setMessage("플로깅을 취소하시겠습니까?")
            .setPositiveButton("예") { dialog, _ ->
                sendCommandToService(ACTION_STOP_SERVICE, this)
                dialog.dismiss()
                finish()
            }
            .setNegativeButton("아니요", null)
            .create()
            .show()
    }

    fun setBackPressable(value: Boolean) {
        backPressable = value
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        processIntent(intent)
    }

    private fun processIntent(intent: Intent?) {
        if (intent?.action == ACTION_SHOW_PLOGGING_FRAGMENT) {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController
            navController.navigate(R.id.action_global_to_plogging_fragment)
        }
    }
}

