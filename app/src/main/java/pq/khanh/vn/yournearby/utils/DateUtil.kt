package pq.khanh.vn.yournearby.utils

import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by khanhpq on 1/11/18.
 */
object DateUtil {
    private val DATE_FORMAT = "yyyy-MM-dd"

    fun convertStringToTimestamp(date: String): Long {
        val dateFormat = SimpleDateFormat(DATE_FORMAT)
        val date = dateFormat.parse(date)
        return date.time
    }

    fun convertTimestampToAge(time: Long): String {
        val timeCurrent = GregorianCalendar.getInstance().timeInMillis
        val age = (timeCurrent / 1000 - time) / 31536000
        return age.toInt().toString()
    }

    fun convertTimestampToDate(timestamp: Long): String {
        val cal = GregorianCalendar.getInstance()
        cal.timeInMillis = timestamp
        return SimpleDateFormat(DATE_FORMAT).format(cal)
    }

    fun dateToLong(date : String) : Long{
        val dateArray = date.split("-")

        val year = Integer.parseInt(dateArray[0])
        val month = Integer.parseInt(dateArray[1])
        val date = Integer.parseInt(dateArray[2])
        print("-----------$year, $month, $date")
        val gc = GregorianCalendar(year, month, date)
        val timeStamp = gc.timeInMillis
        return timeStamp
    }

}