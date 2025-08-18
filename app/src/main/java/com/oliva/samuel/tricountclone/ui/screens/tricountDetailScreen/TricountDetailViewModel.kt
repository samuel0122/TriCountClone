package com.oliva.samuel.tricountclone.ui.screens.tricountDetailScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliva.samuel.tricountclone.domain.AddExpenseUseCase
import com.oliva.samuel.tricountclone.domain.GetTricountWithParticipantsAndExpensesUseCase
import com.oliva.samuel.tricountclone.domain.model.ExpenseModel
import com.oliva.samuel.tricountclone.domain.model.ExpenseShareModel
import com.oliva.samuel.tricountclone.domain.model.TricountWithParticipantsAndExpensesModel
import com.oliva.samuel.tricountclone.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class TricountDetailViewModel @Inject constructor(
    private val getTricountWithParticipantsAndExpensesUseCase: GetTricountWithParticipantsAndExpensesUseCase,
    private val addExpenseUseCase: AddExpenseUseCase
) : ViewModel() {
    private val _tricount =
        MutableStateFlow<Resource<TricountWithParticipantsAndExpensesModel>>(Resource.Loading)
    val tricount: StateFlow<Resource<TricountWithParticipantsAndExpensesModel>> = _tricount

    private val _showAddExpenseDialog = MutableLiveData<Boolean>()
    val showAddExpenseDialog: LiveData<Boolean> = _showAddExpenseDialog

    private var _tricountId: UUID? = null

    fun loadTricount(tricountId: UUID) {
        _tricountId = tricountId
        viewModelScope.launch {
            getTricountWithParticipantsAndExpensesUseCase(tricountId)
                .map { Resource.Success(it) }
                .catch { e ->
                    _tricount.value = Resource.Error(e, e.message)
                }
                .collect { result ->
                    _tricount.value = result
                }
        }
    }

    fun onShowAddExpenseDialogClick() {
        _showAddExpenseDialog.value = true
    }

    fun onDismissAddExpenseDialog() {
        _showAddExpenseDialog.value = false
    }

    fun onExpenseAdded(
        expenseModel: ExpenseModel,
        expenseShares: List<ExpenseShareModel>
    ) {
        _showAddExpenseDialog.value = false

        _tricountId?.let { tricountId ->
            viewModelScope.launch {
                addExpenseUseCase(
                    expenseModel.copy(
                        id = UUID.randomUUID(),
                        createdAt = Date.from(Instant.now()),
                        tricountId = tricountId
                    )
                )
            }
        }
    }
}
