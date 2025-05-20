package br.com.insertkoin.calculadhora.data.repository

import br.com.insertkoin.calculadhora.data.dao.IntervalDao
import br.com.insertkoin.calculadhora.data.model.IntervalEntity
import br.com.insertkoin.calculadhora.domain.repository.IntervalRepository
import kotlinx.coroutines.flow.Flow

class IntervalRepositoryImpl(
    private val intervalDao: IntervalDao
) : IntervalRepository {

    override fun getAll(): Flow<List<IntervalEntity>> =
        intervalDao.getAllIntervals()

    override suspend fun insertInterval(interval: IntervalEntity) =
        intervalDao.insertInterval(interval)

    override suspend fun deleteInterval(interval: IntervalEntity) =
        intervalDao.deleteInterval(interval)
}