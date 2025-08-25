package com.oliva.samuel.tricountclone.domain

import com.oliva.samuel.tricountclone.core.TricountId
import com.oliva.samuel.tricountclone.data.ExpenseRepository
import com.oliva.samuel.tricountclone.data.ParticipantRepository
import com.oliva.samuel.tricountclone.data.TricountRepository
import com.oliva.samuel.tricountclone.data.UserRepository
import com.oliva.samuel.tricountclone.domain.mappers.toUiModel
import com.oliva.samuel.tricountclone.ui.model.TricountDetailUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class GetTricountDetailsUseCase @Inject constructor(
    private val tricountRepository: TricountRepository,
    private val participantRepository: ParticipantRepository,
    private val expenseRepository: ExpenseRepository,
    private val userRepository: UserRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(tricountId: TricountId): Flow<TricountDetailUiModel> {
        val tricountInfo = tricountRepository.getTricount(tricountId)

        return tricountInfo.flatMapLatest { tricount ->
            val userInfo = userRepository.getUserFlow(tricount.createdBy)
            val tricountParticipants = participantRepository.getParticipantsFromTricount(tricountId)
            val tricountExpenses = expenseRepository.getExpensesFromTricount(tricountId)

            combine(
                userInfo,
                tricountParticipants,
                tricountExpenses
            ) { user, participants, expenses ->
                TricountDetailUiModel(
                    tricount = tricount.toUiModel(),
                    createdBy = user.toUiModel(),
                    participants = participants.map { it.toUiModel() }.associateBy { it.id },
                    expenses = expenses.map { it.toUiModel() }
                )
            }
        }
    }
}
