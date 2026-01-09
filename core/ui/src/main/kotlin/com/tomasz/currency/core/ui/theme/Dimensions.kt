package com.tomasz.currency.core.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val paddingXS: Dp = 2.dp,
    val paddingS: Dp = 4.dp,
    val paddingM: Dp = 8.dp,
    val paddingL: Dp = 12.dp,
    val paddingXL: Dp = 16.dp,
    val defaultElevation: Dp = 4.dp,
)

val LocalDimensions = compositionLocalOf { Dimensions() }
