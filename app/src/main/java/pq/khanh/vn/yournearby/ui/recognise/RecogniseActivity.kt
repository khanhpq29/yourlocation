package pq.khanh.vn.yournearby.ui.recognise

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.awareness.Awareness
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.places.PlaceLikelihood
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_recognise.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.RuntimePermissions
import pq.khanh.vn.yournearby.R
import pq.khanh.vn.yournearby.adapter.LikeHoodAdapter
import pq.khanh.vn.yournearby.extensions.d
import pq.khanh.vn.yournearby.extensions.hide
import pq.khanh.vn.yournearby.extensions.show
import pq.khanh.vn.yournearby.extensions.showToast
import pq.khanh.vn.yournearby.utils.ItemGridDecoration

@RuntimePermissions
class RecogniseActivity : AppCompatActivity(), RecogniseContract.View {

    private lateinit var googleClient: GoogleApiClient
    private lateinit var placeAdapter: LikeHoodAdapter
    private var likehoodList: MutableList<PlaceLikelihood> = mutableListOf()
    lateinit var presenter: RecognisePresenter
    private var isGrid: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recognise)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        presenter = RecognisePresenter(CompositeDisposable(), this)
        googleClient = GoogleApiClient.Builder(this)
                .addApi(Awareness.API).build()
        googleClient.connect()
        initRecyclerView()
    }

    override fun showLikeliHood(list: MutableList<PlaceLikelihood>) {
        d("on success")
        likehoodList = list
        placeAdapter = LikeHoodAdapter(likehoodList, isGrid)
        likeLiHoods.apply {
            layoutManager = LinearLayoutManager(this@RecogniseActivity)
            addItemDecoration(DividerItemDecoration(this@RecogniseActivity, DividerItemDecoration.VERTICAL))
            adapter = placeAdapter
            setHasFixedSize(true)
        }
    }

    private fun initRecyclerView() {
        placeAdapter = LikeHoodAdapter(likehoodList, isGrid)
        presenter.getListLikeliHood(googleClient, likehoodList)
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
    fun permissionDenied() {
        showToast("need permission")
    }

    @SuppressLint("MissingPermission")
    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun beginRecognise() {
        Awareness.SnapshotApi.getDetectedActivity(googleClient)
                .setResultCallback { detectedActivityResult ->
                    if (!detectedActivityResult.status.isSuccess) {
                        return@setResultCallback
                    } else {
                        d("${detectedActivityResult.activityRecognitionResult.mostProbableActivity}")
                    }
                }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_recognise, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        return if (isGrid) {
            menu?.findItem(R.id.menu_change)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_grid)
            true
        } else {
            menu?.findItem(R.id.menu_change)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_list)
            true
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
            return true
        } else if (item?.itemId == R.id.menu_change) {
            switchDisplayList()
        }
        return true
    }

    private fun switchDisplayList() {
        if (isGrid) {
            isGrid = false
            invalidateOptionsMenu()
            initRecyclerViewManager(likeLiHoods, GridLayoutManager(this, 2))
        } else {
            isGrid = true
            invalidateOptionsMenu()
            initRecyclerViewManager(likeLiHoods, LinearLayoutManager(this))
        }
    }

    private fun initRecyclerViewManager(recyclerView: RecyclerView, rclLayoutManager: RecyclerView.LayoutManager) {
        if (rclLayoutManager is LinearLayoutManager) {
            recyclerView.apply {
                layoutManager = LinearLayoutManager(this@RecogniseActivity)
            }
        } else if (rclLayoutManager is GridLayoutManager) {
            recyclerView.apply {
                layoutManager = GridLayoutManager(this@RecogniseActivity, 2)
                addItemDecoration(ItemGridDecoration(
                        16, 16))
            }
        }
        placeAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dispose()
        if (googleClient.isConnected) {
            googleClient.disconnect()
        }
    }

    override fun showProgress() {
        prg.show()
    }

    override fun hideProgress() {
        showToast("no data")
        prg.hide(true)
    }

    override fun initData() {
        d("onComplete")
    }
}
