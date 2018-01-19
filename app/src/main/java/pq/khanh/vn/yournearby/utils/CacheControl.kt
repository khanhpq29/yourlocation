package pq.khanh.vn.yournearby.utils

import android.content.Context
import okhttp3.CacheControl
import okhttp3.Interceptor
import java.util.concurrent.TimeUnit

/**
 * Created by khanhpq on 1/15/18.
 */
object CacheControl {
    fun provideCache(context: Context) : Interceptor{
        return Interceptor { chain ->
            var request = chain.request()
            val cacheControl = CacheControl.Builder()
                    .maxStale(1, TimeUnit.HOURS)
                    .build()
            request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build()
            chain.proceed(request)
        }
    }
}