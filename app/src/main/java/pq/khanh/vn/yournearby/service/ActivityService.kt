package pq.khanh.vn.yournearby.service

import android.app.IntentService
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import com.google.android.gms.location.ActivityRecognitionResult
import com.google.android.gms.location.DetectedActivity
import pq.khanh.vn.yournearby.extensions.e
import pq.khanh.vn.yournearby.utils.Constant

/**
 * Created by khanhpq on 1/22/18.
 */
class ActivityService : IntentService("handlerIntent") {

    override fun onHandleIntent(intent: Intent?) {
        val result = ActivityRecognitionResult.extractResult(intent)
        val maybeActivity = result.probableActivities
        for (activity in maybeActivity) {
            e("Detected activity : ${activity.type}, ${activity.confidence}")
            broadcastActivity(activity)
        }
    }

    private fun broadcastActivity(detectedActivity: DetectedActivity?) {
        val intent = Intent(Constant.BROADCAST_DETECT_ACTIVITY)
        intent.putExtra("type", detectedActivity?.type)
        intent.putExtra("confidence", detectedActivity?.confidence)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

}