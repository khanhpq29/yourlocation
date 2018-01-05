package pq.khanh.vn.yournearby.ui.location

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pq.khanh.vn.yournearby.R
import pq.khanh.vn.yournearby.extensions.inflateLayout

/**
 * Created by khanhpq on 1/5/18.
 */
class LocationFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?) = container?.inflateLayout(R.layout.activity_main)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        d("lifcycle", "view created")
    }
}