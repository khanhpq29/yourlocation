package pq.khanh.vn.yournearby.ui.map

import android.annotation.SuppressLint
import com.google.android.gms.awareness.Awareness
import com.google.android.gms.awareness.snapshot.LocationResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Maybe
import io.reactivex.MaybeEmitter
import javax.inject.Inject

/**
 * Created by khanhpq on 1/8/18.
 */
class MapPresenter @Inject constructor(){
    @SuppressLint("MissingPermission")
    fun showLocationWithMap(googleApiClient: GoogleApiClient) : Maybe<LatLng>{
        return Maybe.create {
            emitter: MaybeEmitter<LatLng> ->
            Awareness.SnapshotApi.getLocation(googleApiClient).setResultCallback { locationResult: LocationResult ->
                if (!locationResult.status.isSuccess){
                    emitter.onError(Throwable())
                }else{
                    emitter.onSuccess(LatLng(locationResult.location.latitude, locationResult.location.longitude))
                    emitter.onComplete()
                }
            }
        }
    }
}