package dku.gyeongsotone.gulging.campusplogging.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import dku.gyeongsotone.gulging.campusplogging.R
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.ACTION_PAUSE_SERVICE
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.ACTION_START_OR_RESUME_SERVICE
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.ACTION_STOP_SERVICE
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.FASTEST_LOCATION_INTERVAL
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.LOCATION_UPDATE_INTERVAL
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.NOTIFICATION_CHANNEL_ID
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.NOTIFICATION_CHANNEL_NAME
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.NOTIFICATION_ID
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.TIMER_UPDATE_INTERVAL
import dku.gyeongsotone.gulging.campusplogging.utils.PermissionsUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class PloggingService : LifecycleService() {
    companion object {
        private val TAG = this::class.java.name
        val isPlogging = MutableLiveData<Boolean>()
        val timeInMillis = MutableLiveData<Long>()
        val distanceInMeters = MutableLiveData<Double>()
    }

    var isFirstRun = true
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate() {
        super.onCreate()

        postInitialValues()
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this)

        isPlogging.observe(this) {
            if (it == true) setLocationUpdateCallback()
            else removeLocationUpdateCallback()
        }
    }

    /** companion object 변수들 초기화 */
    private fun postInitialValues() {
        isPlogging.postValue(false)
        timeInMillis.postValue(0L)
        distanceInMeters.postValue(0.0)
    }

    /** service에 전달된 command에 따라 서비스 시작/일시정지/중단 */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action) {
                ACTION_START_OR_RESUME_SERVICE -> {
                    if (isFirstRun) {
                        startForegroundService()
                        isFirstRun = false
                    } else {
                        startTimer()
                    }
                }
                ACTION_PAUSE_SERVICE -> pauseService()
                ACTION_STOP_SERVICE -> killService()
                else -> null
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    /** 플로깅 진행 시간 계산 */
    private var isTimerEnabled = false
    private var lapTime = 0L
    private var timeRun = 0L
    private var timeStarted = 0L

    private fun startTimer() {
        isPlogging.postValue(true)
        timeStarted = System.currentTimeMillis()
        isTimerEnabled = true

        CoroutineScope(Dispatchers.Main).launch {
            while (isPlogging.value == true) {
                Log.d(TAG, "lab time (sec): ${TimeUnit.MILLISECONDS.toSeconds(lapTime)}")
                lapTime = System.currentTimeMillis() - timeStarted
                timeInMillis.postValue(timeRun + lapTime)
                delay(TIMER_UPDATE_INTERVAL)
            }
            timeRun += lapTime
        }
    }

    @SuppressLint("MissingPermission")
    private fun setLocationUpdateCallback() {
        val locationRequest = LocationRequest.create().apply {
            interval = LOCATION_UPDATE_INTERVAL
            fastestInterval = FASTEST_LOCATION_INTERVAL
            priority = PRIORITY_HIGH_ACCURACY
        }

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun removeLocationUpdateCallback() {
        preLocation = null
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    /** location 변화에 따라 거리 계산 */
    var preLocation: Location? = null
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)

            for (location in result.locations) {
                if (preLocation == null) {
                    preLocation = location
                } else {
                    val distance = FloatArray(1)
                    Location.distanceBetween(
                        preLocation!!.latitude,
                        preLocation!!.longitude,
                        location.latitude,
                        location.longitude,
                        distance
                    )
                    preLocation = location
                    Log.d(TAG, "distance diff: ${distance[0]}")
                    distanceInMeters.postValue((distanceInMeters.value ?: 0.0) + distance[0])
                }
            }

        }
    }

    /** foreground service 시작 */
    private fun startForegroundService() {
        startTimer()
        isPlogging.postValue(true)

        val notificationManager = ContextCompat.getSystemService(
            this,
            NotificationManager::class.java
        ) as NotificationManager

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(false)
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_logo)
            .setContentText("플로깅이 진행중입니다.")

        startForeground(NOTIFICATION_ID, notificationBuilder.build())
    }

    /** service 일시 중지 */
    private fun pauseService() {
        isPlogging.postValue(false)
        isTimerEnabled = false
    }

    /** service 중단 */
    private fun killService() {
        isFirstRun = true
        pauseService()
        stopForeground(true)
        stopSelf()
    }

    /** 플러깅 notification 채널 생성 */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }

    /** 앱이 강제종료될 때 서비스도 종료 */
    override fun onTaskRemoved(rootIntent: Intent?) {
        killService()
        super.onTaskRemoved(rootIntent)
    }
}