package pq.khanh.vn.yournearby.injection

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by khanhpq on 1/11/18.
 */
@Module
class AppModule(private val app : Application) {
    @Provides
    @Singleton
    fun provideContext() : Context = app
}