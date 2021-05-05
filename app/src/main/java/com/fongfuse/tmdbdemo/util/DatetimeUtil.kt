package com.fongfuse.tmdbdemo.util

import android.content.Context
import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DatetimeUtil(private val context: Context) {

    companion object {
        const val DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm" //base datetime format
        const val CURRENT_YEAR_FORMAT_12 = "dd MMM, KK:mm a" //current year 12hr (pm/am)
        const val DIFF_YEAR_FORMAT_12 = "dd MMM yyyy, KK:mm a" //different year 12hr (pm/am)
        const val CURRENT_YEAR_FORMAT_24 = "dd MMM, HH:mm" //current year 24hr
        const val DIFF_YEAR_FORMAT_24 = "dd MMM yyyy, HH:mm" //different year 24hr
    }

    fun getDateTime(dateTime: String?): String {
        dateTime?.let {
            //old
            val oldf = SimpleDateFormat(DATE_TIME_FORMAT)
            val cal_oldf = Calendar.getInstance()
            cal_oldf.time = oldf.parse(dateTime)!!

            //new
            val newf = getFormatYear(cal_oldf.get(Calendar.YEAR))
            return newf.format(oldf.parse(dateTime)!!)
        }
        return ""
    }

    fun getFormatYear(year: Int): SimpleDateFormat {
        return if (year == Calendar.getInstance().get(Calendar.YEAR)) {
            if (is24HrFormat()) {
                SimpleDateFormat(CURRENT_YEAR_FORMAT_24)
            } else {
                SimpleDateFormat(CURRENT_YEAR_FORMAT_12)
            }
        } else {
            if (is24HrFormat()) {
                SimpleDateFormat(DIFF_YEAR_FORMAT_24)
            } else {
                SimpleDateFormat(DIFF_YEAR_FORMAT_12)
            }
        }
    }

    fun is24HrFormat(): Boolean {
        return DateFormat.is24HourFormat(context)
    }
}