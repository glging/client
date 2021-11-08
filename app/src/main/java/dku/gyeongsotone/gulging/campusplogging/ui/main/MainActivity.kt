package dku.gyeongsotone.gulging.campusplogging.ui.main

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dku.gyeongsotone.gulging.campusplogging.R
import dku.gyeongsotone.gulging.campusplogging.databinding.ActivityMainBinding
import dku.gyeongsotone.gulging.campusplogging.service.PloggingService
import dku.gyeongsotone.gulging.campusplogging.ui.main.challenge.MainChallengeFragment
import dku.gyeongsotone.gulging.campusplogging.ui.main.history.MainHistoryFragment
import dku.gyeongsotone.gulging.campusplogging.ui.main.plogging.MainPloggingFragment
import dku.gyeongsotone.gulging.campusplogging.ui.main.ranking.MainRankingFragment
import dku.gyeongsotone.gulging.campusplogging.ui.plogging.PloggingActivity
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.ACTION_SHOW_PLOGGING_FRAGMENT
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.MAIN_TAB_NAMES
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.REQUEST_CODE_LOCATION_PERMISSION
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.REQUIRE_PERMISSIONS
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    companion object {
        private val TAG = this::class.java.name
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewPagerAdapter: MainViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
        checkPermission()
        Log.d(TAG, "onCreate")
    }

    override fun onStart() {
        super.onStart()

        Log.d(TAG, "onStart")

        checkPloggingServiceStatus()
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    /** binding, view pager, tab layout 설정 */
    private fun init() {
        // view binding
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.activity_main,
            null,
            false
        )
        setContentView(binding.root)

        // set view pager adapter
        mainViewPagerAdapter = MainViewPagerAdapter(this)
        binding.viewPager.adapter = mainViewPagerAdapter

        // connect tab layout and view page, create tab item
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = MAIN_TAB_NAMES[position]
        }.attach()
    }

    private fun checkPermission() {
        val deniedPermissions = mutableListOf<String>()
        for (permission in REQUIRE_PERMISSIONS) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permission)
            }
        }
        if (deniedPermissions.isNotEmpty()) {
            requestPermissions(REQUIRE_PERMISSIONS, REQUEST_CODE_LOCATION_PERMISSION)
        }
    }

    private fun startPermissionsRequestActivity() {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null)
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        results: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, results)
        if (requestCode != REQUEST_CODE_LOCATION_PERMISSION) return
        if (results[0] != PackageManager.PERMISSION_GRANTED) {
            AlertDialog.Builder(this)
                .setMessage("캠퍼스 플로깅앱을 이용하려면 모든 권한을 허용해야 합니다.\n\n백그라운드 위치 권한을 위해 항상 허용으로 설정해주세요.")
                .setNegativeButton("앱 종료") { _, _ -> exitProcess(0) }
                .setPositiveButton("권한 허용하기") { _, _ ->
                    startPermissionsRequestActivity()
                }
                .setCancelable(false)
                .create()
                .show()
        }
    }


    /** 플로깅이 진행중이면 플로깅 화면으로 이동 */
    private fun checkPloggingServiceStatus() {
        if (PloggingService.isRunning) {
            val intent = Intent(this, PloggingActivity::class.java)
            intent.action = ACTION_SHOW_PLOGGING_FRAGMENT
            startActivity(intent)
        }
    }


    /** view pager2 adapter */
    inner class MainViewPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int = MAIN_TAB_NAMES.size

        override fun createFragment(position: Int): Fragment = when (position) {
            0 -> MainPloggingFragment.getInstance()
            1 -> MainChallengeFragment.getInstance()
            2 -> MainHistoryFragment.getInstance()
            3 -> MainRankingFragment.getInstance()

            else -> MainPloggingFragment.getInstance()
        }
    }
}