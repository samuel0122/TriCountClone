package com.oliva.samuel.tricountclone.data

import com.oliva.samuel.tricountclone.core.TricountId
import com.oliva.samuel.tricountclone.data.database.dao.TricountDao
import com.oliva.samuel.tricountclone.domain.mappers.toDatabase
import com.oliva.samuel.tricountclone.domain.mappers.toDomain
import com.oliva.samuel.tricountclone.domain.model.TricountModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TricountRepository @Inject constructor(
    private val tricountDao: TricountDao
) {
    fun getTricounts(): Flow<List<TricountModel>> = tricountDao
        .getAllFlow()
        .map { tricountEntities ->
            tricountEntities.map { it.toDomain() }
        }

    fun getTricount(id: TricountId): Flow<TricountModel> = tricountDao
        .getTricountFlow(id)
        .map { tricountEntity ->
            tricountEntity.toDomain()
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
