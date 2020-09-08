package com.ervin.pokedex.ui.home

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.ervin.pokedex.core.domain.usecase.home.HomeUseCase
import org.koin.java.KoinJavaComponent.get

class HomeFirstLaunchService : Service() {

    private val homeInteractor: HomeUseCase by lazy {
        get(HomeUseCase::class.java)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        homeInteractor.getAllRemotePokemon()
        stopSelf()
        stopForeground(true)
        return super.onStartCommand(intent, flags, startId)
    }
}