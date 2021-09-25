package dku.gyeongsotone.gulging.campusplogging.ui.plogging

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import dku.gyeongsotone.gulging.campusplogging.R
import dku.gyeongsotone.gulging.campusplogging.databinding.ActivityPloggingBinding
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.ACTION_SHOW_PLOGGING_FRAGMENT

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

    override fun onBackPressed() {
        if (backPressable) super.onBackPressed()
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
            binding.navHostFragment.findNavController().navigate(
                R.id.action_global_to_plogging_fragment
            )
        }
    }
}

