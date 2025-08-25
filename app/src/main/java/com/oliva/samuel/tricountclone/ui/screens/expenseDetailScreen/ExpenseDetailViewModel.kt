package com.oliva.samuel.tricountclone.ui.screens.expenseDetailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliva.samuel.tricountclone.core.ExpenseId
import com.oliva.samuel.tricountclone.domain.GetExpenseDetailsUseCase
import com.oliva.samuel.tricountclone.ui.model.ExpenseDetailUiModel
import com.oliva.samuel.tricountclone.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseDetailViewModel @Inject constructor(
    private val getExpenseDetailsUseCase: GetExpenseDetailsUseCase
) : ViewModel() {

    private val _expenseDetail = MutableStateFlow<Resource<ExpenseDetailUiModel>>(Resource.Loading)
    val expenseDetail: StateFlow<Resource<ExpenseDetailUiModel>> = _expenseDetail

    private var _expenseId: ExpenseId? = null

    fun loadExpense(expenseId: ExpenseId) {
        _expenseId = expenseId
        viewModelScope.launch {
            getExpenseDetailsUseCase(expenseId)
                .map { Resource.Success(it) }
                .catch { e ->
                    _expenseDetail.value = Resource.Error(e, e.message)
                }
                .collect { result ->
                    _expenseDetail.value = result
                }
        }
    }

}
