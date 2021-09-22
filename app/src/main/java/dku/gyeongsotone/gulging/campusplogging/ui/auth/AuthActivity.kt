package dku.gyeongsotone.gulging.campusplogging.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import androidx.databinding.DataBindingUtil
import dku.gyeongsotone.gulging.campusplogging.R
import dku.gyeongsotone.gulging.campusplogging.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    companion object {
        private val TAG = this::class.java.name
    }

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.activity_auth,
            null,
            false
        )

        InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        setContentView(binding.root)
    }
}