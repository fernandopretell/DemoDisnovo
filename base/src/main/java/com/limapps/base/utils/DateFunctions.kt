package com.limapps.base.utils

import android.content.Context
import com.limapps.base.R
import com.limapps.base.models.ServerTime
import com.limapps.base.models.ServerZone
import com.limapps.base.others.ResourcesProvider
import com.limapps.common.tryOrDefault
import org.threeten.bp.Duration
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
const val DATE_FORMAT_SCHEDULED_ORDER = "yyy-MM-dd HH:mm a"
const val DATE_FORMAT_1_TO_24_HOURS = "yyyy-MM-dd kk:mm:ss"
const val DATE_FORMAT_WITH_OR_SEPARATOR = "yyyy-MM-dd | HH:mm"
const val DATE_FORMAT_SEPARATOR_SLASH = "yyyy/MM/dd"
const val DATE_FORMAT_SEPARATOR_MINUS = "yyyy-MM-dd"
const val DATE_FORMAT_FACEBOOK = "dd/MM/yyyy"
const val DATE_FORMAT_MONTH_YEAR = "MM/yyyy"
const val DATE_FORMAT_DAY_MONTH = "dd MMMM"
const val DATE_FORMAT_DAY_MONTH_HOURS = "dd MMMM HH:mm"
const val DATE_FORMAT_DAY_MONTH_12_HOURS = "dd MMMM hh:mm aa"
const val DATE_FORMAT_DAY_MONTH_YEAR = "d MMMM, yyyy"
const val DATE_FORMAT_PAY = "dd/MM/yyyy \' · \' hh:mma"
const val HOUR_FORMAT = "HH:mm:ss"
const val HOUR_FORMAT_WITHOUT_SECONDS = "HH:mm"
const val HOUR_FORMAT_SCHEDULE = "hh:mm a"
const val HOUR_FORMAT_SCHEDULE_MILITARY_TIME = "HH:mm"
const val HOUR_FORMAT_MINUTES_SECONDS = "mm:ss"
const val READABLE_FORMAT_DATE_WITHOUT_TIME = "EEEE d MMMM"
const val READABLE_FORMAT_DATE_WITHOUT_TIME_COMMA = "EEEE, d MMM"
const val READABLE_FORMAT_MONTH_DAY = "MMMM d"
const val READABLE_FORMAT_DATE = "EEEE d MMMM, hh:mm a"
const val EXTENDED_FORMAT_DATE = "EEE MMM d HH:mm:ss z yyyy"
const val SERVER_FORMAT_DATE = "EEE, dd MMM yyyy HH:mm:ss zzz"
const val DATE_FORMAT_GMT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
const val DATE_FORMAT_GMT_EXT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
const val ADULT_MIN_AGE = 18
const val FORMAT_EEEE = "EEEE"
const val FORMAT_SCHEDULE_DATE = "EEE dd, hh:mm a"
const val FORMAT_SCHEDULE_DATE_DAY = "EEEE dd"
const val FORMAT_SUPPORT = "dd MMM, hh:mm a"
const val DEBT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
const val DEBT_PRETY_DATE_FORMAT = "EEEE d, MMMM yyyy - HH:mm:ss a"
const val FORMAT_NO_SPACE = "yyyyMMdd"
const val ONLY_MONTH_NAME = "MMMM"
const val CREDIT_FULL_DATE_FORMAT = "dd MMMM yyyy, hh:mm a"
const val CREDIT_SIMPLE_DATE_FORMAT = "dd MMM yyyy"
const val SIMPLE_ONLY_NUMBER_DATE_FORMAT = "dd-MM-yyyy"

private const val UPDATE_INTERVAL: Int = 60
private var lastUpdateDate = Date()
private var lastLocalDate = Date()
var serverDate: LocalDateTime = LocalDateTime.now()
    private set

var serverTime: ServerTime =
        ServerTime(day = DateTimeFormatter.ofPattern("EEE").format(serverDate),
                hour = DateTimeFormatter.ofPattern(HOUR_FORMAT).format(serverDate),
                timestamp = System.currentTimeMillis(),
                offset = ZoneId.systemDefault().rules.getOffset(serverDate).id,
                serverZone = ServerZone(ZoneId.systemDefault().id))

