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
    private const val REST_NUMBER_KEY = "rest_number"
    private const val TOTAL_NUMBER = "total_number"
    private const val SELECT_NUMBER_KEY = "number_selected"
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

    fun setRestNumber(context: Context, number : Int){
        val preference = getSharePreference(context)
        preference.edit().putInt(REST_NUMBER_KEY, number).apply()
    }

    fun getRestNumber(context: Context) : Int {
        val preference = getSharePreference(context)
        return preference.getInt(REST_NUMBER_KEY, 0)
    }

    fun setTotalNumber(context: Context, number: Int){
        val preference = getSharePreference(context)
        preference.edit().putInt(TOTAL_NUMBER, number).apply()
    }

    fun getTotalNumber(context: Context) : Int{
        val preference = getSharePreference(context)
        return preference.getInt(TOTAL_NUMBER, 0)
    }

    fun setSelectNumber(context: Context, number: Int){
        val preference = getSharePreference(context)
        preference.edit().putInt(SELECT_NUMBER_KEY, number).apply()
    }

    fun getSelectNumber(context: Context) : Int{
        val preference = getSharePreference(context)
        return preference.getInt(SELECT_NUMBER_KEY, 0)
    }

}