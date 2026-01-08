package com.tomasz.nbpcurrencies.feature.currency.domain.model

import java.math.BigDecimal

data class CurrencyDetails(
    val code: String,
    val name: String,
    val table: String,
    val currentRate: Rate? = null,
    val historicalRates: List<Rate> = emptyList(),
)

data class Rate(
    val id: String,
    val effectiveDate: String,
    val averageRate: BigDecimal,
)
