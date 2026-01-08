package com.tomasz.nbpcurrencies.feature.currency.di

import com.tomasz.nbpcurrencies.feature.currency.data.repository.CurrencyRepositoryImpl
import com.tomasz.nbpcurrencies.feature.currency.domain.repository.CurrencyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCurrencyRepository(repository: CurrencyRepositoryImpl): CurrencyRepository {
        return repository
    }
}
