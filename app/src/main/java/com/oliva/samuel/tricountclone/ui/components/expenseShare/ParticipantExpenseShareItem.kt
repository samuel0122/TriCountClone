package com.oliva.samuel.tricountclone.ui.components.expenseShare

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oliva.samuel.tricountclone.domain.model.Currency
import com.oliva.samuel.tricountclone.domain.model.ExpenseShareModel
import com.oliva.samuel.tricountclone.domain.model.ParticipantModel
import com.oliva.samuel.tricountclone.ui.components.CircularCheckbox
import com.oliva.samuel.tricountclone.utils.StringFormats

@Composable
fun ParticipantExpenseShareItem(
    modifier: Modifier = Modifier,
    participantModel: ParticipantModel,
    expenseShareModel: ExpenseShareModel?,
    expenseCurrency: Currency,
    onParticipantExpenseShareSelected: (ParticipantModel) -> Unit
) {
    val isChecked = expenseShareModel != null

    Box(
        modifier = Modifier
            .clickable { onParticipantExpenseShareSelected(participantModel) }
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularCheckbox(
                checked = isChecked,
            )

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = participantModel.name
            )

            Spacer(modifier = Modifier.weight(1f))

            AnimatedVisibility(
                visible = isChecked,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                expenseShareModel?.let {
                    Text(
                        text = StringFormats.formatCurrency(
                            value = expenseShareModel.amountOwed,
                            currency = expenseCurrency
                        ),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ParticipantExpenseShareItemPreview() {
    ParticipantExpenseShareItem(
        participantModel = ParticipantModel.default().copy(name = "Samuel"),
        expenseShareModel = null,
        expenseCurrency = Currency.Euro,
        onParticipantExpenseShareSelected = {}
    )
}
