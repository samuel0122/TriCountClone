package com.oliva.samuel.tricountclone.domain

import com.oliva.samuel.tricountclone.core.ExpenseId
import com.oliva.samuel.tricountclone.data.ExpenseRepository
import com.oliva.samuel.tricountclone.data.ExpenseShareRepository
import com.oliva.samuel.tricountclone.data.ParticipantRepository
import com.oliva.samuel.tricountclone.domain.mappers.toUiModel
import com.oliva.samuel.tricountclone.ui.model.ExpenseDetailUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetExpenseDetailsUseCase @Inject constructor(
    private val participantRepository: ParticipantRepository,
    private val expenseRepository: ExpenseRepository,
    private val expenseShareRepository: ExpenseShareRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(expenseId: ExpenseId): Flow<ExpenseDetailUiModel> =
        expenseRepository.getExpense(expenseId).flatMapLatest { expense ->
            combine(
                participantRepository.getParticipant(expense.paidBy),
                expenseShareRepository.getExpenseShares(expense.id)
            ) { paidBy, shares ->
                paidBy to shares
            }.flatMapLatest { (paidByParticipant, expenseShares) ->
                participantRepository.getParticipants(expenseShares.map { it.participantId })
                    .map { expenseParticipants ->
                        ExpenseDetailUiModel(
                            expenseUiModel = expense.toUiModel(),
                            paidBy = paidByParticipant.toUiModel(),
                            expenseShares = expenseShares.map { it.toUiModel() },
                            expenseSharesParticipants = expenseParticipants
                                .map { it.toUiModel() }
                                .associateBy { it.id }
                        )
                    }
            }
        }
}
