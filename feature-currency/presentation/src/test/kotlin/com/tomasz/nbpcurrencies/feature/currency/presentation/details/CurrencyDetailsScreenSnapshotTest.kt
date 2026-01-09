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
            RateUi(
                id = "002/A/NBP/2026",
                effectiveDate = "2026-01-02",
                averageValue = "4.5890",
                isDifferentByTenPercent = false
            ),
            RateUi(
                id = "003/A/NBP/2026",
                effectiveDate = "2026-01-03",
                averageValue = "5.1000",
                isDifferentByTenPercent = true
            ),
            RateUi(
                id = "004/A/NBP/2026",
                effectiveDate = "2026-01-04",
                averageValue = "4.4500",
                isDifferentByTenPercent = false
            ),
            RateUi(
                id = "005/A/NBP/2026",
                effectiveDate = "2026-01-05",
                averageValue = "4.4500",
                isDifferentByTenPercent = true
            )
        ).sortedByDescending { it.effectiveDate }
    )

    private fun dataLoadedSnapshot(darkTheme: Boolean) {
        val mockState = CurrencyDetailsUiState(currencyDetails = mockDetails)
        paparazzi.snapshot(name = if (darkTheme) "dark" else "light") {
            CurrencyDemoTheme(darkTheme = darkTheme) {
                CurrencyDetailsContent(state = mockState)
            }
        }
    }

    private fun loadingSnapshot(darkTheme: Boolean) {
        val mockState = CurrencyDetailsUiState(isLoading = true)
        paparazzi.snapshot(name = if (darkTheme) "loading_dark" else "loading_light") {
            CurrencyDemoTheme(darkTheme = darkTheme) {
                CurrencyDetailsContent(state = mockState)
            }
        }
    }

    private fun errorSnapshot(darkTheme: Boolean) {
        val mockState = CurrencyDetailsUiState(error = "Details loading error")
        paparazzi.snapshot(name = if (darkTheme) "error_dark" else "error_light") {
            CurrencyDemoTheme(darkTheme = darkTheme) {
                CurrencyDetailsContent(state = mockState)
            }
        }
    }

    @Test
    fun currencyDetailsDataLoadedSnapshotLight() {
        dataLoadedSnapshot(darkTheme = false)
    }

    @Test
    fun currencyDetailsScreenDataLoadedSnapshotDark() {
        dataLoadedSnapshot(darkTheme = true)
    }

    @Test
    fun currencyDetailsScreenLoadingSnapshotLight() {
        loadingSnapshot(darkTheme = false)
    }

    @Test
    fun currencyDetailsScreenLoadingSnapshotDark() {
        loadingSnapshot(darkTheme = true)
    }

    @Test
    fun currencyDetailsScreenErrorSnapshotLight() {
        errorSnapshot(darkTheme = false)
    }

    @Test
    fun currencyDetailsScreenErrorSnapshotDark() {
        errorSnapshot(darkTheme = true)
    }
}
