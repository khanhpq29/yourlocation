package pq.khanh.vn.yournearby.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import pq.khanh.vn.yournearby.R
import pq.khanh.vn.yournearby.extensions.inflateLayout
import pq.khanh.vn.yournearby.utils.Constant

/**
 * Created by khanhpq on 1/10/18.
 */
class LikeHoodAdapter(private var likelihoodList: MutableList<String>) : RecyclerView.Adapter<LikeHoodHolder>() {
    private var isSwitch = true

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): LikeHoodHolder {
        val view = if (viewType == Constant.LIST_TYPE) {
            parent.inflateLayout(R.layout.item_likelihood)
        } else {
            parent.inflateLayout(R.layout.item_likelihood_grid)
        }
        return LikeHoodHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return if (isSwitch) {
            Constant.LIST_TYPE
        } else {
            Constant.GRID_TYPE
        }
    }

    override fun getItemCount(): Int {
        return likelihoodList.size
    }

    override fun onBindViewHolder(holder: LikeHoodHolder?, position: Int) {
        val item = likelihoodList[position]
        holder?.bind(item)
    }

    fun switchLayout() : Boolean{
        isSwitch = !isSwitch
        return isSwitch
    }
}