package com.ervin.pokedex.core.domain.backgroundservice

import android.annotation.TargetApi
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ervin.pokedex.core.R
import com.ervin.pokedex.core.data.source.local.LocalDataSource
import com.ervin.pokedex.core.data.source.local.entity.foreignkey.PokemonElementEntity
import com.ervin.pokedex.core.data.source.remote.RemoteDataSource
import com.ervin.pokedex.core.data.source.remote.network.ApiResponse
import com.ervin.pokedex.core.util.mappingPokemonApiResponseToLocalResponse
import com.ervin.pokedex.core.util.mappingPokemonResponseToPokemonElementEntity
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import org.koin.java.KoinJavaComponent.get

@ExperimentalCoroutinesApi
class HomeFirstLaunchService : Service() {

    private val localDataSource: LocalDataSource by lazy {
        get(LocalDataSource::class.java)
    }

    private val remoteDataSource: RemoteDataSource by lazy {
        get(RemoteDataSource::class.java)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // create notification and register the channel with the app
        val build = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this@HomeFirstLaunchService.prepareChannel(
                "ONGOING_CHANNEL_ID",
                NotificationManagerCompat.IMPORTANCE_HIGH
            )
            NotificationCompat.Builder(this@HomeFirstLaunchService, "ONGOING_CHANNEL_ID")
        } else {
            NotificationCompat.Builder(this@HomeFirstLaunchService, "ONGOING_CHANNEL_ID")
        }
        val builder = build.apply {
            setOngoing(true)
            setSmallIcon(R.drawable.ic_logo)
            setContentTitle("Data Loading")
            setProgress(0, 0, true)
        }
        val notification = builder.build()
        startForeground(123, notification)

        getAllRemotePokemon()

        return super.onStartCommand(intent, flags, startId)
    }

    private fun getAllRemotePokemon() {
        val parentCoroutineContext = SupervisorJob() + Dispatchers.Main
        val childCoroutineContext = Job() + Dispatchers.IO

        CoroutineScope(parentCoroutineContext).launch {
            /**
             * fetch up to 151 pokemon (Pokemon at Kanto Region)
             */
            var count = 0
            val maxOffset = 151
            val currLimitPerFetch = 10
            val lastLimitFetch = maxOffset % currLimitPerFetch

            val fetchPokemonChannel = produce(CoroutineName("channelPokemon")) {
                while (count + currLimitPerFetch <= maxOffset) {
                    send(LimitOffsetPokemon(count, currLimitPerFetch))
                    count += currLimitPerFetch
                }
                //add to channel for last limit
                if (lastLimitFetch != 0)
                    send(LimitOffsetPokemon(count, lastLimitFetch))
            }
            try {
                /**
                 * create 10 channel to run parallel
                 */
                withContext(childCoroutineContext) {
                    repeat(10) {
                        launch(CoroutineName("launcher-$it")) {
                            getQueueRemotePokemon(fetchPokemonChannel)
                        }
                    }
                }
            } catch (e: Exception) {
                stopSelf()
                stopForeground(true)
                parentCoroutineContext.cancel(CancellationException("failed to fetch remote pokemon ${e.localizedMessage}"))
            }

            stopSelf()
            stopForeground(true)
        }
    }

    data class LimitOffsetPokemon(val offset: Int, val limit: Int)

    private suspend fun getQueueRemotePokemon(receiveQueuePokemons: ReceiveChannel<LimitOffsetPokemon>) {
        /**
         * we can't convert the [receiveQueuePokemons] to Flow by [receiveAsFlow] since flow won't blocking the current thread
         * thus other function below where this function called will be executed
         * So, it's better to still receive it as channel, to wait all of data is added to db then this service can stop
         */
        for (queue in receiveQueuePokemons) {
            remoteDataSource.getAllListPokemon(queue.offset, queue.limit).collect {
                when (it) {
                    is ApiResponse.Success -> {
                        /**
                         * add pokemon and composite key to db
                         */
                        val listPokemonElementEntity = mutableListOf<PokemonElementEntity>()
                        val convertedApiPokemonResult = it.data
                            .map { pokemonResponse ->
                                /**
                                 * split the types
                                 */
                                listPokemonElementEntity.addAll(
                                    mappingPokemonResponseToPokemonElementEntity(pokemonResponse)
                                )
                                /**
                                 * return it as List of PokemonEntity
                                 */
                                mappingPokemonApiResponseToLocalResponse(pokemonResponse)
                            }

                        localDataSource.insertAllPokemon(convertedApiPokemonResult)
                        localDataSource.insertAllPokemonElementFK(
                            listPokemonElementEntity
                        )

                    }
                    is ApiResponse.Empty -> {
                        //do nothing
                    }
                    is ApiResponse.Error -> {
                        throw CancellationException(it.errorMessage)
                    }
                }
            }

        }
    }

    @TargetApi(26)
    private fun Context.prepareChannel(id: String, importance: Int) {
        val appName = "poke"
        val description = "Description"
        val nm = getSystemService(Activity.NOTIFICATION_SERVICE) as NotificationManager

        val nChannel = NotificationChannel(id, appName, importance)
        nChannel.description = description
        nm.createNotificationChannel(nChannel)
    }

}

