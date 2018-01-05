package pq.khanh.vn.yournearby.ui

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.awareness.Awareness
import com.google.android.gms.awareness.snapshot.LocationResult
import com.google.android.gms.common.api.GoogleApiClient
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.disposables.CompositeDisposable

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.RuntimePermissions
import pq.khanh.vn.yournearby.extensions.d
import pq.khanh.vn.yournearby.extensions.e
import java.util.concurrent.TimeUnit
import android.location.Geocoder
import com.google.android.gms.awareness.snapshot.PlacesResult
import io.reactivex.Single
import pq.khanh.vn.yournearby.R
import pq.khanh.vn.yournearby.extensions.i
import java.util.*


@RuntimePermissions
class MainActivity : AppCompatActivity() {
    private val compositDispose: CompositeDisposable by lazy { CompositeDisposable() }
    private lateinit var googleClient: GoogleApiClient
    private var cityLat: Double = 0.0
    private var cityLon: Double = 0.0

    override fun onStart() {
        super.onStart()
        d("on start")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        d("on create")
        googleClient = GoogleApiClient.Builder(this).addApi(Awareness.API).build()
        googleClient.connect()
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setUpListener()
    }

    private fun setUpListener() {
        compositDispose.add(RxView.clicks(fab)
                .throttleFirst(150, TimeUnit.MILLISECONDS)
                .subscribe { getLocationWithPermissionCheck() })
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
    fun deniedLocation() = d("permission denied")

    @SuppressLint("MissingPermission")
    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun getLocation() {
        Awareness.SnapshotApi
                .getLocation(googleClient)
                .setResultCallback { locationResult: LocationResult ->
                    if (!locationResult.status.isSuccess) {
                        e("location failed")
                        return@setResultCallback
                    } else {
                        val location = locationResult.location
                        cityLat = location.latitude
                        cityLon = location.longitude
                        tvLat.text = cityLat.toString()
                        tvLon.text = cityLon.toString()
                        compositDispose.add(Single.fromCallable {
                            Geocoder(this, Locale.getDefault())
                        }.subscribe({ geocoder: Geocoder ->
                            val addresses = geocoder.getFromLocation(cityLat, cityLon, 1)
                            val cityName = addresses[0].getAddressLine(0)
                            tvCurrentLocation.text = cityName
                        }))
                    }
                }

    }

    @SuppressLint("NeedOnRequestPermissionsResult")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositDispose.clear()
        if (googleClient.isConnected) googleClient.disconnect()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.actions_near -> {
                checkPlaceWithPermissionCheck()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("MissingPermission")
    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun checkPlace() {
        Awareness.SnapshotApi.getPlaces(googleClient)
                .setResultCallback { placesResult: PlacesResult ->
                    if (!placesResult.status.isSuccess){
                        d("failed")
                        return@setResultCallback
                    }else {
                        val placeLikelihoodsList = placesResult.placeLikelihoods
                        for (placeLikelihood in placeLikelihoodsList){
                            i("${placeLikelihood.place.name}, ${placeLikelihood.likelihood}")
                        }
                    }
                }
    }
}
