package com.tomasz.nbpcurrencies.feature.currency.data.model

import com.google.gson.annotations.SerializedName
import com.tomasz.nbpcurrencies.feature.currency.domain.model.Currency
import java.math.BigDecimal

data class CurrencyDto(
    @SerializedName("currency")
    val currency: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("mid")
    val averageRate: BigDecimal,
)

fun CurrencyDto.toDomain(table: String): Currency {
    return Currency(
        table = table,
        name = currency,
        code = code,
        averageRate = averageRate
    )
}
