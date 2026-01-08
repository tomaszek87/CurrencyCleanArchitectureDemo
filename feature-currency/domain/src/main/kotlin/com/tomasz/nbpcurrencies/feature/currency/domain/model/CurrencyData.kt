package com.tomasz.nbpcurrencies.feature.currency.domain.model

data class CurrencyData(
    val effectiveDate: String,
    val currencies: List<Currency>,
)
