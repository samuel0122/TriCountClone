package com.oliva.samuel.tricountclone.domain

import com.oliva.samuel.tricountclone.data.TricountRepository
import com.oliva.samuel.tricountclone.domain.model.TricountModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTricountUseCase @Inject constructor(
    private val tricountRepository: TricountRepository
) {
    operator fun invoke(): Flow<List<TricountModel>> = tricountRepository.getTricounts()
}
