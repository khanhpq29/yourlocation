package pq.khanh.vn.yournearby.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.google.android.gms.location.places.PlaceLikelihood
import kotlinx.android.synthetic.main.item_likelihood_grid.view.*

/**
 * Created by khanhpq on 1/19/18.
 */
class LikeLiHoodGridHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(placeLikelihood: PlaceLikelihood) {
        itemView.tvLikelihoodGrid.text = placeLikelihood.place.name
        itemView.tvRatio.text = placeLikelihood.likelihood.toString()
    }
}