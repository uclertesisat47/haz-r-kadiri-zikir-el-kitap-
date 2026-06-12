package com.kadiri.elkitabi.core.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "kadiri_prefs")

@Singleton
class UserPreferencesDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore = context.dataStore

    companion object {
        val DARK_THEME         = booleanPreferencesKey("dark_theme")
        val OLED_BLACK         = booleanPreferencesKey("oled_black")
        val COLOR_SCHEME       = stringPreferencesKey("color_scheme")
        val FONT_SIZE_SCALE    = floatPreferencesKey("font_size_scale")
        val LANGUAGE           = stringPreferencesKey("language")
        val PRAYER_CITY        = stringPreferencesKey("prayer_city")
        val PRAYER_METHOD      = stringPreferencesKey("prayer_method")
        val PRAYER_LATITUDE    = floatPreferencesKey("prayer_latitude")
        val PRAYER_LONGITUDE   = floatPreferencesKey("prayer_longitude")
        val ZIKIR_SES          = stringPreferencesKey("zikir_ses")
        val ZIKIR_VIBRATION    = booleanPreferencesKey("zikir_vibration")
        val EKRAN_ACIK_KALSIN  = booleanPreferencesKey("ekran_acik_kalsin")
        val BILDIRIM_NAMAZ     = booleanPreferencesKey("bildirim_namaz")
        val BILDIRIM_GUNLUK    = booleanPreferencesKey("bildirim_gunluk")
        val ONBOARDING_DONE    = booleanPreferencesKey("onboarding_done")
        val LAST_ZIKIR_ID      = intPreferencesKey("last_zikir_id")
    }

    val darkTheme: Flow<Boolean>        = dataStore.data.map { it[DARK_THEME] ?: true }
    val oledBlack: Flow<Boolean>        = dataStore.data.map { it[OLED_BLACK] ?: false }
    val colorScheme: Flow<String>       = dataStore.data.map { it[COLOR_SCHEME] ?: "EMERALD" }
    val fontSizeScale: Flow<Float>      = dataStore.data.map { it[FONT_SIZE_SCALE] ?: 1.0f }
    val language: Flow<String>          = dataStore.data.map { it[LANGUAGE] ?: "tr" }
    val prayerCity: Flow<String>        = dataStore.data.map { it[PRAYER_CITY] ?: "İstanbul" }
    val prayerMethod: Flow<String>      = dataStore.data.map { it[PRAYER_METHOD] ?: "TURKEY" }
    val prayerLatitude: Flow<Float>     = dataStore.data.map { it[PRAYER_LATITUDE] ?: 41.0082f }
    val prayerLongitude: Flow<Float>    = dataStore.data.map { it[PRAYER_LONGITUDE] ?: 28.9784f }
    val zikirSes: Flow<String>          = dataStore.data.map { it[ZIKIR_SES] ?: "TITREŞIM" }
    val zikirVibration: Flow<Boolean>   = dataStore.data.map { it[ZIKIR_VIBRATION] ?: true }
    val ekranAcikKalsin: Flow<Boolean>  = dataStore.data.map { it[EKRAN_ACIK_KALSIN] ?: true }
    val bildirimNamaz: Flow<Boolean>    = dataStore.data.map { it[BILDIRIM_NAMAZ] ?: true }
    val bildirimGunluk: Flow<Boolean>   = dataStore.data.map { it[BILDIRIM_GUNLUK] ?: true }
    val onboardingDone: Flow<Boolean>   = dataStore.data.map { it[ONBOARDING_DONE] ?: false }
    val lastZikirId: Flow<Int>          = dataStore.data.map { it[LAST_ZIKIR_ID] ?: 1 }

    suspend fun setDarkTheme(value: Boolean)       = dataStore.edit { it[DARK_THEME] = value }
    suspend fun setOledBlack(value: Boolean)       = dataStore.edit { it[OLED_BLACK] = value }
    suspend fun setColorScheme(value: String)      = dataStore.edit { it[COLOR_SCHEME] = value }
    suspend fun setFontSizeScale(value: Float)     = dataStore.edit { it[FONT_SIZE_SCALE] = value }
    suspend fun setLanguage(value: String)         = dataStore.edit { it[LANGUAGE] = value }
    suspend fun setPrayerCity(value: String)       = dataStore.edit { it[PRAYER_CITY] = value }
    suspend fun setPrayerMethod(value: String)     = dataStore.edit { it[PRAYER_METHOD] = value }
    suspend fun setPrayerLatitude(value: Float)    = dataStore.edit { it[PRAYER_LATITUDE] = value }
    suspend fun setPrayerLongitude(value: Float)   = dataStore.edit { it[PRAYER_LONGITUDE] = value }
    suspend fun setZikirSes(value: String)         = dataStore.edit { it[ZIKIR_SES] = value }
    suspend fun setZikirVibration(value: Boolean)  = dataStore.edit { it[ZIKIR_VIBRATION] = value }
    suspend fun setEkranAcikKalsin(value: Boolean) = dataStore.edit { it[EKRAN_ACIK_KALSIN] = value }
    suspend fun setBildirimNamaz(value: Boolean)   = dataStore.edit { it[BILDIRIM_NAMAZ] = value }
    suspend fun setBildirimGunluk(value: Boolean)  = dataStore.edit { it[BILDIRIM_GUNLUK] = value }
    suspend fun setOnboardingDone(value: Boolean)  = dataStore.edit { it[ONBOARDING_DONE] = value }
    suspend fun setLastZikirId(value: Int)         = dataStore.edit { it[LAST_ZIKIR_ID] = value }
}
