package com.kadiri.elkitabi.core.designsystem.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext

val DarkColorScheme = darkColorScheme(
    primary            = PrimaryDark,
    onPrimary          = OnPrimaryDark,
    primaryContainer   = PrimaryContainerDark,
    secondary          = SecondaryDark,
    onSecondary        = OnSecondaryDark,
    secondaryContainer = SecondaryContainerDark,
    tertiary           = TertiaryDark,
    onTertiary         = OnTertiaryDark,
    background         = BackgroundDark,
    onBackground       = OnBackgroundDark,
    surface            = SurfaceDark,
    onSurface          = OnSurfaceDark,
    surfaceVariant     = SurfaceVariantDark,
    onSurfaceVariant   = OnSurfaceVariantDark,
    outline            = OutlineDark,
    outlineVariant     = OutlineVariantDark,
    error              = ErrorDark,
    onError            = OnErrorDark,
    errorContainer     = ErrorContainerDark
)

val LightColorScheme = lightColorScheme(
    primary            = PrimaryLight,
    onPrimary          = OnPrimaryLight,
    primaryContainer   = PrimaryContainerLight,
    onPrimaryContainer = OnPrimaryContainerLight,
    secondary          = SecondaryLight,
    onSecondary        = OnSecondaryLight,
    secondaryContainer = SecondaryContainerLight,
    tertiary           = TertiaryLight,
    onTertiary         = OnTertiaryLight,
    tertiaryContainer  = TertiaryContainerLight,
    background         = BackgroundLight,
    onBackground       = OnBackgroundLight,
    surface            = SurfaceLight,
    onSurface          = OnSurfaceLight,
    surfaceVariant     = SurfaceVariantLight,
    onSurfaceVariant   = OnSurfaceVariantLight,
    outline            = OutlineLight,
    outlineVariant     = OutlineVariantLight,
    error              = ErrorLight,
    onError            = OnErrorLight,
    errorContainer     = ErrorContainerLight
)

data class KadiriExtendedColors(
    val goldAccent: androidx.compose.ui.graphics.Color = GoldAccent,
    val arabesqueGold: androidx.compose.ui.graphics.Color = ArabesqueGold,
    val islamicGreen: androidx.compose.ui.graphics.Color = IslamicGreen,
    val mysticPurple: androidx.compose.ui.graphics.Color = MysticPurple,
    val warmAmber: androidx.compose.ui.graphics.Color = WarmAmber
)

val LocalKadiriExtendedColors = compositionLocalOf { KadiriExtendedColors() }

@Composable
fun KadiriTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    oledBlack: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> if (oledBlack) DarkColorScheme.copy(
            background = OLEDBlack,
            surface    = OLEDBlack
        ) else DarkColorScheme
        else -> LightColorScheme
    }

    CompositionLocalProvider(
        LocalKadiriExtendedColors provides KadiriExtendedColors()
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography  = KadiriTypography,
            shapes      = KadiriShapes,
            content     = content
        )
    }
}

object KadiriTheme {
    val extendedColors: KadiriExtendedColors
        @Composable get() = LocalKadiriExtendedColors.current
}
