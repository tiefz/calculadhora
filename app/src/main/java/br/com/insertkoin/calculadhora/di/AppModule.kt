package br.com.insertkoin.calculadhora.di

import androidx.room.Room
import br.com.insertkoin.calculadhora.data.db.AppDataBase
import br.com.insertkoin.calculadhora.data.repository.IntervalRepositoryImpl
import br.com.insertkoin.calculadhora.domain.use_case.DeleteIntervalUseCase
import br.com.insertkoin.calculadhora.domain.use_case.GetAllIntervalsUseCase
import br.com.insertkoin.calculadhora.domain.use_case.InsertIntervalUseCase
import br.com.insertkoin.calculadhora.domain.use_case.IntervalUseCases
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDataBase::class.java,
            "calculadhora_db"
        ).build()
    }
    single { get<AppDataBase>().intervalDao() }
    single { IntervalRepositoryImpl(get()) }
    single { GetAllIntervalsUseCase(get()) }
    single { InsertIntervalUseCase(get()) }
    single { DeleteIntervalUseCase(get()) }
    single {
        IntervalUseCases(
            getAllIntervalsUseCase = get(),
            insertIntervalUseCase = get(),
            deleteIntervalUseCase = get()
        )
    }
}