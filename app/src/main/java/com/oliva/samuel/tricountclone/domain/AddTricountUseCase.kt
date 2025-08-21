package com.oliva.samuel.tricountclone.domain

import com.oliva.samuel.tricountclone.data.TricountRepository
import com.oliva.samuel.tricountclone.domain.mappers.toDomain
import com.oliva.samuel.tricountclone.ui.model.TricountUiModel
import javax.inject.Inject

class AddTricountUseCase @Inject constructor(
    private val tricountRepository: TricountRepository
) {
    suspend operator fun invoke(tricountUiModel: TricountUiModel) =
        tricountRepository.insertTricount(tricountUiModel.toDomain())
}
