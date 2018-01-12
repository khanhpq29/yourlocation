package pq.khanh.vn.yournearby.extensions

import android.app.Activity
import android.support.v7.widget.SwitchCompat
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.CompoundButton
import android.widget.SeekBar
import android.widget.TextView

/**
 * Created by khanhpq on 1/9/18.
 */
inline fun <reified T : View> Activity.findView(id: Int): T = findViewById(id)

var View.visible
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

inline fun View.hide(gone: Boolean = true) {
    visibility = if (gone) View.GONE else View.INVISIBLE
}

inline fun View.show() {
    visibility = View.VISIBLE
}

private var _beforeTextChanged: ((CharSequence?, Int, Int, Int) -> Unit)? = null
private var _onTextChanged: ((CharSequence?, Int, Int, Int) -> Unit)? = null
private var _afterTextChanged: ((Editable?) -> Unit)? = null

inline fun TextView.addTextWatcher(init: EditTextWatcher.() -> Unit) = this.addTextChangedListener(EditTextWatcher().apply(init))

class EditTextWatcher : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
        _afterTextChanged?.invoke(s)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        _beforeTextChanged?.invoke(s, start, count, after)
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        _onTextChanged?.invoke(s, start, before, count)
    }

    fun beforeTextChanged(listener : (CharSequence?, Int, Int, Int) -> Unit) {
        _beforeTextChanged = listener
    }

    fun onTextChanged(listener: (CharSequence?, Int, Int, Int) -> Unit) {
        _onTextChanged = listener
    }

    fun afterTextChanged(listener: (Editable?) -> Unit) {
        _afterTextChanged = listener
    }
}

fun SeekBar.onSeekBarChange(change : (Int, Boolean) -> Unit){
    setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            change(progress, fromUser)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
        }
    })
}


fun SwitchCompat.onSwitch(switch : (Boolean) -> Unit){
    setOnCheckedChangeListener { _, isChecked -> switch(isChecked) }
}