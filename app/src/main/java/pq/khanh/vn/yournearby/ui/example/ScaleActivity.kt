package pq.khanh.vn.yournearby.ui.example

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import pq.khanh.vn.yournearby.R

class ScaleActivity : AppCompatActivity() {
    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scale)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
