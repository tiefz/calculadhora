package br.com.insertkoin.calculadhora.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.insertkoin.calculadhora.data.model.IntervalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IntervalDao {
    @Query("SELECT * FROM intervals")
    fun getAllIntervals(): Flow<List<IntervalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInterval(interval: IntervalEntity)

    @Delete
    suspend fun deleteInterval(interval: IntervalEntity)
}