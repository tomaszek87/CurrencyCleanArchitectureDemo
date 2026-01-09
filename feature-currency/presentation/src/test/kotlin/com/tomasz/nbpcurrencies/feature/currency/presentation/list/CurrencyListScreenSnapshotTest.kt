package com.tomasz.nbpcurrencies.feature.currency.presentation.list

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.tomasz.currency.core.ui.theme.CurrencyDemoTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CurrencyListScreenSnapshotTest {

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5,
        theme = "Theme.CurrencyDemo"
    )

    private val mockCurrencies = listOf(
        CurrencyUi("BBD", "dolar barbadoski", "B", "1.7901"),
        CurrencyUi("EUR", "euro", "A", "4.2110"),
        CurrencyUi("NIO", "cordoba oro (Nikaragua)", "B", "0.0975"),
        CurrencyUi("USD", "dolar amerykanski", "A", "3.6901")
    )

    private fun takeSnapshot(
        state: CurrencyListUiState,
        darkTheme: Boolean,
        nameSuffix: String,
    ) {
        paparazzi.snapshot(name = "${nameSuffix}_${if (darkTheme) "dark" else "light"}") {
            CurrencyDemoTheme(darkTheme = darkTheme) {
                CurrencyListContent(
                    state = state,
                    onItemClick = { _, _ -> },
                    onRetry = {}
                )
            }
        }
    }

    @Test
    fun currencyListAllStatesSnapshots() {
        val themes = listOf(false, true)

        val states = listOf(
            "data_loaded" to CurrencyListUiState(currencies = mockCurrencies, effectiveDate = "2026-01-01"),
            "loading" to CurrencyListUiState(isLoading = true),
            "error" to CurrencyListUiState(error = "Connection error")
        )

        for ((name, state) in states) {
            for (darkTheme in themes) {
                takeSnapshot(state, darkTheme, name)
            }
        }
    }
}
