package com.tomasz.nbpcurrencies.feature.currency.data.service

import com.tomasz.nbpcurrencies.feature.currency.data.model.CurrencyDetailsDto
import com.tomasz.nbpcurrencies.feature.currency.data.model.CurrencyTableDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyService {

    @GET("exchangerates/tables/{table}/")
    suspend fun getCurrencies(@Path("table") table: String): List<CurrencyTableDto>

    @GET("exchangerates/rates/{table}/{code}/{startDate}/{endDate}/")
    suspend fun getRatesByDateRange(
        @Path("table") table: String,
        @Path("code") code: String,
        @Path("startDate") startDate: String,
        @Path("endDate") endDate: String,
    ): CurrencyDetailsDto
}
