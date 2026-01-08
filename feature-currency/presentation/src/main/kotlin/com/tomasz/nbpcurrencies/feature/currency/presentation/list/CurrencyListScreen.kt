package com.tomasz.nbpcurrencies.feature.currency.presentation.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tomasz.currency.core.ui.components.ErrorRenderer
import com.tomasz.currency.core.ui.theme.AccessibilityPreview
import com.tomasz.currency.core.ui.theme.CurrencyDemoTheme
import com.tomasz.currency.core.ui.theme.CurrencyTheme.colors
import com.tomasz.currency.core.ui.theme.CurrencyTheme.dimensions
import com.tomasz.nbpcurrencies.feature.currency.presentation.R
import java.math.BigDecimal

@Composable
internal fun CurrencyListScreen(
    viewModel: CurrencyListViewModel,
    onItemClick: (String, String) -> Unit,
    updateTopAppBarAction: (titleResId: Int, date: String?, showBackButton: Boolean) -> Unit,
) {
    val currentUpdateTopAppBarAction by rememberUpdatedState(updateTopAppBarAction)
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.effectiveDate) {
        currentUpdateTopAppBarAction(
            R.string.currencies_screen_title,
            state.effectiveDate,
            false
        )
    }

    CurrencyListContent(
        state = state,
        onItemClick = onItemClick,
        onRetry = viewModel::onRetry
    )
}

@Composable
internal fun CurrencyListContent(
    state: CurrencyListUiState,
    onItemClick: (String, String) -> Unit,
    onRetry: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(
                    items = state.currencies,
                    key = { currency -> currency.code }
                ) { currency ->
                    CurrencyItem(
                        currency = currency,
                        onItemClick = { onItemClick(currency.table, currency.code) }
                    )

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
                errorMessage = it,
                onRetry = onRetry,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(dimensions.paddingVeryLarge)
            )
        }
    }
}

@AccessibilityPreview
@Composable
private fun CurrencyListScreenPreview() {
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

    CurrencyDemoTheme {
        Surface(color = colors.background) {
            CurrencyListContent(
                state = mockState,
                onItemClick = { _, _ -> },
                onRetry = {}
            )
        }
    }
}
