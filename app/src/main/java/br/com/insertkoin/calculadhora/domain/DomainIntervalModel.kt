package br.com.insertkoin.calculadhora.domain

data class DomainIntervalModel(
    val id: Long,
    var start: String,
    var breakStart: String,
    var breakEnd: String,
    var end: String
)
