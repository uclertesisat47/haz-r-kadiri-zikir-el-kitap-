package com.kadiri.elkitabi.core.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateUtils {

    private val trLocale = Locale("tr", "TR")

    fun formatDate(date: Date, pattern: String = "dd MMM yyyy"): String =
        SimpleDateFormat(pattern, trLocale).format(date)

    fun formatTime(date: Date, use24h: Boolean = true): String {
        val pattern = if (use24h) "HH:mm" else "hh:mm a"
        return SimpleDateFormat(pattern, trLocale).format(date)
    }

    fun formatDateTime(date: Date): String =
        SimpleDateFormat("dd MMM yyyy HH:mm", trLocale).format(date)

    fun today(): Date = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
    }.time

    fun startOfDay(epochMillis: Long): Long {
        val cal = Calendar.getInstance()
        cal.timeInMillis = epochMillis
        cal.set(Calendar.HOUR_OF_DAY, 0); cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0); cal.set(Calendar.MILLISECOND, 0)
        return cal.timeInMillis
    }

    fun startOfWeek(): Long {
        val cal = Calendar.getInstance()
        cal.set(Calendar.DAY_OF_WEEK, cal.firstDayOfWeek)
        return startOfDay(cal.timeInMillis)
    }

    fun startOfMonth(): Long {
        val cal = Calendar.getInstance()
        cal.set(Calendar.DAY_OF_MONTH, 1)
        return startOfDay(cal.timeInMillis)
    }

    fun isSameDay(a: Long, b: Long): Boolean =
        startOfDay(a) == startOfDay(b)

    fun daysBetween(start: Long, end: Long): Int =
        ((end - start) / (1000 * 60 * 60 * 24)).toInt()

    fun turkishDayName(date: Date = Date()): String {
        val days = listOf("Pazar","Pazartesi","Salı","Çarşamba","Perşembe","Cuma","Cumartesi")
        return days[Calendar.getInstance().apply { time = date }.get(Calendar.DAY_OF_WEEK) - 1]
    }

    fun formatDuration(seconds: Long): String {
        val h = seconds / 3600
        val m = (seconds % 3600) / 60
        val s = seconds % 60
        return if (h > 0) "%d:%02d:%02d".format(h, m, s) else "%02d:%02d".format(m, s)
    }
}
