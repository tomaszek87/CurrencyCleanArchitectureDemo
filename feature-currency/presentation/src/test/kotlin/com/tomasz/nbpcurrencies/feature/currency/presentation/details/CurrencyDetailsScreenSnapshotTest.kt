package com.tomasz.nbpcurrencies.feature.currency.presentation.details

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.tomasz.currency.core.ui.theme.CurrencyDemoTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.math.BigDecimal

@RunWith(JUnit4::class)
class CurrencyDetailsScreenSnapshotTest {

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5,
    )

    @Test
    fun currencyDetailsScreenSnapshot() {
        val mockCurrentRate = RateUi(
            "001/A/NBP/2026",
            "2026-01-01",
            BigDecimal("4.5000"),
            isDifferentByTenPercent = false
        )
        val mockDetails = CurrencyDetailsUi(
            code = "EUR",
            name = "euro",
            table = "A",
            currentRate = mockCurrentRate,
            historicalRates = listOf(
                RateUi(
                    id = "002/A/NBP/2026",
                    effectiveDate = "2026-01-02",
                    averageValue = BigDecimal("4.5890"),
                    isDifferentByTenPercent = false
                ),
                RateUi(
                    id = "003/A/NBP/2026",
                    effectiveDate = "2026-01-03",
                    averageValue = BigDecimal("5.1000"),
                    isDifferentByTenPercent = true
                ),
                RateUi(
                    id = "004/A/NBP/2026",
                    effectiveDate = "2026-01-04",
                    averageValue = BigDecimal("4.4500"),
                    isDifferentByTenPercent = false
                ),
                RateUi(
                    id = "005/A/NBP/2026",
                    effectiveDate = "2026-01-05",
                    averageValue = BigDecimal("4.4500"),
                    isDifferentByTenPercent = true
                )
            ).sortedByDescending { it.effectiveDate }
        )
        val mockState = CurrencyDetailsUiState(currencyDetails = mockDetails)

        paparazzi.snapshot {
            CurrencyDemoTheme {
                CurrencyDetailsContent(state = mockState)
            }
        }
    }

    @Test
    fun currencyDetailsScreenLoadingSnapshot() {
        val mockState = CurrencyDetailsUiState(isLoading = true)

        paparazzi.snapshot {
            CurrencyDemoTheme {
                CurrencyDetailsContent(state = mockState)
            }
        }
    }

    @Test
    fun currencyDetailsScreenErrorSnapshot() {
        val mockState = CurrencyDetailsUiState(error = "Details loading error")

        paparazzi.snapshot {
            CurrencyDemoTheme {
                CurrencyDetailsContent(state = mockState)
            }
        }
    }
}
