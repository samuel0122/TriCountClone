package com.oliva.samuel.tricountclone.ui.screens.tricountDetailScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliva.samuel.tricountclone.core.ExpenseId
import com.oliva.samuel.tricountclone.core.TricountId
import com.oliva.samuel.tricountclone.domain.AddExpenseUseCase
import com.oliva.samuel.tricountclone.domain.GetTricountDetailsUseCase
import com.oliva.samuel.tricountclone.ui.model.ExpenseShareUiModel
import com.oliva.samuel.tricountclone.ui.model.ExpenseUiModel
import com.oliva.samuel.tricountclone.ui.model.TricountDetailUiModel
import com.oliva.samuel.tricountclone.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TricountDetailViewModel @Inject constructor(
    private val getTricountDetailsUseCase: GetTricountDetailsUseCase,
    private val addExpenseUseCase: AddExpenseUseCase
) : ViewModel() {

    private val _tricount = MutableStateFlow<Resource<TricountDetailUiModel>>(Resource.Loading)
    val tricount: StateFlow<Resource<TricountDetailUiModel>> = _tricount

    private val _showAddExpenseDialog = MutableLiveData<Boolean>()
    val showAddExpenseDialog: LiveData<Boolean> = _showAddExpenseDialog

    private var _tricountId: TricountId? = null

    fun loadTricount(tricountId: TricountId) {
        _tricountId = tricountId
        viewModelScope.launch {
            getTricountDetailsUseCase(tricountId)
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
        expenseModel: ExpenseUiModel,
        expenseShares: List<ExpenseShareUiModel>
    ) {
        _showAddExpenseDialog.value = false

        _tricountId?.let { tricountId ->
            viewModelScope.launch {
                addExpenseUseCase(
                    expenseModel.copy(
                        id = ExpenseId.randomUUID(),
                        createdAt = Date.from(Instant.now()),
                        tricountId = tricountId
                    )
                )
            }
        }
    }
}
