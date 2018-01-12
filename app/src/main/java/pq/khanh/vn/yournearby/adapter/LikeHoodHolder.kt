package pq.khanh.vn.yournearby.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.google.android.gms.location.places.PlaceLikelihood
import kotlinx.android.synthetic.main.item_likelihood.view.*

/**
 * Created by khanhpq on 1/10/18.
 */
class LikeHoodHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(placeLikelihood: PlaceLikelihood) {
        itemView.tvAddress.text = placeLikelihood.place.name
        itemView.tvLikeLiHood.text = placeLikelihood.likelihood.toString()
    }
}