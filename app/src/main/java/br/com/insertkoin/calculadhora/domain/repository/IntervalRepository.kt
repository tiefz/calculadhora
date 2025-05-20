package br.com.insertkoin.calculadhora.domain.repository

import br.com.insertkoin.calculadhora.data.model.IntervalEntity
import kotlinx.coroutines.flow.Flow

interface IntervalRepository {
    fun getAll(): Flow<List<IntervalEntity>>
    suspend fun insertInterval(interval: IntervalEntity)
    suspend fun deleteInterval(interval: IntervalEntity)
}