package pq.khanh.vn.yournearby.ui.recognise

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
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
import pq.khanh.vn.yournearby.extensions.showToast

@RuntimePermissions
class RecogniseActivity : AppCompatActivity(), RecogniseContract.View {
    private lateinit var googleClient: GoogleApiClient
    private lateinit var placeAdapter: LikeHoodAdapter
    private var likehoodList: MutableList<PlaceLikelihood> = mutableListOf()
    //    @Inject
    lateinit var presenter: RecognisePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
//        YourApplication.di.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recognise)
        presenter = RecognisePresenter(CompositeDisposable(), this)
        googleClient = GoogleApiClient.Builder(this).addApi(Awareness.API).build()
        googleClient.connect()
        initRecyclerView()
//        presenter.getListLikeliHood(googleClient, likehoodList)
    }

    override fun showLikeliHood(list: MutableList<PlaceLikelihood>) {
        likehoodList = list
        d("${list.size}")
//        likeLiHoods.apply {
//            layoutManager = LinearLayoutManager(this@RecogniseActivity)
//            adapter = placeAdapter
//            setHasFixedSize(true)
//
//        }
        placeAdapter.notifyDataSetChanged()
    }

    private fun initRecyclerView() {
        placeAdapter = LikeHoodAdapter(likehoodList)
        likeLiHoods.apply {
            layoutManager = LinearLayoutManager(this@RecogniseActivity)
            adapter = placeAdapter
            setHasFixedSize(true)
        }
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

    override fun onDestroy() {
        super.onDestroy()
        presenter.dispose()
    }
}
