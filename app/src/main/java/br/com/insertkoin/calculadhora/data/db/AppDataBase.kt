package br.com.insertkoin.calculadhora.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.insertkoin.calculadhora.data.dao.IntervalDao
import br.com.insertkoin.calculadhora.data.model.IntervalEntity

@Database(entities = [IntervalEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun intervalDao(): IntervalDao
}