fun setCurrentServerDate(currentServerTime: ServerTime) {
    serverTime = currentServerTime
    serverDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(currentServerTime.timestamp * 1000),
            ZoneOffset.of(currentServerTime.offset).normalized())
    lastLocalDate = Date()
}

fun serverDate(): LocalDateTime {
    val now = Date()
    val difference = (now.time - lastUpdateDate.time) / 1000
    if (difference > UPDATE_INTERVAL) {
        serverDate = updateCurrentTime()
        lastUpdateDate = now
    }
    return serverDate
}

fun updateCurrentTime(): LocalDateTime {
    val newDate = Date()
    val difference = (newDate.time - lastLocalDate.time) / 1000
    lastLocalDate.time = newDate.time

    val calendarInstance = Calendar.getInstance()
    calendarInstance.timeInMillis = serverTime.timestamp
    calendarInstance.add(Calendar.MILLISECOND, difference.toInt())
    serverTime.timestamp = calendarInstance.time.time

    val currentTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(calendarInstance.timeInMillis * 1000),
            ZoneOffset.of(serverTime.offset).normalized())

    serverTime.hour = DateTimeFormatter.ofPattern(HOUR_FORMAT).format(currentTime)

    return currentTime
}

fun currentDateString(): String = SimpleDateFormat(DATE_FORMAT, Locale.US).format(Date())

fun getDateAsString(calendar: Calendar, format: String, locale: Locale = Locale.US): String {
    return SimpleDateFormat(format, locale).format(calendar.time)
}

fun dateStringWithoutDayName(context: Context, date: String): String {
    val dateFormat = context.resources.getString(R.string.date_format_month_text)
    val dateToFormat = SimpleDateFormat(DATE_FORMAT, Locale.US).parse(date)
    return SimpleDateFormat(dateFormat, Locale.getDefault()).format(dateToFormat)
}

fun dateStringWithoutDayName(dateFormat: String, date: String): String {
    val dateToFormat = SimpleDateFormat(DATE_FORMAT, Locale.US).parse(date)
    return SimpleDateFormat(dateFormat, Locale.getDefault()).format(dateToFormat)
}

fun getDateFromFormatToFormat(date: String, initialFormat: String, finalFormat: String): String {
    return tryOrDefault({
        val dateToFormat = SimpleDateFormat(initialFormat, Locale.US).parse(date)
        SimpleDateFormat(finalFormat, Locale.getDefault()).format(dateToFormat)
    }, "")
}

fun getDateWithFormat(date: String, format: String): String =
        getDateFromFormatToFormat(date, DATE_FORMAT, format)

fun dateStringWithMonthYear(date: String): String {
    return tryOrDefault({
        val dateFormat = ResourcesProvider.getString(R.string.date_format_month_year_text)
        val locale = Locale(Locale.getDefault().language)
        val dateToFormat = SimpleDateFormat(DATE_FORMAT, locale).parse(date)
        SimpleDateFormat(dateFormat, locale).format(dateToFormat)
    }, "")
}

fun getDayOfTheMonth(date: String): String {
    return tryOrDefault({
        val locale = Locale(Locale.getDefault().language)
        val dateToFormat = SimpleDateFormat(DATE_FORMAT_SEPARATOR_MINUS, locale).parse(date)
        SimpleDateFormat("dd", locale).format(dateToFormat)
    }, "")
}

fun getDateByFormat(date: String, format: String): String {
    return tryOrDefault({
        val locale = Locale(Locale.getDefault().language)
        val dateToFormat = SimpleDateFormat(DATE_FORMAT_SEPARATOR_MINUS, locale).parse(date)
        SimpleDateFormat(format, locale).format(dateToFormat)
    }, "")
}

fun getReadableFormatFromDate(context: Context, date: Date): String {
    val dateFormat = context.getString(R.string.date_format_month_year_text)
    val locale = Locale(Locale.getDefault().language)
    val sdf = SimpleDateFormat(dateFormat, locale)
    return sdf.format(date)
}


