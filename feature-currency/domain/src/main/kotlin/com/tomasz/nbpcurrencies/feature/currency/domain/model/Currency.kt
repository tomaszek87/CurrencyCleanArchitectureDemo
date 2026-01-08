package com.tomasz.nbpcurrencies.feature.currency.domain.model

import java.math.BigDecimal

data class Currency(
    val code: String,
    val name: String,
    val table: String,
    val averageRate: BigDecimal,
)
