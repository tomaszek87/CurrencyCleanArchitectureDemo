package com.tomasz.nbpcurrencies.feature.currency.presentation.details

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.tomasz.currency.core.ui.theme.CurrencyDemoTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CurrencyDetailsScreenSnapshotTest {

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5,
        theme = "Theme.CurrencyDemo"
    )

    private val mockCurrentRate = RateUi(
        "001/A/NBP/2026",
        "2026-01-01",
        "4.5000",
        isDifferentByTenPercent = false
    )

    private val mockDetails = CurrencyDetailsUi(
        code = "EUR",
        name = "euro",
        table = "A",
        currentRate = mockCurrentRate,
        historicalRates = listOf(
            RateUi("002/A/NBP/2026", "2026-01-02", "4.5890", false),
            RateUi("003/A/NBP/2026", "2026-01-03", "5.1000", true),
            RateUi("004/A/NBP/2026", "2026-01-04", "4.4500", false),
            RateUi("005/A/NBP/2026", "2026-01-05", "4.4500", true)
        ).sortedByDescending { it.effectiveDate }
    )

    private val themes = listOf(
        false to "light",
        true to "dark"
    )

    private val states = listOf(
        "data_loaded" to CurrencyDetailsUiState(currencyDetails = mockDetails),
        "loading" to CurrencyDetailsUiState(isLoading = true),
        "error" to CurrencyDetailsUiState(error = "Details loading error")
    )

    private fun takeSnapshot(
        state: CurrencyDetailsUiState,
        darkTheme: Boolean,
        nameSuffix: String,
    ) {
        paparazzi.snapshot(name = "${nameSuffix}_${if (darkTheme) "dark" else "light"}") {
            CurrencyDemoTheme(darkTheme = darkTheme) {
                CurrencyDetailsContent(state = state)
            }
        }
    }

    @Test
    fun currencyDetailsAllStatesSnapshots() {
        for ((name, state) in states) {
            for ((darkTheme, _) in themes) {
                takeSnapshot(state, darkTheme, name)
            }
        }
    }
}
