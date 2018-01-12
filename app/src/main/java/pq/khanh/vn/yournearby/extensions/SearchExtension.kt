package pq.khanh.vn.yournearby.extensions

import android.support.v7.widget.SearchView

/**
 * Created by khanhpq on 1/9/18.
 */
fun SearchView.onQuerySubmit(query : (String?) -> Unit){
    setOnQueryTextListener(object : SearchView.OnQueryTextListener{
        override fun onQueryTextSubmit(query: String?): Boolean {
            query(query)
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return false
        }
    })
}

fun SearchView.onQueryChange(query : (String?) -> Unit){
    setOnQueryTextListener(object  : SearchView.OnQueryTextListener{
        override fun onQueryTextSubmit(query: String?): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            query(newText)
            return false
        }
    })
}