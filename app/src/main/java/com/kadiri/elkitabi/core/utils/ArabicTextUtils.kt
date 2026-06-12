package com.kadiri.elkitabi.core.utils

import android.icu.text.ArabicShaping
import android.os.Build

object ArabicTextUtils {

    fun isArabic(text: String): Boolean {
        return text.any { c -> c.code in 0x0600..0x06FF || c.code in 0x0750..0x077F }
    }

    fun containsArabic(text: String): Boolean = text.any { isArabicChar(it) }

    private fun isArabicChar(c: Char): Boolean {
        val code = c.code
        return code in 0x0600..0x06FF ||
               code in 0x0750..0x077F ||
               code in 0xFB50..0xFDFF ||
               code in 0xFE70..0xFEFF
    }

    fun formatArabicNumber(number: Int): String {
        val arabicDigits = charArrayOf('٠','١','٢','٣','٤','٥','٦','٧','٨','٩')
        return number.toString().map { c ->
            if (c.isDigit()) arabicDigits[c.digitToInt()] else c
        }.joinToString("")
    }

    fun addTashkeel(text: String): String = text

    fun removeTashkeel(text: String): String {
        return text.filter { c ->
            val code = c.code
            code !in 0x064B..0x065F
        }
    }

    fun normalizeArabic(text: String): String {
        return text
            .replace('أ', 'ا')
            .replace('إ', 'ا')
            .replace('آ', 'ا')
            .replace('ة', 'ه')
            .replace('ى', 'ي')
    }
}
