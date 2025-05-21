package br.com.insertkoin.calculadhora.domain.use_case

import br.com.insertkoin.calculadhora.domain.DomainIntervalModel
import br.com.insertkoin.calculadhora.domain.repository.IntervalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllIntervalsUseCase(
    private val intervalRepository: IntervalRepository
) {
    operator fun invoke(): Flow<List<DomainIntervalModel>> =
        intervalRepository.getAll().map {
            it.map { intervalEntity ->
                DomainIntervalModel(
                    id = intervalEntity.id,
                    start = intervalEntity.start,
                    breakStart = intervalEntity.breakStart,
                    breakEnd = intervalEntity.breakEnd,
                    end = intervalEntity.end,
                )
            }
        }
}