package com.oliva.samuel.tricountclone.ui.screens.tricountDetailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliva.samuel.tricountclone.domain.GetTricountWithParticipantsAndExpensesUseCase
import com.oliva.samuel.tricountclone.domain.model.TricountWithParticipantsAndExpensesModel
import com.oliva.samuel.tricountclone.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class TricountDetailViewModel @Inject constructor(
    private val getTricountWithParticipantsAndExpensesUseCase: GetTricountWithParticipantsAndExpensesUseCase
) : ViewModel() {
    private val _tricount =
        MutableStateFlow<Resource<TricountWithParticipantsAndExpensesModel>>(Resource.Loading)
    val tricount: StateFlow<Resource<TricountWithParticipantsAndExpensesModel>> = _tricount

    fun loadTricount(tricountId: UUID) {
        viewModelScope.launch {
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
    }
}
