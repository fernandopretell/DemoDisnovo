package com.limapps.baseui.views.dialogs

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatDialogFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale


class GeneralDatePicker : AppCompatDialogFragment(), DatePickerDialog.OnDateSetListener {

    companion object {
        const val DATE_KEY = "date_key"
        const val THEME_KEY = "theme_key"
        const val MAX_DATE_KEY = "max_date_key"
        private const val DATE_FORMAT_SEPARATOR_SLASH = "yyyy/MM/dd"
    }

    private var datePickerListener: DatePickerListener? = null
    private var baseCalendar: Calendar? = null
    private var maxDate: Long? = null

    interface DatePickerListener {
        fun onDateSelected(calendar: Calendar, dateSelected: String)
    }

    fun setDatePickerListener(datePickerListener: DatePickerListener) {
        this.datePickerListener = datePickerListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseCalendar = getCalendarFromExtras()
        maxDate = getMaxDateFromExtras()
    }

    private fun getCalendarFromExtras(): Calendar? {
        return if (arguments?.containsKey(DATE_KEY) == true) {
            stringToCalendar(arguments?.getString(DATE_KEY), DATE_FORMAT_SEPARATOR_SLASH)
        } else {
            null
        }
    }

    private fun getMaxDateFromExtras(): Long? {
        return if (arguments?.containsKey(MAX_DATE_KEY) == true) {
            arguments?.getLong(MAX_DATE_KEY)
        } else {
            null
        }
    }

    private fun getThemeFromExtras(): Int? {
        return if (arguments?.containsKey(THEME_KEY) == true) {
            arguments?.getInt(THEME_KEY, 0)
        } else {
            null
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val safeCalendar = baseCalendar ?: Calendar.getInstance()
        val theme = getThemeFromExtras()
        val year = safeCalendar.get(Calendar.YEAR)
        val month = safeCalendar.get(Calendar.MONTH)
        val day = safeCalendar.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(context!!, theme ?: 0, this, year, month, day).apply {
            maxDate?.let {
                datePicker.maxDate = it
            }
        }
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        datePickerListener?.onDateSelected(calendar, dateToString(calendar, DATE_FORMAT_SEPARATOR_SLASH))
    }


    private fun stringToCalendar(time: String?, dateFormat: String): Calendar {
        return try {
            val format = SimpleDateFormat(dateFormat, Locale.ENGLISH)
            val date = format.parse(time)
            val calendar = GregorianCalendar.getInstance()
            calendar.timeInMillis = date.time
            calendar
        } catch (exception: Exception) {
            exception.printStackTrace()
            Calendar.getInstance()
        }
    }

    private fun dateToString(date: Calendar, format: String): String {
        val target = Date(date.timeInMillis)
        return SimpleDateFormat(format).format(target)
    }
}
