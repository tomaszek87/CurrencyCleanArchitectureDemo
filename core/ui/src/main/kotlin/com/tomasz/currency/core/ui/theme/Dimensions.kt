package com.tomasz.currency.core.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val paddingVerySmall: Dp = 2.dp,
    val paddingSmall: Dp = 4.dp,
    val paddingMedium: Dp = 8.dp,
    val paddingLarge: Dp = 12.dp,
    val paddingVeryLarge: Dp = 16.dp,
    val defaultElevation: Dp = 4.dp,
)

val LocalDimensions = compositionLocalOf { Dimensions() }
