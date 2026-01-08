package com.tomasz.nbpcurrencies.feature.currency.data.model

import com.google.gson.annotations.SerializedName
import com.tomasz.nbpcurrencies.feature.currency.domain.model.Rate
import java.math.BigDecimal

data class RateDto(
    @SerializedName("no")
    val id: String,
    @SerializedName("effectiveDate")
    val effectiveDate: String,
    @SerializedName("mid")
    val averageRate: BigDecimal,
)

fun RateDto.toDomain(): Rate {
    return Rate(
        id = id,
        effectiveDate = effectiveDate,
        averageRate = averageRate
    )
}
