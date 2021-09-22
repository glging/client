package dku.gyeongsotone.gulging.campusplogging.ui.plogging

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import dku.gyeongsotone.gulging.campusplogging.R
import dku.gyeongsotone.gulging.campusplogging.data.local.model.Plogging
import dku.gyeongsotone.gulging.campusplogging.databinding.ActivityPloggingBinding
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
    }

    override fun onBackPressed() {
        if (backPressable)
            super.onBackPressed()
    }

    fun setBackPressable(value: Boolean) {
        backPressable = value
    }
}

