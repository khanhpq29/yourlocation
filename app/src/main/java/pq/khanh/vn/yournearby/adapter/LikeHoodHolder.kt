package pq.khanh.vn.yournearby.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.item_likelihood.view.*

/**
 * Created by khanhpq on 1/10/18.
 */
class LikeHoodHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(placeLikelihood: String) {
        itemView.tvAddress.text = placeLikelihood
    }
}