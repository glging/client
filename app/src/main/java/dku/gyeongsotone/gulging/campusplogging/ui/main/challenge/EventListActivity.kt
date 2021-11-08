package dku.gyeongsotone.gulging.campusplogging.ui.main.challenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import androidx.databinding.DataBindingUtil
import dku.gyeongsotone.gulging.campusplogging.R
import dku.gyeongsotone.gulging.campusplogging.databinding.ActivityAuthBinding
import dku.gyeongsotone.gulging.campusplogging.databinding.ActivityEventListBinding

class EventListActivity : AppCompatActivity() {
    companion object {
        private val TAG = this::class.java.name
    }

    private lateinit var binding: ActivityEventListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    /**
     * 화면 초기화
     */
    private fun init() {
        // 바인딩 설정
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.activity_event_list,
            null,
            false
        )
        setContentView(binding.root)

        // 클릭 리스너 설정
        setClickListener()
    }

    /**
     * 클릭 리스너 설정
     */
    private fun setClickListener() {
        binding.btnExit.setOnClickListener { onBackPressed() }
    }
}