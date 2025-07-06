package com.oliva.samuel.tricountclone.domain

import com.oliva.samuel.tricountclone.data.TricountRepository
import com.oliva.samuel.tricountclone.domain.model.TricountModel
import javax.inject.Inject

class AddTricountUseCase @Inject constructor(
    private val tricountRepository: TricountRepository
) {
    suspend operator fun invoke(tricountModel: TricountModel) =
        tricountRepository.insertTricount(tricountModel)
}
