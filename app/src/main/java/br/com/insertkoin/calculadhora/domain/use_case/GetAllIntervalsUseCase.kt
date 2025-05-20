package br.com.insertkoin.calculadhora.domain.use_case

import br.com.insertkoin.calculadhora.domain.repository.IntervalRepository

class GetAllIntervalsUseCase(
    private val intervalRepository: IntervalRepository
) {
    operator fun invoke() = intervalRepository.getAll()
}