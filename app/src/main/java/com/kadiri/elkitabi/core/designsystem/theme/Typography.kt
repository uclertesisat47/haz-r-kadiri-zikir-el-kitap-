package com.kadiri.elkitabi.core.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.kadiri.elkitabi.R

val AmiriRegular = FontFamily(Font(R.font.amiri_regular, FontWeight.Normal))
val AmiriBold    = FontFamily(Font(R.font.amiri_bold, FontWeight.Bold))
val AmiriQuran   = FontFamily(Font(R.font.amiri_quran, FontWeight.Normal))
val NotoKufi     = FontFamily(Font(R.font.noto_kufi_arabic, FontWeight.Normal))
val NunitoFamily = FontFamily(
    Font(R.font.nunito_regular, FontWeight.Normal),
    Font(R.font.nunito_semibold, FontWeight.SemiBold),
    Font(R.font.nunito_bold, FontWeight.Bold),
    Font(R.font.nunito_extrabold, FontWeight.ExtraBold)
)

val KadiriTypography = Typography(
    displayLarge   = TextStyle(fontFamily = AmiriBold,    fontSize = 57.sp, lineHeight = 64.sp),
    displayMedium  = TextStyle(fontFamily = AmiriBold,    fontSize = 45.sp, lineHeight = 52.sp),
    displaySmall   = TextStyle(fontFamily = AmiriBold,    fontSize = 36.sp, lineHeight = 44.sp),
    headlineLarge  = TextStyle(fontFamily = AmiriBold,    fontSize = 32.sp, lineHeight = 40.sp, fontWeight = FontWeight.Bold),
    headlineMedium = TextStyle(fontFamily = AmiriBold,    fontSize = 28.sp, lineHeight = 36.sp),
    headlineSmall  = TextStyle(fontFamily = AmiriBold,    fontSize = 24.sp, lineHeight = 32.sp),
    titleLarge     = TextStyle(fontFamily = NunitoFamily, fontSize = 22.sp, lineHeight = 28.sp, fontWeight = FontWeight.Bold),
    titleMedium    = TextStyle(fontFamily = NunitoFamily, fontSize = 16.sp, lineHeight = 24.sp, fontWeight = FontWeight.SemiBold),
    titleSmall     = TextStyle(fontFamily = NunitoFamily, fontSize = 14.sp, lineHeight = 20.sp, fontWeight = FontWeight.SemiBold),
    bodyLarge      = TextStyle(fontFamily = NunitoFamily, fontSize = 16.sp, lineHeight = 24.sp),
    bodyMedium     = TextStyle(fontFamily = NunitoFamily, fontSize = 14.sp, lineHeight = 20.sp),
    bodySmall      = TextStyle(fontFamily = NunitoFamily, fontSize = 12.sp, lineHeight = 16.sp),
    labelLarge     = TextStyle(fontFamily = NunitoFamily, fontSize = 14.sp, lineHeight = 20.sp, fontWeight = FontWeight.SemiBold),
    labelMedium    = TextStyle(fontFamily = NunitoFamily, fontSize = 12.sp, lineHeight = 16.sp, fontWeight = FontWeight.Medium),
    labelSmall     = TextStyle(fontFamily = NunitoFamily, fontSize = 11.sp, lineHeight = 16.sp, fontWeight = FontWeight.Medium)
)

// ÖZEL: Arapça büyük metin stili
val ArabicTextLarge   = TextStyle(fontFamily = AmiriQuran,   fontSize = 22.sp, lineHeight = 44.sp, textAlign = TextAlign.End, letterSpacing = 0.5.sp)
val ArabicTextMedium  = TextStyle(fontFamily = AmiriRegular, fontSize = 18.sp, lineHeight = 36.sp, textAlign = TextAlign.End)
val ArabicTextSmall   = TextStyle(fontFamily = AmiriRegular, fontSize = 15.sp, lineHeight = 28.sp, textAlign = TextAlign.End)
val ZikirCounterStyle = TextStyle(fontFamily = AmiriBold,    fontSize = 72.sp, lineHeight = 80.sp, textAlign = TextAlign.Center)
val ZikirNameStyle    = TextStyle(fontFamily = AmiriQuran,   fontSize = 28.sp, lineHeight = 42.sp, textAlign = TextAlign.Center, letterSpacing = 1.sp)