fun getExpectedTimeOfArrivalInMinutes(date: String): Int {
    return tryOrDefault({
        val df = SimpleDateFormat(DATE_FORMAT_1_TO_24_HOURS, Locale.ENGLISH)
        val expectedArrivalDate = df.parse(date)

        var minutesRemain = TimeUnit.MILLISECONDS.toMinutes(expectedArrivalDate.time - Date().time).toInt()
        minutesRemain = Math.max(0, minutesRemain)
        minutesRemain
    }, 0)
}

@JvmOverloads
fun stringToCalendar(time: String?, dateFormat: String = DATE_FORMAT): Calendar {
    return tryOrDefault({
        val format = SimpleDateFormat(dateFormat, Locale.ENGLISH)
        val date = format.parse(time)
        val calendar = GregorianCalendar.getInstance()
        calendar.timeInMillis = date.time
        calendar
    }, Calendar.getInstance())
}

fun isValidDate(text: String): Boolean {
    val dateFormat = if (text.contains("/")) {
        DATE_FORMAT_SEPARATOR_SLASH
    } else {
        DATE_FORMAT_SEPARATOR_MINUS
    }
    val date = stringToCalendar(text, dateFormat)
    return date != null
}

fun convertSecondsToMmSs(seconds: Long): String {
    val s = seconds % 60
    val m = seconds / 60 % 60
    return String.format(Locale.ENGLISH, "%02d:%02d", m, s)
}

fun checkTimeWithSchedules(timeToCheck: String, startTime: String, endTime: String): Int =
        checkTimeWithSchedules(timeToCheck, startTime, endTime, HOUR_FORMAT)

private fun checkTimeWithSchedules(timeToCheck: String, startTime: String, endTime: String, timeFormat: String): Int {
    return tryOrDefault({
        val time1 = SimpleDateFormat(timeFormat, Locale.ENGLISH).parse(startTime)
        val time2 = SimpleDateFormat(timeFormat, Locale.ENGLISH).parse(endTime)
        val dateToCheck = SimpleDateFormat(timeFormat, Locale.ENGLISH).parse(timeToCheck)
        checkTimeWithSchedules(dateToCheck, time1, time2)
    }, -1)
}

fun checkTimeWithSchedules(dateToCheck: Date, startDate: Date, endDate: Date): Int = when {
    dateToCheck.after(startDate) && dateToCheck.before(endDate) -> 0
    dateToCheck.before(endDate) -> 1
    dateToCheck.after(endDate) -> -1
    else -> -1
}

fun checkWithServerTime(startHour: String, endHour: String): Boolean {
    return if (startHour.isNotBlank() && endHour.isNotBlank()) {
        val start = LocalTime.parse(startHour)
        val end = LocalTime.parse(endHour)
        val serverLocalTime = serverDate().toLocalTime()
        serverLocalTime.isAfter(start) && serverLocalTime.isBefore(end)
    } else false
}

fun serverTimeIsBeforeThan(startTime: String): Boolean {
    val start = LocalTime.parse(startTime)
    val serverLocalTime = serverDate().toLocalTime()
    return serverLocalTime.isBefore(start)
}

fun getKeyForDates(key: String?): String {
    return key?.let {
        tryOrDefault({
            val locale = Locale(Locale.getDefault().language)
            SimpleDateFormat(FORMAT_EEEE, locale).format(
                    SimpleDateFormat(DATE_FORMAT_SEPARATOR_MINUS, locale).parse(it))
        }, "")
    } ?: run {
        ""
    }
}

fun minutesToParticularHour(hour: String, delimiter: String = ":"): Long {
    return tryOrDefault({
        val actualDate = serverDate()
        val result = hour.split(delimiter)
        val nextDate = LocalDateTime.of(actualDate.year,
                actualDate.month,
                actualDate.dayOfMonth,
                result[0].toInt(),
                if (result.size > 1) result[1].toInt() else 0,
                if (result.size > 2) result[2].toInt() else 0)
        if (nextDate.isBefore(actualDate)) {
            nextDate.plusDays(1)
        }
        Duration.between(actualDate, nextDate).seconds / 60
    }, 0L)
}

