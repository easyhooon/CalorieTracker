package com.kenshi.onboarding_presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import com.kenshi.core_ui.LocalSpacing

@ExperimentalComposeUiApi
@Composable
fun UnitTextField(
    value: String,
    onValueChange: (String) -> Unit,
    unit: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(
        color = MaterialTheme.colors.primaryVariant,
        fontSize = 70.sp
    )
) {
    val spacing = LocalSpacing.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = textStyle,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            ),
            singleLine = true,
            modifier = Modifier
                    // 원하는 만큼으로 텍스트필드 가로 크기를 조절
                .width(IntrinsicSize.Min)
                    // 하단 높이를 같게
                .alignBy(LastBaseline)
        )
        Spacer(modifier = Modifier.width(spacing.spaceSmall))
        Text(
            text = unit,
            modifier = Modifier.alignBy(LastBaseline)
        )
    }
}