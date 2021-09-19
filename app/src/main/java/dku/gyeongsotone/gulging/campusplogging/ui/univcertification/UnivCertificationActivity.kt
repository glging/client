package dku.gyeongsotone.gulging.campusplogging.ui.univcertification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import dku.gyeongsotone.gulging.campusplogging.R
import dku.gyeongsotone.gulging.campusplogging.databinding.ActivityAuthBinding
import dku.gyeongsotone.gulging.campusplogging.databinding.ActivityUnivCertificationBinding

class UnivCertificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUnivCertificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.activity_univ_certification,
            null,
            false
        )

        setContentView(binding.root)
    }
}