fun getHourFromMilitaryFormat(militaryHour: String): String {
    return tryOrDefault({
        SimpleDateFormat(HOUR_FORMAT_SCHEDULE, Locale.ENGLISH)
                .format(SimpleDateFormat(HOUR_FORMAT, Locale.ENGLISH).parse(militaryHour))
    }, militaryHour)
}

fun getHourFromDateFormat(dateString: String): String {
    return tryOrDefault({
        SimpleDateFormat(HOUR_FORMAT, Locale.ENGLISH)
                .format(SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH).parse(dateString))
    }, dateString)
}

fun getHourScheduleFromDateFormat(dateString: String): String {
    return tryOrDefault({
        SimpleDateFormat(HOUR_FORMAT_SCHEDULE, Locale.ENGLISH)
                .format(SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH).parse(dateString))
    }, dateString)
}

@JvmOverloads
fun getDateFromTimeStamp(time: Long, format: String = DATE_FORMAT): String {
    val calendar = Calendar.getInstance(Locale.ENGLISH)
    calendar.timeInMillis = time
    return getDateAsString(calendar, format)
}

fun getServerBirthday(date: String): String {
    return StringUtils.dateToString(stringToCalendar(date,
            DATE_FORMAT_SEPARATOR_SLASH), DATE_FORMAT_SEPARATOR_MINUS)
}

private fun getAgeCalendar(birthday: String): Calendar {
    val dateFormat = if (birthday.contains("/")) {
        DATE_FORMAT_SEPARATOR_SLASH
    } else {
        DATE_FORMAT_SEPARATOR_MINUS
    }
    return stringToCalendar(birthday, dateFormat)
}

private fun getAge(birthday: String): Int {
    val date = getAgeCalendar(birthday)
    val currentDate = Calendar.getInstance()
    var age = currentDate.get(Calendar.YEAR) - date.get(Calendar.YEAR)

    val currentMonth = currentDate.get(Calendar.MONTH)
    val monthBirth = date.get(Calendar.MONTH)
    if (monthBirth > currentMonth) {
        age--
    } else if (currentMonth == monthBirth) {
        val currentDay = currentDate.get(Calendar.DAY_OF_MONTH)
        val dayBirth = date.get(Calendar.DAY_OF_MONTH)
        if (dayBirth > currentDay) {
            age--
        }
    }
    return age
}

fun isAdult(birthday: String, adultMinAge: Int = ADULT_MIN_AGE): Boolean {
    val isSuccess = isValidDate(birthday) && birthday.isNotBlank()

    return if (isSuccess) {
        val currentDate = Calendar.getInstance()
        val date = getAgeCalendar(birthday)
        val yearsDiff = getAge(birthday)

        val condition = yearsDiff >= adultMinAge
        condition && currentDate.timeInMillis >= date.timeInMillis
    } else false
}

fun daysBetweenTwoDates(dateString: String, dateStringTwo: String): Long =
        getDifferenceTimeUnitBetweenDates(dateString, dateStringTwo, DATE_FORMAT_SEPARATOR_MINUS, TimeUnit.DAYS)

fun minutesBetweenTwoTimestamps(lowerTimestamp: Long, higherTimestamp: Long): Int {
    return tryOrDefault({
        val diff = higherTimestamp - lowerTimestamp
        TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS).toInt()
    }, Int.MAX_VALUE)
}

fun timestampFromXHoursAgo(hours: Int = 1): Long =
        Date().time - TimeUnit.MILLISECONDS.convert(hours.toLong(), TimeUnit.HOURS)

fun timestampWithGMT(): String {
    val timestamp: String
    val cal = Calendar.getInstance()
    val dfm = SimpleDateFormat(DATE_FORMAT_GMT, Locale.getDefault())
    dfm.timeZone = TimeZone.getTimeZone("GMT")
    timestamp = dfm.format(cal.time)
    return timestamp
}

