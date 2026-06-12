package com.kadiri.elkitabi.core.utils

import com.batoulapps.adhan.CalculationMethod
import com.batoulapps.adhan.Coordinates
import com.batoulapps.adhan.PrayerTimes
import com.batoulapps.adhan.data.DateComponents
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

data class PrayerTimeEntry(
    val name: String,
    val nameAr: String,
    val time: Date,
    val isCurrent: Boolean = false,
    val isNext: Boolean = false
)

object PrayerTimeUtils {

    fun getPrayerTimes(
        latitude: Double,
        longitude: Double,
        date: Date = Date(),
        methodName: String = "TURKEY"
    ): List<PrayerTimeEntry> {
        val coordinates = Coordinates(latitude, longitude)
        val cal = Calendar.getInstance()
        cal.time = date
        val dateComponents = DateComponents(
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH) + 1,
            cal.get(Calendar.DAY_OF_MONTH)
        )
        val params = when (methodName) {
            "TURKEY"  -> CalculationMethod.TURKEY.parameters
            "EGYPT"   -> CalculationMethod.EGYPTIAN.parameters
            "KARACHI" -> CalculationMethod.KARACHI.parameters
            "MWL"     -> CalculationMethod.MUSLIM_WORLD_LEAGUE.parameters
            else      -> CalculationMethod.TURKEY.parameters
        }
        val prayerTimes = PrayerTimes(coordinates, dateComponents, params)
        val now = Date()

        val entries = listOf(
            PrayerTimeEntry("İmsak",   "الإمساك",   prayerTimes.fajr),
            PrayerTimeEntry("Güneş",   "الشروق",    prayerTimes.sunrise),
            PrayerTimeEntry("Öğle",    "الظهر",     prayerTimes.dhuhr),
            PrayerTimeEntry("İkindi",  "العصر",     prayerTimes.asr),
            PrayerTimeEntry("Akşam",   "المغرب",    prayerTimes.maghrib),
            PrayerTimeEntry("Yatsı",   "العشاء",    prayerTimes.isha)
        )

        val currentIdx = entries.indexOfLast { it.time.before(now) || it.time == now }
        return entries.mapIndexed { index, entry ->
            entry.copy(
                isCurrent = index == currentIdx,
                isNext    = index == (currentIdx + 1) % entries.size
            )
        }
    }

    fun getNextPrayer(times: List<PrayerTimeEntry>): PrayerTimeEntry? =
        times.firstOrNull { it.isNext }

    fun formatTime(date: Date, use24h: Boolean = true): String {
        val cal = Calendar.getInstance()
        cal.time = date
        val h = cal.get(Calendar.HOUR_OF_DAY)
        val m = cal.get(Calendar.MINUTE)
        return if (use24h) {
            "%02d:%02d".format(h, m)
        } else {
            val h12 = if (h == 0 || h == 12) 12 else h % 12
            val amPm = if (h < 12) "ÖÖ" else "ÖS"
            "%02d:%02d %s".format(h12, m, amPm)
        }
    }
}
