package br.com.insertkoin.calculadhora

import android.app.Application
import br.com.insertkoin.calculadhora.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class CalculadhoraApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CalculadhoraApp)
            modules(appModule)
        }
    }
}