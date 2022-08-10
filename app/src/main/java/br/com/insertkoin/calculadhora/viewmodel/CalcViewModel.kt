package br.com.insertkoin.calculadhora.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalcViewModel : ViewModel(){

    companion object {
        const val ZERO_HOUR = "00:00"
    }

    private val _exitTime = MutableLiveData<String>()
    val exitTime: LiveData<String>
        get() = _exitTime

    init {
        _exitTime.value = ZERO_HOUR
    }
    fun startCalcExit() {
        Log.i("ViewModel", "CalcExit")
    }
}