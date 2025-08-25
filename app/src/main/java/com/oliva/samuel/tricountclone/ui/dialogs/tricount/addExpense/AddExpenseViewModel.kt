package com.oliva.samuel.tricountclone.ui.dialogs.tricount.addExpense

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliva.samuel.tricountclone.core.TricountId
import com.oliva.samuel.tricountclone.domain.GetTricountDetailsUseCase
import com.oliva.samuel.tricountclone.domain.mappers.toUiModel
import com.oliva.samuel.tricountclone.domain.model.ExpenseModel
import com.oliva.samuel.tricountclone.domain.model.TricountModel
import com.oliva.samuel.tricountclone.ui.model.ExpenseShareUiModel
import com.oliva.samuel.tricountclone.ui.model.ExpenseUiModel
import com.oliva.samuel.tricountclone.ui.model.ParticipantUiModel
import com.oliva.samuel.tricountclone.ui.model.TricountUiModel
import com.oliva.samuel.tricountclone.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val getTricountDetailsUseCase: GetTricountDetailsUseCase
) : ViewModel() {

    private val _expenseModel = mutableStateOf(ExpenseModel.default().toUiModel())
    val expenseModel: State<ExpenseUiModel>
        get() = _expenseModel

    private val _expenseSharesList = mutableStateListOf<ExpenseShareUiModel>()
    val expenseSharesList: List<ExpenseShareUiModel>
        get() = _expenseSharesList

    private val _tricountInfo = mutableStateOf(TricountModel.default().toUiModel())
    val tricountInfo: State<TricountUiModel>
        get() = _tricountInfo

    private val _participantsList = mutableStateListOf<ParticipantUiModel>()
    val participantsList: List<ParticipantUiModel>
        get() = _participantsList


    fun loadTricount(tricountId: TricountId) {
        viewModelScope.launch {
            getTricountDetailsUseCase(tricountId)
                .map { Resource.Success(it) }
                .catch {}
                .collect { result ->
                    _tricountInfo.value = result.data.tricount
                    _participantsList.clear()
                    _participantsList.addAll(result.data.participants.values)
                    if (_participantsList.none { it.id == _expenseModel.value.paidBy }) {
                        _expenseModel.value =
                            _expenseModel.value.copy(paidBy = participantsList.first().id)
                    }
                }
        }
    }

    fun onExpenseModelChanged(expense: ExpenseUiModel) {
        _expenseModel.value = expense

        updateExpenseShares()
    }

    fun onAddParticipantExpenseShare(participant: ParticipantUiModel) {
        _expenseSharesList.add(
            ExpenseShareUiModel(
                participantId = participant.id,
                expenseId = expenseModel.value.id,
                amountOwed = 0.0
            )
        )

        updateExpenseShares()
    }

    fun onRemoveParticipantExpenseShare(participant: ParticipantUiModel) {
        _expenseSharesList.removeIf { it.participantId == participant.id }

        updateExpenseShares()
    }

    private fun updateExpenseShares() {
        if (_expenseSharesList.isEmpty()) return

        val shareAmount = expenseModel.value.amount / _expenseSharesList.size
        _expenseSharesList.forEachIndexed { index, expenseShare ->
            _expenseSharesList[index] = expenseShare.copy(amountOwed = shareAmount)
        }
    }

    fun onSubmitClick(
        onSubmitted: (ExpenseUiModel, List<ExpenseShareUiModel>) -> Unit
    ) {
        onSubmitted(expenseModel.value, expenseSharesList)
    }
}
