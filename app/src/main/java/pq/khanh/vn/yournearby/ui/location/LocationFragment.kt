package pq.khanh.vn.yournearby.ui.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.google.android.gms.awareness.Awareness
import com.google.android.gms.awareness.snapshot.LocationResult
import com.google.android.gms.awareness.snapshot.PlacesResult
import com.google.android.gms.common.api.GoogleApiClient
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_location.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.RuntimePermissions
import pq.khanh.vn.yournearby.R
import pq.khanh.vn.yournearby.extensions.inflateLayout
import pq.khanh.vn.yournearby.ui.MapActivity
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by khanhpq on 1/5/18.
 */
@RuntimePermissions
class LocationFragment : Fragment() {
    private val compositDispose: CompositeDisposable by lazy { CompositeDisposable() }
    private lateinit var googleClient: GoogleApiClient
    private var cityLat: Double = 0.0
    private var cityLon: Double = 0.0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?) = container?.inflateLayout(R.layout.activity_location)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        googleClient = GoogleApiClient.Builder(context!!).addApi(Awareness.API).build()
        googleClient.connect()
        setUpListener()
        setHasOptionsMenu(true)
    }
    private fun setUpListener() {
        compositDispose.add(RxView.clicks(fab)
                .throttleFirst(150, TimeUnit.MILLISECONDS)
                .subscribe { getLocationWithPermissionCheck() })
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
    fun deniedLocation() {}

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
                        compositDispose.add(Single.fromCallable {
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
        compositDispose.clear()
        if (googleClient.isConnected) googleClient.disconnect()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_main, menu)
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
            R.id.action_map -> {
                val intent = Intent(context, MapActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("MissingPermission")
    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun checkPlace() {
        compositDispose.add(Single.fromCallable {
            Awareness.SnapshotApi.getPlaces(googleClient)
                    .setResultCallback { placesResult: PlacesResult ->
                        if (!placesResult.status.isSuccess) {
                            return@setResultCallback
                        } else {
                            val placeLikelihoodsList = placesResult.placeLikelihoods
                            for (placeLikelihood in placeLikelihoodsList) {
//                                i("${placeLikelihood.place.latLng}, ${placeLikelihood.place.name}")
                            }
                        }
                    }
        }.subscribe())
    }
}