package dku.gyeongsotone.gulging.campusplogging.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dku.gyeongsotone.gulging.campusplogging.R
import dku.gyeongsotone.gulging.campusplogging.databinding.ActivityMainBinding
import dku.gyeongsotone.gulging.campusplogging.ui.main.plogging.MainPloggingFragment
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.MAIN_TAB_NAMES
import dku.gyeongsotone.gulging.campusplogging.utils.PermissionsUtil
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    companion object {
        private val TAG = this::class.java.name
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewPagerAdapter: MainViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
        requestPermissions()
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


    /** 권한 요청 */
    private fun requestPermissions() {
        if (PermissionsUtil.checkPermissions(this)) return
        PermissionsUtil.requestPermissions(this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermissions()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, true)
    }

    /**
     * view pager2 adapter
     */
    inner class MainViewPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int = MAIN_TAB_NAMES.size

        override fun createFragment(position: Int): Fragment = when (position) {
            0 -> MainPloggingFragment.getInstance()
            else -> MainPloggingFragment.getInstance()
        }
    }
}