package com.tomasz.currency

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.tomasz.currency.core.ui.theme.CurrencyDemoTheme
import com.tomasz.currency.core.ui.theme.CurrencyTheme.colors
import com.tomasz.nbpcurrencies.feature.currency.presentation.navigation.CurrencyDetails
import com.tomasz.nbpcurrencies.feature.currency.presentation.navigation.CurrencyList
import com.tomasz.nbpcurrencies.feature.currency.presentation.navigation.currencyNavGraph
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurrencyDemoTheme {
                val navController = rememberNavController()
                Surface(color = colors.background) {
                    val topAppBarState by sharedViewModel.topAppBarState.collectAsStateWithLifecycle()

                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Column {
                                        topAppBarState.titleResId?.let { Text(stringResource(id = it)) }
                                        topAppBarState.effectiveDate?.let {
                                            Text(it, style = MaterialTheme.typography.bodySmall)
                                        }
                                    }
                                },
                                navigationIcon = {
                                    if (topAppBarState.showBackButton) {
                                        IconButton(onClick = { navController.navigateUp() }) {
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                                contentDescription = stringResource(
                                                    id = R.string.back_button_content_description
                                                )
                                            )
                                        }
                                    }
                                }
                            )
                        }
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = CurrencyList,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            currencyNavGraph(
                                onCurrencyClick = { table, code ->
                                    navController.navigate(CurrencyDetails(table, code))
                                },
                                updateTopAppBarAction = sharedViewModel::updateTopAppBarAction
                            )
                        }
                    }
                }
            }
        }
    }
}
