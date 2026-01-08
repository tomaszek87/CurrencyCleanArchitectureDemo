package com.tomasz.nbpcurrencies.feature.currency.data.model

import com.google.gson.annotations.SerializedName
import com.tomasz.nbpcurrencies.feature.currency.domain.model.CurrencyDetails

data class CurrencyDetailsDto(
    @SerializedName("table")
    val table: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("rates")
    val rates: List<RateDto>,
)

fun CurrencyDetailsDto.toDomain(): CurrencyDetails {
    val domainRates = rates.map { it.toDomain() }
    return CurrencyDetails(
        table = table,
        name = currency,
        code = code,
        currentRate = domainRates.lastOrNull(),
        historicalRates = domainRates
    )
}
