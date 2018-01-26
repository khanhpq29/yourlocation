package pq.khanh.vn.yournearby.ui.order

/**
 * Created by khanhpq on 1/25/18.
 */
class OrderPresenter(val view : OrderView) {
    fun incresaseNumber(){
        view.increase()
    }
    fun decreaseNumber(){
        view.decreaseNumber()
    }
}