fun getNotificationDisplay(context: Context, date: String): String? {
    val dateFormat = context.resources.getString(R.string.date_format_day_hour_text)
    val dateToFormat = SimpleDateFormat(DATE_FORMAT, Locale.US).parse(date.replace("T", " "))
    return SimpleDateFormat(dateFormat, Locale.getDefault()).format(dateToFormat)
}

fun getTimeRequestDisplay(date: String): String? {
    val dateToFormat = SimpleDateFormat(DATE_FORMAT, Locale.US).parse(date.replace("T", " "))
    return SimpleDateFormat(DATE_FORMAT_PAY, Locale.getDefault()).format(dateToFormat)
}

fun minutesBetweenTwoDates(dateString: String, dateStringTwo: String): Long {
    return getDifferenceTimeUnitBetweenDates(dateString, dateStringTwo, DATE_FORMAT, TimeUnit.MINUTES)
}

fun secondsBetweenTwoDates(dateString: String, dateStringTwo: String): Long {
    return getDifferenceTimeUnitBetweenDates(dateString, dateStringTwo, DATE_FORMAT, TimeUnit.SECONDS)
}

private fun getDifferenceTimeUnitBetweenDates(initialDate: String, endDate: String, dateFormat: String, timeUnit: TimeUnit): Long {
    val format = SimpleDateFormat(dateFormat, Locale.US)
    return tryOrDefault({
        val date1 = format.parse(initialDate)
        val date2 = format.parse(endDate)
        val diff = date2.time - date1.time
        timeUnit.convert(diff, TimeUnit.MILLISECONDS)
    }, -1)
}

fun getTimestampFromDate(date: String,
                         format: String = DATE_FORMAT_1_TO_24_HOURS,
                         defaultValue: Long = 0L): Long {
    return tryOrDefault({
        SimpleDateFormat(format, Locale.getDefault()).parse(date).time
    }, defaultValue)
}

fun Long.getDiffMillisUntilNow(): Long {
    return Date().time - this
}

fun Long.toMinutes(): Long {
    return TimeUnit.MILLISECONDS.toMinutes(this) -
            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(this))
}

fun formatDebtDate(date: String): String {
    return getDateFromFormatToFormat(date, DEBT_DATE_FORMAT, DEBT_PRETY_DATE_FORMAT)
}

fun formatRegularDate(date: String, format: String, outputFormat: String, ignoreLocal: Boolean = false): String {
    return tryOrDefault({
        val locale = Locale.getDefault()

        val dateToFormat = when (ignoreLocal) {
            true -> SimpleDateFormat(format)
            else -> SimpleDateFormat(format, locale).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
        }
        val output = dateToFormat.parse(date)
        SimpleDateFormat(outputFormat, locale).format(output)
    }, "")
}

fun String.getScheduleCopy(context: Context): String {
    val date = SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).parse(this)
    return when {
        date.isToday() -> context.getString(R.string.copy_schedule_today)
        date.isTomorrow() -> context.getString(R.string.copy_schedule_tomorrow)
        else -> dateStringWithMonthYear(this)
    }
}

fun String.isTodayOrTomorrow(): Boolean {
    val date = SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).parse(this)
    return date.isToday() || date.isTomorrow()
}

fun Date.isToday(): Boolean {
    val cal1 = Calendar.getInstance()
    val cal2 = Calendar.getInstance()
    cal1.time = Date()
    cal2.time = this
    return cal1.isSame(cal2, Calendar.DAY_OF_YEAR)
}

fun Date.isTomorrow(): Boolean {
    val cal1 = Calendar.getInstance()
    val cal2 = Calendar.getInstance()
    cal1.time = Date()
    cal1.set(Calendar.DAY_OF_YEAR, cal1.get(Calendar.DAY_OF_YEAR) + 1)
    cal2.time = this
    return cal1.isSame(cal2, Calendar.DAY_OF_YEAR)
}


fun Calendar.isSame(cal2: Calendar?, type: Int): Boolean {
    require(cal2 != null) { "The dates must not be null" }
    return get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
            get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            get(type) == cal2.get(type)
}
