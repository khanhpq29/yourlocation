package pq.khanh.vn.yournearby.ui.recognise

import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.places.PlaceLikelihood
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import pq.khanh.vn.yournearby.utils.AwarenessApi

/**
 * Created by khanhpq on 1/11/18.
 */
class RecognisePresenter (private var compositeDispose: CompositeDisposable, private var contractView : RecogniseContract.View) : RecogniseContract.Presenter{
    private lateinit var contracView : RecogniseContract.View

    override fun getListLikeliHood(googleApiClient: GoogleApiClient, list: MutableList<PlaceLikelihood>) {
        compositeDispose.add(AwarenessApi.checkNearbyLocation(googleApiClient)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { contractView.showProgress() }
                .doOnSuccess { contractView.hideProgress() }
                .subscribe({ t: MutableList<PlaceLikelihood> ->
                    contractView.showLikeliHood(t)
                }, {error: Throwable ->
                    error.printStackTrace()
                    contractView.hideProgress()
                }))
    }

    override fun dispose() {
        compositeDispose.clear()
    }

    override fun initView(view: RecogniseContract.View) {
        contractView = view
        compositeDispose = CompositeDisposable()
    }
}