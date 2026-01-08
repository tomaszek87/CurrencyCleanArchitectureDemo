package com.tomasz.currency.core.ui.theme

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Light Mode",
    showBackground = true,
    group = "Portrait"
)
@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    group = "Portrait"
)
@Preview(
    name = "Light Mode - Large Font",
    showBackground = true,
    fontScale = 2f,
    group = "Large Font Portrait"
)
@Preview(
    name = "Dark Mode - Large Font",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    fontScale = 2f,
    group = "Large Font Portrait"
)
@Preview(
    name = "Landscape Light",
    showBackground = true,
    widthDp = 800,
    heightDp = 360,
    group = "Landscape"
)
@Preview(
    name = "Landscape Dark",
    showBackground = true,
    widthDp = 800,
    heightDp = 360,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    group = "Landscape"
)
@Preview(
    name = "Landscape Light - Large Font",
    showBackground = true,
    widthDp = 800,
    heightDp = 360,
    fontScale = 2f,
    group = "Large Font Landscape"
)
@Preview(
    name = "Landscape Dark - Large Font",
    showBackground = true,
    widthDp = 800,
    heightDp = 360,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    fontScale = 2f,
    group = "Large Font Landscape"
)
annotation class AccessibilityPreview
