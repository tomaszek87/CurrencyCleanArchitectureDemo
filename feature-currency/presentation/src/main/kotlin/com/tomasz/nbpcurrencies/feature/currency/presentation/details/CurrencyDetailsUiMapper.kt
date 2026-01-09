package com.tomasz.nbpcurrencies.feature.currency.presentation.details

import androidx.compose.runtime.Immutable
import com.tomasz.nbpcurrencies.feature.currency.domain.model.CurrencyDetails
import com.tomasz.nbpcurrencies.feature.currency.domain.model.Rate
import java.math.BigDecimal
import java.math.RoundingMode

data class CurrencyDetailsUiState(
    val isLoading: Boolean = false,
    val currencyDetails: CurrencyDetailsUi? = null,
    val error: String? = null,
)

data class CurrencyDetailsUi(
    val code: String,
    val name: String,
    val table: String,
    val currentRate: RateUi? = null,
    val historicalRates: List<RateUi> = emptyList(),
)

@Immutable
data class RateUi(
    val id: String,
    val effectiveDate: String,
    val averageValue: String,
    val isDifferentByTenPercent: Boolean,
)

fun CurrencyDetails.toUi(): CurrencyDetailsUi {
    val currentMidRate = this.currentRate?.averageRate ?: BigDecimal.ZERO
    return CurrencyDetailsUi(
        code = code,
        name = name,
        table = table,
        currentRate = currentRate?.toUi(currentMidRate),
        historicalRates = historicalRates.map { it.toUi(currentMidRate) }.sortedByDate()
    )
}

fun Rate.toUi(currentRate: BigDecimal): RateUi {
    val percentageDiff = if (currentRate > BigDecimal.ZERO) {
        averageRate.subtract(currentRate).abs().divide(currentRate, 4, RoundingMode.HALF_UP)
    } else {
        BigDecimal.ZERO
    }

    return RateUi(
        id = id,
        effectiveDate = effectiveDate,
        averageValue = averageRate.toPlainString(),
        isDifferentByTenPercent = percentageDiff > BigDecimal("0.1")
    )
}

fun List<RateUi>.sortedByDate(): List<RateUi> =
    sortedByDescending { it.effectiveDate }
