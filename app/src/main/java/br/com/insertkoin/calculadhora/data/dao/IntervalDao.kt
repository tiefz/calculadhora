package br.com.insertkoin.calculadhora.data.dao

import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import androidx.room.Dao
import androidx.room.Delete
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