package com.tomasz.nbpcurrencies.feature.currency.presentation.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.tomasz.nbpcurrencies.feature.currency.presentation.details.CurrencyDetailsScreen
import com.tomasz.nbpcurrencies.feature.currency.presentation.list.CurrencyListScreen

fun NavGraphBuilder.currencyNavGraph(
    onCurrencyClick: (table: String, code: String) -> Unit,
    updateTopAppBarAction: (titleResId: Int, date: String?, showBackButton: Boolean) -> Unit,
) {
    composable<CurrencyList> {
        CurrencyListScreen(
            viewModel = hiltViewModel(),
            onItemClick = onCurrencyClick,
            updateTopAppBarAction = updateTopAppBarAction
        )
    }
    composable<CurrencyDetails> { backStackEntry ->
        val details = backStackEntry.toRoute<CurrencyDetails>()
        CurrencyDetailsScreen(
            table = details.table,
            code = details.code,
            viewModel = hiltViewModel(),
            updateTopAppBarAction = updateTopAppBarAction
        )
    }
}
