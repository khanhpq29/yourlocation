package pq.khanh.vn.yournearby.utils

import android.annotation.SuppressLint
import com.google.android.gms.awareness.Awareness
import com.google.android.gms.awareness.snapshot.LocationResult
import com.google.android.gms.awareness.snapshot.PlacesResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.places.PlaceLikelihood
import com.google.android.gms.maps.model.LatLng
import io.reactivex.*

/**
 * Created by khanhpq on 1/8/18.
 */
object AwarenessApi {

    @SuppressLint("MissingPermission")
    fun currentLocation(googleApiClient: GoogleApiClient): Maybe<LatLng> {
        return Maybe.create { emitter: MaybeEmitter<LatLng> ->
            Awareness.SnapshotApi.getLocation(googleApiClient).setResultCallback { locationResult: LocationResult ->
                if (!locationResult.status.isSuccess) {
                    emitter.onError(Throwable())
                } else {
                    emitter.onSuccess(LatLng(locationResult.location.latitude, locationResult.location.longitude))
                    emitter.onComplete()
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun checkNearbyLocation(googleApiClient: GoogleApiClient): Maybe<MutableList<PlaceLikelihood>> {
        return Maybe.create { emitter ->
            Awareness.SnapshotApi.getPlaces(googleApiClient)
                    .setResultCallback { placesResult: PlacesResult ->
                        if (!placesResult.status.isSuccess) {
                            emitter.onError(Throwable())
                        } else {
                            emitter.onSuccess(placesResult.placeLikelihoods)
                            emitter.onComplete()
                        }
                    }
        }
    }
}