package com.tomasz.nbpcurrencies.feature.currency.presentation.list

import androidx.compose.material3.Surface
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.tomasz.currency.core.ui.theme.CurrencyDemoTheme
import com.tomasz.currency.core.ui.theme.CurrencyTheme.colors
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
        CurrencyUi(
            code = "BBD",
            name = "dolar barbadoski",
            table = "B",
            averageRate = "1.7901"
        ),
        CurrencyUi(
            code = "EUR",
            name = "euro",
            table = "A",
            averageRate = "4.2110"
        ),
        CurrencyUi(
            code = "NIO",
            name = "cordoba oro (Nikaragua)",
            table = "B",
            averageRate = "0.0975"
        ),
        CurrencyUi(
            code = "USD",
            name = "dolar amerykanski",
            table = "A",
            averageRate = "3.6901"
        )
    )

    private fun dataLoadedSnapshot(darkTheme: Boolean) {
        val mockState = CurrencyListUiState(currencies = mockCurrencies, effectiveDate = "2026-01-01")
        paparazzi.snapshot(name = if (darkTheme) "dark" else "light") {
            CurrencyDemoTheme(darkTheme = darkTheme) {
                Surface(color = colors.background) {
                    CurrencyListContent(
                        state = mockState,
                        onItemClick = { _, _ -> },
                        onRetry = {}
                    )
                }
            }
        }
    }

    private fun loadingSnapshot(darkTheme: Boolean) {
        val mockState = CurrencyListUiState(isLoading = true)
        paparazzi.snapshot(name = if (darkTheme) "loading_dark" else "loading_light") {
            CurrencyDemoTheme(darkTheme = darkTheme) {
                Surface(color = colors.background) {
                    CurrencyListContent(
                        state = mockState,
                        onItemClick = { _, _ -> },
                        onRetry = {}
                    )
                }
            }
        }
    }

    private fun errorSnapshot(darkTheme: Boolean) {
        val mockState = CurrencyListUiState(error = "Connection error")
        paparazzi.snapshot(name = if (darkTheme) "error_dark" else "error_light") {
            CurrencyDemoTheme(darkTheme = darkTheme) {
                Surface(color = colors.background) {
                    CurrencyListContent(
                        state = mockState,
                        onItemClick = { _, _ -> },
                        onRetry = {}
                    )
                }
            }
        }
    }

    @Test
    fun currencyListDataLoadedSnapshot() {
        dataLoadedSnapshot(darkTheme = false)
    }

    @Test
    fun currencyListScreenSnapshotDark() {
        dataLoadedSnapshot(darkTheme = true)
    }

    @Test
    fun currencyListScreenLoadingSnapshot() {
        loadingSnapshot(darkTheme = false)
    }

    @Test
    fun currencyListScreenLoadingSnapshotDark() {
        loadingSnapshot(darkTheme = true)
    }

    @Test
    fun currencyListScreenErrorSnapshot() {
        errorSnapshot(darkTheme = false)
    }

    @Test
    fun currencyListScreenErrorSnapshotDark() {
        errorSnapshot(darkTheme = true)
    }
}
