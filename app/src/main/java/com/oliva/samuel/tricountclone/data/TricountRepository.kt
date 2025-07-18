package com.oliva.samuel.tricountclone.data

import com.oliva.samuel.tricountclone.data.database.dao.TricountDao
import com.oliva.samuel.tricountclone.domain.mappers.toDatabase
import com.oliva.samuel.tricountclone.domain.mappers.toDomain
import com.oliva.samuel.tricountclone.domain.model.TricountModel
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class TricountRepository @Inject constructor(
    private val tricountDao: TricountDao
) {
    fun getTricounts() = tricountDao
        .getAll()
        .map { tricountEntities ->
            tricountEntities.map { it.toDomain() }
        }

    fun getTricountWithParticipantsAndExpenses(id: UUID) = tricountDao
        .getTricountWithParticipantsAndExpenses(id)
        .map {
            it.toDomain()
        }

    suspend fun insertTricount(tricount: TricountModel) {
        tricountDao.insert(tricount.toDatabase())
    }

    suspend fun updateTricount(tricount: TricountModel) {
        tricountDao.update(tricount.toDatabase())
    }

    suspend fun deleteTricount(tricount: TricountModel) {
        tricountDao.delete(tricount.toDatabase())
    }
}
