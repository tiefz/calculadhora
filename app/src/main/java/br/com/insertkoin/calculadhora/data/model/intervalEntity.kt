package br.com.insertkoin.calculadhora.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "intervals")
data class IntervalEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val start: String,
    val breakStart: String,
    val breakEnd: String,
    val end: String
)