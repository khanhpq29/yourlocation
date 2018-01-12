package pq.khanh.vn.yournearby.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.google.android.gms.location.places.PlaceLikelihood
import pq.khanh.vn.yournearby.R
import pq.khanh.vn.yournearby.adapter.LikeHoodHolder
import pq.khanh.vn.yournearby.extensions.inflateLayout

/**
 * Created by khanhpq on 1/10/18.
 */
class LikeHoodAdapter(private val likelihoodList : MutableList<PlaceLikelihood>) : RecyclerView.Adapter<LikeHoodHolder>(){
    override fun onBindViewHolder(holder: LikeHoodHolder?, position: Int) {
        holder?.bind(likelihoodList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): LikeHoodHolder {
        val view = parent.inflateLayout(R.layout.item_likelihood)
        return LikeHoodHolder(view)
    }

    override fun getItemCount(): Int {
        return likelihoodList.size
    }

}