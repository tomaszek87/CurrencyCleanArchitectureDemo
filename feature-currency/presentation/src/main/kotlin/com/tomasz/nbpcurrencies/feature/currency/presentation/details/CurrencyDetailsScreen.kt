package com.tomasz.nbpcurrencies.feature.currency.presentation.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tomasz.currency.core.ui.components.ErrorRenderer
import com.tomasz.currency.core.ui.theme.AccessibilityPreview
import com.tomasz.currency.core.ui.theme.CurrencyDemoTheme
import com.tomasz.currency.core.ui.theme.CurrencyTheme.dimensions
import com.tomasz.nbpcurrencies.feature.currency.presentation.R
import java.math.BigDecimal

@Composable
internal fun CurrencyDetailsScreen(
    table: String,
    code: String,
    viewModel: CurrencyDetailsViewModel = hiltViewModel(),
    updateTopAppBarAction: (titleResId: Int, date: String?, showBackButton: Boolean) -> Unit,
) {
    val currentUpdateTopAppBarAction by rememberUpdatedState(updateTopAppBarAction)
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(table, code) {
        currentUpdateTopAppBarAction(
            R.string.currency_details_screen_title,
            null,
            true
        )
        viewModel.getCurrencyDetails(table, code)
    }

    CurrencyDetailsContent(state = state)
}

@Composable
internal fun CurrencyDetailsContent(state: CurrencyDetailsUiState) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        state.currencyDetails?.let { currencyDetails ->
            // all content wrapped in lazy column for accessibility purposes
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = dimensions.paddingVeryLarge)
            ) {
                item {
                    DetailsHeader(
                        currencyDetails.name,
                        currencyDetails.code,
                        currencyDetails.currentRate
                    )
                }

                item {
                    HistoryHeader(
                        modifier = Modifier.padding(
                            top = dimensions.paddingVeryLarge,
                            bottom = dimensions.paddingMedium
                        )
                    )
                }

                items(
                    items = currencyDetails.historicalRates,
                    key = { rate -> rate.id }
                ) { rate ->
                    HistoryItem(rate = rate)

                    HorizontalDivider()
                }
            }
        }

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .testTag("loading_indicator")
            )
        }

        state.error?.let {
            ErrorRenderer(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(dimensions.paddingVeryLarge),
                errorMessage = it
            )
        }
    }
}

@AccessibilityPreview
@Composable
private fun CurrencyDetailsScreenPreview() {
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

    CurrencyDemoTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            CurrencyDetailsContent(state = mockState)
        }
    }
}
