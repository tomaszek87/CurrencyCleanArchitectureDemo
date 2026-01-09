package com.tomasz.nbpcurrencies.feature.currency.presentation.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.tomasz.currency.core.ui.modifiers.accessibilityFocusWithClickAction
import com.tomasz.currency.core.ui.theme.CurrencyTheme.dimensions
import com.tomasz.currency.core.ui.theme.CurrencyTheme.typography
import com.tomasz.nbpcurrencies.feature.currency.presentation.R

@Composable
internal fun CurrencyItem(
    modifier: Modifier = Modifier,
    currency: CurrencyUi,
    onItemClick: () -> Unit,
) {
    val description = stringResource(
        id = R.string.currency_item_content_description,
        currency.name,
        currency.code,
        currency.averageRate.toPlainString()
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensions.paddingVerySmall)
            .accessibilityFocusWithClickAction(onClick = onItemClick)
            .padding(horizontal = dimensions.paddingLarge, vertical = dimensions.paddingLarge)
            .semantics(mergeDescendants = true) {
                contentDescription = description
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                modifier = Modifier.clearAndSetSemantics { },
                text = currency.name,
                style = typography.bodyLarge,
            )

            Text(
                modifier = Modifier.clearAndSetSemantics { },
                text = currency.code,
                style = typography.bodyMedium,
            )
        }
        Text(
            modifier = Modifier.clearAndSetSemantics { },
            text = currency.averageRate.toPlainString(),
            style = typography.bodyMedium
        )
    }
}
