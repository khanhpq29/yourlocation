package pq.khanh.vn.yournearby.injection

import dagger.Component
import pq.khanh.vn.yournearby.ui.recognise.RecogniseActivity

/**
 * Created by khanhpq on 1/11/18.
 */
@Component(modules = [(AppModule::class)])
interface AppComponent {
    fun inject(activity : RecogniseActivity)
}