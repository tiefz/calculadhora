package br.com.insertkoin.calculadhora.domain.use_case

import br.com.insertkoin.calculadhora.data.model.IntervalEntity
import br.com.insertkoin.calculadhora.domain.repository.IntervalRepository

class InsertIntervalUseCase(
    private val repository: IntervalRepository
) {
    suspend operator fun invoke(interval: IntervalEntity) = repository.insertInterval(interval)
}