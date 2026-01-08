package com.tomasz.nbpcurrencies.feature.currency.data.model

import com.google.gson.annotations.SerializedName

data class CurrencyTableDto(
    @SerializedName("no")
    val id: String,
    @SerializedName("table")
    val table: String,
    @SerializedName("effectiveDate")
    val effectiveDate: String,
    @SerializedName("rates")
    val rates: List<CurrencyDto>,
)
