package com.kadiri.elkitabi.core.designsystem.theme

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring

val SpringDefault = spring<Float>(
    dampingRatio = Spring.DampingRatioMediumBouncy,
    stiffness    = Spring.StiffnessMedium
)

val SpringSnappy = spring<Float>(
    dampingRatio = Spring.DampingRatioLowBouncy,
    stiffness    = Spring.StiffnessMediumLow
)

val SpringBouncy = spring<Float>(
    dampingRatio = Spring.DampingRatioHighBouncy,
    stiffness    = Spring.StiffnessLow
)

val SpringZikir = spring<Float>(
    dampingRatio = 0.6f,
    stiffness    = 400f
)

val EaseExpressive = CubicBezierEasing(0.34f, 1.56f, 0.64f, 1.0f)
val EaseIn         = CubicBezierEasing(0.4f, 0.0f, 1.0f, 1.0f)
val EaseOut        = CubicBezierEasing(0.0f, 0.0f, 0.2f, 1.0f)
val EaseInOut      = CubicBezierEasing(0.4f, 0.0f, 0.2f, 1.0f)

const val DURATION_SHORT  = 200
const val DURATION_MEDIUM = 350
const val DURATION_LONG   = 500
const val DURATION_EXTRA  = 700
