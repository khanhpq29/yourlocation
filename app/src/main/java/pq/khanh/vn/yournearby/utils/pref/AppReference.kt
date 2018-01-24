package pq.khanh.vn.yournearby.utils.pref

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * Created by khanhpq on 1/23/18.
 */
object AppReference {
    const val DISPLAY_KEY = "display"
    private fun getSharePreference(context: Context) : SharedPreferences{
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun setDisplayType(context: Context, isList : Boolean){
        val preference = getSharePreference(context)
        preference.edit().putBoolean(DISPLAY_KEY, isList).apply()
    }

    fun getDisplayType(context: Context) : Boolean{
        val preference = getSharePreference(context)
        return preference.getBoolean(DISPLAY_KEY, true)
    }

}