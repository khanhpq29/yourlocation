package pq.khanh.vn.yournearby.utils.pref

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * Created by khanhpq on 1/23/18.
 */
object AppReference {
    private const val DISPLAY_KEY = "display"
    private const val EXAMPLE_KEY = "example"
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

    fun setStringExample(context: Context, name : String){
        val preference = getSharePreference(context)
        preference.edit().putString(EXAMPLE_KEY, name).apply()
    }

    fun getStringExample(context: Context) : String {
        val preference = getSharePreference(context)
        return preference.getString(EXAMPLE_KEY, "")
    }

}