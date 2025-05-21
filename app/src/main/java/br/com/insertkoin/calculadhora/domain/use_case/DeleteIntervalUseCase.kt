package br.com.insertkoin.calculadhora.domain.use_case

import br.com.insertkoin.calculadhora.data.model.IntervalEntity
import br.com.insertkoin.calculadhora.domain.DomainIntervalModel
import br.com.insertkoin.calculadhora.domain.repository.IntervalRepository

class DeleteIntervalUseCase(
    private val repository: IntervalRepository
) {
    suspend operator fun invoke(interval: DomainIntervalModel) =
        repository.deleteInterval(
            IntervalEntity(
                id = interval.id,
                start = interval.start,
                breakStart = interval.breakStart,
                breakEnd = interval.breakEnd,
                end = interval.end
            )
        )
}