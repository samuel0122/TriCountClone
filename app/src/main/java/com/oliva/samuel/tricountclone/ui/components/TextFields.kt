package com.oliva.samuel.tricountclone.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.oliva.samuel.tricountclone.domain.model.Currency

@Composable
fun BorderlessTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun CapsuleTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        shape = RoundedCornerShape(50)
    )
}

enum class CurrencyTextFieldType {
    Plain,
    Borderless,
    Capsule
}

@Composable
fun CurrencyTextField(
    value: Double,
    onValueChange: (Double) -> Unit,
    currency: Currency,
    type: CurrencyTextFieldType = CurrencyTextFieldType.Plain,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false
) {
    var currencyTextInput by rememberSaveable { mutableStateOf(value.toString()) }
    val currencyRegex = Regex("^\\d*(\\.\\d{0,2})?\$")

    when (type) {
        CurrencyTextFieldType.Plain ->
            TextField(
                modifier = modifier,
                value = currencyTextInput,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                trailingIcon = { Text(text = currency.symbol) },
                onValueChange = { newValue ->
                    if (newValue.isEmpty() || newValue.matches(currencyRegex)) {
                        currencyTextInput = newValue
                        currencyTextInput.toDoubleOrNull()?.let { onValueChange(it) }
                    }
                },
                enabled = enabled,
                readOnly = readOnly,
                placeholder = { Text("0,00") },
            )

        CurrencyTextFieldType.Borderless -> BorderlessTextField(
            modifier = modifier
                .fillMaxWidth(),
            value = currencyTextInput,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            trailingIcon = { Text(text = currency.symbol) },
            onValueChange = { newValue ->
                if (newValue.isEmpty() || newValue.matches(currencyRegex)) {
                    currencyTextInput = newValue
                    currencyTextInput.toDoubleOrNull()?.let { onValueChange(it) }
                }
            },
            enabled = enabled,
            readOnly = readOnly,
            placeholder = { Text("0,00") },
        )

        CurrencyTextFieldType.Capsule ->
            CapsuleTextField(
                modifier = modifier
                    .fillMaxWidth(),
                value = currencyTextInput,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                trailingIcon = { Text(text = currency.symbol) },
                onValueChange = { newValue ->
                    if (newValue.isEmpty() || newValue.matches(currencyRegex)) {
                        currencyTextInput = newValue
                        currencyTextInput.toDoubleOrNull()?.let { onValueChange(it) }
                    }
                },
                enabled = enabled,
                readOnly = readOnly,
                placeholder = { Text("0,00") },
            )
    }
}
