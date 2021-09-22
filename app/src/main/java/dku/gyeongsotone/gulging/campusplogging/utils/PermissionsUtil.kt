package dku.gyeongsotone.gulging.campusplogging.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Build
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.REQUEST_CODE_LOCATION_PERMISSION
import pub.devrel.easypermissions.EasyPermissions

object PermissionsUtil {
    /** 권한 정보 체크 */
    fun checkPermissions(context: Context) =
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.hasPermissions(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        } else {
            EasyPermissions.hasPermissions(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }

    /** 권한 요청 */
    fun requestPermissions(activity: Activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                activity,
                "플로깅 기능을 사용하려면 위치 액세스 권한 허용에 동의해야 합니다.",
                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        } else {
            EasyPermissions.requestPermissions(
                activity,
                "플로깅 기능을 사용하려면 위치 액세스 권한 허용에 동의해야 합니다.",
                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }
    }
}