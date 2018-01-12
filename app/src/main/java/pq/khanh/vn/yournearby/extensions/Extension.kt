package pq.khanh.vn.yournearby.extensions

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

/**
 * Created by khanhpq on 1/5/18.
 */
fun Any.d(message: String) {
    Log.d(this.javaClass.simpleName, message)
}

fun Any.e(message: String) {
    Log.e(this.javaClass.simpleName, message)
}

fun Context.i(message: String) {
    Log.e(this.javaClass.simpleName, message)
}

fun ViewGroup?.inflateLayout(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(this?.context).inflate(layoutId, this, attachToRoot)
}

fun Context.showToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

