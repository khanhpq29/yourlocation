package pq.khanh.vn.yournearby.ui.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import com.google.android.gms.awareness.Awareness
import com.google.android.gms.awareness.snapshot.LocationResult
import com.google.android.gms.awareness.snapshot.PlacesResult
import com.google.android.gms.awareness.snapshot.WeatherResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.maps.model.LatLng
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_location.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.RuntimePermissions
import pq.khanh.vn.yournearby.R
import pq.khanh.vn.yournearby.extensions.inflateLayout
import pq.khanh.vn.yournearby.extensions.showToast
import pq.khanh.vn.yournearby.ui.MapActivity
import java.util.*
import java.util.concurrent.TimeUnit

@RuntimePermissions
class LocationFragment : Fragment() {
    private val compositeDispose: CompositeDisposable by lazy { CompositeDisposable() }
    private lateinit var googleClient: GoogleApiClient
    private var cityLat: Double = 0.0
    private var cityLon: Double = 0.0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?) = container?.inflateLayout(R.layout.fragment_location)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        googleClient = GoogleApiClient.Builder(context!!).addApi(Awareness.API).build()
        googleClient.connect()
        setUpListener()
    }

    private fun setUpListener() {
        compositeDispose.add(RxView.clicks(fab)
                .throttleFirst(150, TimeUnit.MILLISECONDS)
                .subscribe { getLocationWithPermissionCheck() })
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
    fun deniedLocation() {
    }

    @SuppressLint("MissingPermission")
    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun getLocation() {
        Awareness.SnapshotApi
                .getLocation(googleClient)
                .setResultCallback { locationResult: LocationResult ->
                    if (!locationResult.status.isSuccess) {
                        return@setResultCallback
                    } else {
                        val location = locationResult.location
                        cityLat = location.latitude
                        cityLon = location.longitude
                        tvLat.text = cityLat.toString()
                        tvLon.text = cityLon.toString()
                        compositeDispose.add(Single.fromCallable {
                            Geocoder(context, Locale.getDefault())
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
        compositeDispose.clear()
        if (googleClient.isConnected) googleClient.disconnect()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.actions_near -> {
                checkPlaceWithPermissionCheck()
                true
            }
            R.id.action_map -> {
                gotoMapWithPermissionCheck()
                true
            }
            R.id.actions_weather -> {
                checkWeather()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("MissingPermission")
    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun checkWeather() {
        Awareness.SnapshotApi.getWeather(googleClient).setResultCallback { weatherResult: WeatherResult ->
            if (!weatherResult.status.isSuccess) {
                return@setResultCallback
            } else {
                context?.showToast(weatherResult.weather.humidity.toString())
            }
        }
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun gotoMap() {
        Log.d("lat ", "$cityLat")
        val intent = Intent(context, MapActivity::class.java)
        val placeLocation = LatLng(cityLat, cityLon)
        intent.putExtra("location_coord", placeLocation)
        startActivity(intent)
    }

    @SuppressLint("MissingPermission")
    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun checkPlace() {
        compositeDispose.add(Single.fromCallable {
            Awareness.SnapshotApi.getPlaces(googleClient)
                    .setResultCallback { placesResult: PlacesResult ->
                        if (!placesResult.status.isSuccess) {
                            return@setResultCallback
                        } else {
                            val placeLikelihoodsList = placesResult.placeLikelihoods
                            for (placeLikelihood in placeLikelihoodsList) {
//                                Log.i("near", "${placeLikelihood.place.latLng}, ${placeLikelihood.place.name}")
                            }
                        }
                    }
        }.subscribe())
    }
}