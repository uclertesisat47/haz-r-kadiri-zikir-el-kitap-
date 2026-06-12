package com.kadiri.elkitabi.core.utils

import java.util.Calendar
import java.util.Date

data class HijriDate(
    val day: Int,
    val month: Int,
    val year: Int,
    val monthName: String
)

object HijriUtils {

    private val hijriMonthsTR = listOf(
        "Muharrem", "Safer", "Rebiülevvel", "Rebiülahir",
        "Cemaziyelevvel", "Cemaziyelahir", "Recep", "Şaban",
        "Ramazan", "Şevval", "Zilkade", "Zilhicce"
    )

    private val hijriMonthsAR = listOf(
        "مُحَرَّم", "صَفَر", "رَبِيعُ الأَوَّل", "رَبِيعُ الثَّانِي",
        "جُمَادَى الأُولَى", "جُمَادَى الثَّانِيَة", "رَجَب", "شَعْبَان",
        "رَمَضَان", "شَوَّال", "ذُو القَعْدَة", "ذُو الحِجَّة"
    )

    fun toHijri(date: Date = Date()): HijriDate {
        val cal = Calendar.getInstance()
        cal.time = date
        val y = cal.get(Calendar.YEAR)
        val m = cal.get(Calendar.MONTH) + 1
        val d = cal.get(Calendar.DAY_OF_MONTH)
        return gregorianToHijri(y, m, d)
    }

    private fun gregorianToHijri(gy: Int, gm: Int, gd: Int): HijriDate {
        val jd = gregorianToJulian(gy, gm, gd)
        return julianToHijri(jd)
    }

    private fun gregorianToJulian(gy: Int, gm: Int, gd: Int): Long {
        val a = (14 - gm) / 12
        val y = gy + 4800 - a
        val m = gm + 12 * a - 3
        return gd + (153 * m + 2) / 5 + 365L * y + y / 4 - y / 100 + y / 400 - 32045
    }

    private fun julianToHijri(jd: Long): HijriDate {
        val l  = jd - 1948440 + 10632
        val n  = (l - 1) / 10631
        val l2 = l - 10631 * n + 354
        val j  = (10985 - l2) / 5316 * (50 * l2 / 17719) + (l2 / 5670) * (43 * l2 / 15238)
        val l3 = l2 - (30 - j) / 15 * (17719 * j / 50) - (j / 16) * (15238 * j / 43) + 29
        val month  = 24 * l3 / 709
        val day    = l3 - 709 * month / 24
        val year   = 30 * n + j - 30
        val monthIdx = (month - 1).coerceIn(0, 11)
        return HijriDate(day.toInt(), month.toInt(), year.toInt(), hijriMonthsTR[monthIdx])
    }

    fun formatHijri(date: HijriDate, arabic: Boolean = false): String {
        return if (arabic) {
            val monthAR = hijriMonthsAR.getOrElse(date.month - 1) { "" }
            "${ArabicTextUtils.formatArabicNumber(date.day)} $monthAR ${ArabicTextUtils.formatArabicNumber(date.year)}"
        } else {
            "${date.day} ${date.monthName} ${date.year}"
        }
    }

    fun todayHijri(): HijriDate = toHijri(Date())

    /**
     * offset ay kadar ileri/geri giderek yaklaşık bir HijriDate döndürür.
     * Gerçek hesaplama için offset * 29.5 gün eklenir.
     */
    fun addMonths(base: HijriDate, offset: Int): HijriDate {
        if (offset == 0) return base
        var year  = base.year
        var month = base.month + offset
        while (month > 12) { month -= 12; year++ }
        while (month < 1)  { month += 12; year-- }
        val monthIdx = (month - 1).coerceIn(0, 11)
        return HijriDate(day = base.day, month = month, year = year, monthName = hijriMonthsTR[monthIdx])
    }
}
