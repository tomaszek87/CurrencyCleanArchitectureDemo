package com.tomasz.nbpcurrencies.feature.currency.presentation.list

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.tomasz.currency.core.ui.theme.CurrencyDemoTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.math.BigDecimal

@RunWith(JUnit4::class)
class CurrencyListScreenSnapshotTest {

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5,
        theme = "Theme.CurrencyDemo"
    )

    @Test
    fun currencyListScreenSnapshot() {
        val mockCurrencies = listOf(
            CurrencyUi(
                code = "BBD",
                name = "dolar barbadoski",
                table = "B",
                averageRate = BigDecimal("1.7901")
            ),
            CurrencyUi(
                code = "EUR",
                name = "euro",
                table = "A",
                averageRate = BigDecimal("4.2110")
            ),
            CurrencyUi(
                code = "NIO",
                name = "cordoba oro (Nikaragua)",
                table = "B",
                averageRate = BigDecimal("0.0975")
            ),
            CurrencyUi(
                code = "USD",
                name = "dolar amerykański",
                table = "A",
                averageRate = BigDecimal("3.6901")
            )
        )
        val mockState = CurrencyListUiState(currencies = mockCurrencies, effectiveDate = "2026-01-01")

        paparazzi.snapshot {
            CurrencyDemoTheme {
                CurrencyListContent(
                    state = mockState,
                    onItemClick = { _, _ -> },
                    onRetry = {}
                )
            }
        }
    }

    @Test
    fun currencyListScreenLoadingSnapshot() {
        val mockState = CurrencyListUiState(isLoading = true)

        paparazzi.snapshot {
            CurrencyDemoTheme {
                CurrencyListContent(
                    state = mockState,
                    onItemClick = { _, _ -> },
                    onRetry = {}
                )
            }
        }
    }

    @Test
    fun currencyListScreenErrorSnapshot() {
        val mockState = CurrencyListUiState(error = "Connection error")

        paparazzi.snapshot {
            CurrencyDemoTheme {
                CurrencyListContent(
                    state = mockState,
                    onItemClick = { _, _ -> },
                    onRetry = {}
                )
            }
        }
    }
}
