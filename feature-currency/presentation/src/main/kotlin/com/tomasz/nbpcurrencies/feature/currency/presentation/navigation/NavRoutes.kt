package com.tomasz.nbpcurrencies.feature.currency.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object CurrencyList

@Serializable
data class CurrencyDetails(
    val table: String,
    val code: String,
)
