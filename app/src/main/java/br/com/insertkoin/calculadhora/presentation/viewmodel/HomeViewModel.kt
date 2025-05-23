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

    fun calculateTotalTime(intervals: List<DomainIntervalModel>): String {
        val totalTime = intervals.sumOf { interval ->
            val (start, breakStart, breakEnd, end) = listOf(
                interval.start,
                interval.breakStart,
                interval.breakEnd,
                interval.end
            )
            if (start != null && end != null && breakStart != null && breakEnd != null) {
                timeStringToMillis(end) - (timeStringToMillis(breakEnd) - timeStringToMillis(
                    breakStart
                )) - timeStringToMillis(start)
            } else 0L
        }
        return formatTime(totalTime)
    }

    private fun timeStringToMillis(time: String): Long {
        val parts = time.split(":")
        val hours = parts[0].toLong()
        val minutes = parts[1].toLong()
        return (hours * 60 + minutes) * 60_000
    }

    private fun formatTime(totalTime: Long): String {
        val hours = (totalTime / 3600000).toInt()
        val minutes = ((totalTime % 3600000) / 60000).toInt()
        return String.format("%02d:%02d", hours, minutes)
    }
}