package com.tomasz.currency.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.tomasz.currency.core.ui.R
import com.tomasz.currency.core.ui.theme.CurrencyTheme.dimensions

@Composable
fun ErrorRenderer(
    modifier: Modifier = Modifier,
    errorMessage: String,
    errorActionMessage: String? = null,
    onRetry: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = errorMessage)
        Spacer(modifier = Modifier.height(dimensions.paddingVeryLarge))
        onRetry?.let {
            Button(onClick = it) {
                Text(errorActionMessage ?: stringResource(id = R.string.retry_button_text))
            }
        }
    }
}
