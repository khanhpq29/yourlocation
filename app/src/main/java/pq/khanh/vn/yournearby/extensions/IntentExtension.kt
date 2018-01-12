package pq.khanh.vn.yournearby.extensions

import android.content.Context
import android.content.Intent

/**
 * Created by khanhpq on 1/9/18.
 */
inline fun Intent.start(context: Context)  = context.startActivity(this)
inline fun <reified T : Any> intentFor(context: Context) : Intent = Intent(context, T::class.java)
inline fun <reified T : Context> startIntent(context: Context) = context.startActivity(intentFor<T>(context))
