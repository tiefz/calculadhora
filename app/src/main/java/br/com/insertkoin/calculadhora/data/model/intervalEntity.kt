package br.com.insertkoin.calculadhora.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "intervals")
data class IntervalEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val start: String,
    val breakStart: String,
    val breakEnd: String,
    val end: String
)