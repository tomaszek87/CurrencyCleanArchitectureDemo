package com.tomasz.currency.core.ui.modifiers

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tomasz.currency.core.ui.theme.CurrencyTheme.colors
import com.tomasz.currency.core.ui.theme.CurrencyTheme.dimensions

fun Modifier.accessibilityFocus(interactionSource: MutableInteractionSource): Modifier =
    composed {
        val isFocused by interactionSource.collectIsFocusedAsState()

        this.border(
            border = if (isFocused) {
                BorderStroke(dimensions.paddingXS, colors.outline)
            } else {
                BorderStroke(0.dp, Color.Transparent)
            },
            shape = RoundedCornerShape(dimensions.paddingS)
        )
    }

fun Modifier.accessibilityFocusWithClickAction(onClick: () -> Unit): Modifier =
    composed {
        val interactionSource = remember { MutableInteractionSource() }
        val isFocused by interactionSource.collectIsFocusedAsState()

        this
            .border(
                border = if (isFocused) {
                    BorderStroke(dimensions.paddingXS, colors.outline)
                } else {
                    BorderStroke(0.dp, Color.Transparent)
                },
                shape = RoundedCornerShape(dimensions.paddingS)
            )
            .clickable(
                interactionSource = interactionSource,
                onClick = onClick
            )
    }
