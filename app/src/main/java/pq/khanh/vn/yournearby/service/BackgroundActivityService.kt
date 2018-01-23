//package pq.khanh.vn.yournearby.service
//
//import android.annotation.SuppressLint
//import android.app.PendingIntent
//import android.app.Service
//import android.content.Intent
//import android.os.Binder
//import android.os.IBinder
//import com.google.android.gms.location.ActivityRecognitionClient
//import pq.khanh.vn.yournearby.extensions.d
//import pq.khanh.vn.yournearby.extensions.showToast
//import pq.khanh.vn.yournearby.utils.Constant
//
///**
// * Created by khanhpq on 1/22/18.
// */
//class BackgroundActivityService : Service() {
//    private var iBinder: Binder = BackgroundActivityService.LocalBinderService
//    private lateinit var intentService: Intent
//    private lateinit var pendingIntent: PendingIntent
//    private lateinit var recogniseClient: ActivityRecognitionClient
//    override fun onCreate() {
//        super.onCreate()
//        recogniseClient = ActivityRecognitionClient(this)
//        intentService = Intent(this, ActivityService::class.java)
//        pendingIntent = PendingIntent.getActivity(this, 1, intentService, PendingIntent.FLAG_UPDATE_CURRENT)
//        requestDetectActivity()
//    }
//
//    override fun onBind(intent: Intent?): IBinder {
//        return iBinder
//    }
//
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        super.onStartCommand(intent, flags, startId)
//        return START_STICKY
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        removeRequestActivity()
//    }
//
//    @SuppressLint("MissingPermission")
//    private fun removeRequestActivity() {
//        recogniseClient.removeActivityUpdates(pendingIntent)
//                .addOnSuccessListener { showToast("success") }
//                .addOnFailureListener { showToast("failed") }
//
//    }
//
//    @SuppressLint("MissingPermission")
//    private fun requestDetectActivity() {
//        recogniseClient.requestActivityUpdates(Constant.DETECT_INTERVAL, pendingIntent)
//                .addOnSuccessListener { d("success") }
//                .addOnFailureListener { d("failed") }
//    }
//
//    inner class LocalBinderService : Binder() {
//        fun getInstance(): BackgroundActivityService {
//            return BackgroundActivityService()
//        }
//    }
//
//}