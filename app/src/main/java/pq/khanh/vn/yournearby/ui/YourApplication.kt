package pq.khanh.vn.yournearby.ui

import android.app.Application
import pq.khanh.vn.yournearby.injection.AppComponent
import pq.khanh.vn.yournearby.injection.DaggerAppComponent

/**
 * Created by khanhpq on 1/11/18.
 */
class YourApplication : Application() {
    companion object {
        lateinit var di : AppComponent
    }
    override fun onCreate() {
        super.onCreate()
        di = DaggerAppComponent.builder().build()
    }
}