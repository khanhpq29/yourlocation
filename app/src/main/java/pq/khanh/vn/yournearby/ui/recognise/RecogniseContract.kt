package pq.khanh.vn.yournearby.ui.recognise

import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.places.PlaceLikelihood

/**
 * Created by khanhpq on 1/11/18.
 */
interface RecogniseContract {
    interface View{
        fun showLikeliHood(list : MutableList<PlaceLikelihood>)
    }
    interface Presenter{
        fun getListLikeliHood(googleApiClient: GoogleApiClient, list : MutableList<PlaceLikelihood>)
        fun dispose()
        fun initView(view : View)
    }
}