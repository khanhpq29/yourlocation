package pq.khanh.vn.yournearby.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.google.android.gms.location.places.PlaceLikelihood
import pq.khanh.vn.yournearby.R
import pq.khanh.vn.yournearby.extensions.inflateLayout
import pq.khanh.vn.yournearby.utils.Constant

/**
 * Created by khanhpq on 1/10/18.
 */
class LikeHoodAdapter(private val likelihoodList: MutableList<PlaceLikelihood>, private val isGrid: Boolean) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is LikeHoodHolder) {
            holder?.bind(likelihoodList[position])
        } else if (holder is LikeLiHoodGridHolder) {
            holder?.bind(likelihoodList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val holder = if (viewType == Constant.LIST_TYPE) {
            val view = parent.inflateLayout(R.layout.item_likelihood)
            LikeHoodHolder(view)
        } else {
            val view = parent.inflateLayout(R.layout.item_likelihood_grid)
            LikeLiHoodGridHolder(view)
        }
        return holder
    }

    override fun getItemViewType(position: Int): Int {
        return if (isGrid) {
            Constant.GRID_TYPE
        } else {
            Constant.LIST_TYPE
        }
    }

    override fun getItemCount(): Int {
        return likelihoodList.size
    }

}