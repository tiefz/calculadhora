package br.com.insertkoin.calculadhora.domain.use_case

import br.com.insertkoin.calculadhora.data.model.IntervalEntity
import br.com.insertkoin.calculadhora.domain.repository.IntervalRepository

class DeleteIntervalUseCase(
    private val repository: IntervalRepository
) {
    suspend operator fun invoke(interval: IntervalEntity) = repository.deleteInterval(interval)
}