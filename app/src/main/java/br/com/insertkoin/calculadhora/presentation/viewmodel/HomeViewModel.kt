package br.com.insertkoin.calculadhora.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.insertkoin.calculadhora.domain.DomainIntervalModel
import br.com.insertkoin.calculadhora.domain.use_case.IntervalUseCases
import kotlinx.coroutines.launch

class HomeViewModel(
    private val useCases: IntervalUseCases
) : ViewModel() {
    fun getAllIntervals() = useCases.getAllIntervalsUseCase()
    fun insertInterval(interval: DomainIntervalModel) = viewModelScope.launch {
        useCases.insertIntervalUseCase(interval)
    }

    fun deleteInterval(interval: DomainIntervalModel) = viewModelScope.launch {
        useCases.deleteIntervalUseCase(interval)
    }
}