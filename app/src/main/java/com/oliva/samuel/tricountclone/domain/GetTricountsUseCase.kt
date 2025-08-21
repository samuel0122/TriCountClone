package com.oliva.samuel.tricountclone.domain

import com.oliva.samuel.tricountclone.data.TricountRepository
import com.oliva.samuel.tricountclone.domain.mappers.toUiModel
import com.oliva.samuel.tricountclone.ui.model.TricountUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTricountsUseCase @Inject constructor(
    private val tricountRepository: TricountRepository
) {
    operator fun invoke(): Flow<List<TricountUiModel>> = tricountRepository
        .getTricounts()
        .map { tricountModels ->
            tricountModels.map { it.toUiModel() }
        }
}
