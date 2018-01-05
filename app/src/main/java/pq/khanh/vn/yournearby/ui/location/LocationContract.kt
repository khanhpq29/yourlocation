package pq.khanh.vn.yournearby.ui.location

import com.google.android.gms.common.api.GoogleApiClient

/**
 * Created by khanhpq on 1/5/18.
 */
interface LocationContract{
    interface View{}
    interface Presenter{
        fun getLocation(googleApiClient: GoogleApiClient)
    }
}