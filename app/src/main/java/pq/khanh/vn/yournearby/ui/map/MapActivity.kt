package pq.khanh.vn.yournearby.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.location.Geocoder
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.gms.awareness.Awareness
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_map.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import pq.khanh.vn.yournearby.R
import pq.khanh.vn.yournearby.extensions.*
import pq.khanh.vn.yournearby.ui.recognise.RecogniseActivity
import pq.khanh.vn.yournearby.utils.AwarenessApi
import java.util.*

@RuntimePermissions
class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var coord: LatLng
    private var googleMap: GoogleMap? = null
    private lateinit var googleClient: GoogleApiClient
    private val compositeDispose: CompositeDisposable by lazy { CompositeDisposable() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        d("on create")
        setContentView(R.layout.activity_map)
        googleClient = GoogleApiClient.Builder(this).addApi(Awareness.API).build()
        googleClient.connect()
        coord = intent?.getParcelableExtra("location_coord") ?: LatLng(0.0, 0.0)
        d("${coord.latitude}, ${coord.longitude}")
        val supportMap = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        supportMap.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap?) {
        d("onMap ready")
        googleMap = map
        googleMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
        showLocationOnMapWithPermissionCheck(googleMap!!)
    }

    @SuppressLint("NeedOnRequestPermissionsResult")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun showLocationOnMap(map: GoogleMap) {
        compositeDispose.add(AwarenessApi.currentLocation(googleClient)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    progress.show()
                }
                .doOnSuccess {
                    progress.hide(true)
                }
                .subscribe({ t: LatLng ->
                    val address = Geocoder(this@MapActivity, Locale.getDefault()).getFromLocation(t.latitude, t.longitude, 1)
                    val cityName = address[0].getAddressLine(0)
                    locationTitle.text = cityName
                    map.addMarker(MarkerOptions().position(t).title("You"))
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(t, 18f))
                }, { progress.visibility = View.GONE }))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_map, menu)
        val menuItem = menu?.findItem(R.id.actions_search)
        val searchView = menuItem?.actionView as SearchView
        searchView.onQuerySubmit { name: String? -> d(name ?: "error") }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.actions_activity) {
            startIntent<RecogniseActivity>(this)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        googleMap?.clear()
        compositeDispose.clear()
    }
}
