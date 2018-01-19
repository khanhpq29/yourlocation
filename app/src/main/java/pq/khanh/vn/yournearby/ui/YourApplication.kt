package pq.khanh.vn.yournearby.ui

import android.app.Application
import android.content.Context
import com.squareup.leakcanary.LeakCanary
import pq.khanh.vn.yournearby.injection.AppComponent
import pq.khanh.vn.yournearby.injection.DaggerAppComponent
import com.squareup.leakcanary.LeakCanary.refWatcher
import com.squareup.leakcanary.RefWatcher



/**
 * Created by khanhpq on 1/11/18.
 */
class YourApplication : Application() {
    private lateinit var refWatcher: RefWatcher

    companion object {
        lateinit var di : AppComponent
        fun getRefWatcher(context: Context): RefWatcher {
            val application = context.applicationContext as YourApplication
            return application.refWatcher
        }
    }



    override fun onCreate() {
        super.onCreate()
        di = DaggerAppComponent.builder().build()
        refWatcher = LeakCanary.install(this)
    }
}