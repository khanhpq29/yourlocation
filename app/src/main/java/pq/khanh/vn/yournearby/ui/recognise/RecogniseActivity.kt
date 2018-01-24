package pq.khanh.vn.yournearby.ui.recognise

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.DetectedActivity
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_recognise.*
import pq.khanh.vn.yournearby.R
import pq.khanh.vn.yournearby.extensions.startIntent
//import pq.khanh.vn.yournearby.service.BackgroundActivityService
import pq.khanh.vn.yournearby.service.BackgroundDetectedActivitiesService
import pq.khanh.vn.yournearby.ui.example.DemoActivity
import pq.khanh.vn.yournearby.utils.Constant
import pq.khanh.vn.yournearby.utils.pref.AppReference

class RecogniseActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private lateinit var googleApiClient: GoogleApiClient
    private lateinit var broadcastManager: BroadcastReceiver

    private fun handleActivityRecognise(type: Int, confident: Int) {
        var labelType = ""
        when (type) {
            DetectedActivity.IN_VEHICLE ->
                labelType = "On driving"
            DetectedActivity.ON_BICYCLE ->
                labelType = "On bicycle"
            DetectedActivity.ON_FOOT ->
                labelType = "On foot"
            DetectedActivity.STILL ->
                labelType = "On still"
            DetectedActivity.TILTING ->
                labelType = "Tilting"
            DetectedActivity.WALKING ->
                labelType = "Walking"
            DetectedActivity.RUNNING ->
                labelType = "Running"
            DetectedActivity.UNKNOWN ->
                labelType = "Unknown"
        }
        if (confident > Constant.CONFIDENCE) {
            tvActivity.text = labelType
            tvConfidence.text = "Confidence : $confident"
        }
        btnStartTrack.isEnabled = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recognise)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        buildGoogleClient()
        setUpListener()
        initBroadcast()
        startTracking()
    }

    private fun initBroadcast() {
        broadcastManager = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent == null) return
                if (intent.action == Constant.BROADCAST_DETECT_ACTIVITY) {
                    val type = intent?.getIntExtra("type", -1)
                    val confident = intent?.getIntExtra("confidence", 0)
                    handleActivityRecognise(type, confident)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(broadcastManager, IntentFilter(Constant.BROADCAST_DETECT_ACTIVITY))
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(broadcastManager)
    }

    override fun onStop() {
        super.onStop()
        if (googleApiClient.isConnected) {
            googleApiClient.disconnect()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_recognise, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_change) {
            startIntent<DemoActivity>(this)
        }
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
    }

    override fun onConnected(p0: Bundle?) {
    }

    override fun onConnectionSuspended(p0: Int) {
    }

    private fun buildGoogleClient() {
        googleApiClient = GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build()
    }

    private fun setUpListener() {
        btnStartTrack.setOnClickListener {
            startTracking()
        }
        btnStopTrack.setOnClickListener {
            val intent = Intent(this, BackgroundDetectedActivitiesService::class.java)
            stopService(intent)
            btnStartTrack.isEnabled = true
        }
    }

    private fun startTracking() {
        btnStartTrack.isEnabled = false
        val intent = Intent(this, BackgroundDetectedActivitiesService::class.java)
        startService(intent)
    }
}
