package com.oliva.samuel.tricountclone.ui.dialogs.tricount.addExpense

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliva.samuel.tricountclone.domain.GetTricountWithParticipantsAndExpensesUseCase
import com.oliva.samuel.tricountclone.domain.model.ExpenseModel
import com.oliva.samuel.tricountclone.domain.model.ExpenseShareModel
import com.oliva.samuel.tricountclone.domain.model.ParticipantModel
import com.oliva.samuel.tricountclone.domain.model.TricountModel
import com.oliva.samuel.tricountclone.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val getTricountWithParticipantsAndExpensesUseCase: GetTricountWithParticipantsAndExpensesUseCase
) : ViewModel() {
    private val _expenseModel = mutableStateOf(ExpenseModel.default())
    val expenseModel: State<ExpenseModel>
        get() = _expenseModel

    private val _expenseSharesList = mutableStateListOf<ExpenseShareModel>()
    val expenseSharesList: List<ExpenseShareModel>
        get() = _expenseSharesList

    private val _tricountInfo = mutableStateOf(TricountModel.default())
    val tricountInfo: State<TricountModel>
        get() = _tricountInfo

    private val _participantsList = mutableStateListOf<ParticipantModel>()
    val participantsList: List<ParticipantModel>
        get() = _participantsList


    fun loadTricount(tricountId: UUID) {
        viewModelScope.launch {
            getTricountWithParticipantsAndExpensesUseCase(tricountId)
                .map { Resource.Success(it) }
                .catch {}
                .collect { result ->
                    _tricountInfo.value = result.data.tricount
                    _participantsList.clear()
                    _participantsList.addAll(result.data.participants)
                    if (_participantsList.none { it.id == _expenseModel.value.paidBy }) {
                        _expenseModel.value =
                            _expenseModel.value.copy(paidBy = participantsList.first().id)
                    }
                }
        }
    }

    fun onExpenseModelChanged(expenseModel: ExpenseModel) {
        _expenseModel.value = expenseModel

        updateExpenseShares()
    }

    fun onAddParticipantExpenseShare(participantModel: ParticipantModel) {
        _expenseSharesList.add(
            ExpenseShareModel(
                participantId = participantModel.id,
                expenseId = expenseModel.value.id,
                amountOwed = 0.0
            )
        )

        updateExpenseShares()
    }

    fun onRemoveParticipantExpenseShare(participantModel: ParticipantModel) {
        _expenseSharesList.removeIf { it.participantId == participantModel.id }

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
        onSubmitted: (ExpenseModel, List<ExpenseShareModel>) -> Unit
    ) {
        onSubmitted(expenseModel.value, expenseSharesList)
    }
}
