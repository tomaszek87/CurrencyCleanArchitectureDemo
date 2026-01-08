package com.tomasz.nbpcurrencies.feature.currency.presentation.list

import androidx.compose.runtime.Immutable
import com.tomasz.nbpcurrencies.feature.currency.domain.model.Currency
import java.math.BigDecimal

data class CurrencyListUiState(
    val isLoading: Boolean = false,
    val currencies: List<CurrencyUi> = emptyList(),
    val error: String? = null,
    val effectiveDate: String? = null,
)

@Immutable
data class CurrencyUi(
    val code: String,
    val name: String,
    val table: String,
    val averageRate: BigDecimal,
)

fun Currency.toUi(): CurrencyUi {
    return CurrencyUi(
        code = code,
        name = name,
        table = table,
        averageRate = averageRate
    )
}

fun List<Currency>.toUi(): List<CurrencyUi> =
    map { it.toUi() }

fun List<CurrencyUi>.sortedByName(): List<CurrencyUi> =
    sortedBy { it.name.lowercase() }
