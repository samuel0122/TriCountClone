package com.oliva.samuel.tricountclone.domain

import com.oliva.samuel.tricountclone.data.TricountRepository
import com.oliva.samuel.tricountclone.domain.model.TricountWithParticipantsAndExpensesModel
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class GetTricountWithParticipantsAndExpensesUseCase @Inject constructor(
    private val tricountRepository: TricountRepository
) {
    operator fun invoke(id: UUID): Flow<TricountWithParticipantsAndExpensesModel> =
        tricountRepository.getTricountWithParticipantsAndExpenses(id)
}
