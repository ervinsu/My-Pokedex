package com.ervin.pokedex.di

import com.ervin.feature_detail.ui.DetailActivity
import com.ervin.feature_detail.ui.DetailViewModel
import com.ervin.pokedex.core.data.repository.DetailRepository
import com.ervin.pokedex.core.data.source.remote.network.detail.DetailApiService
import com.ervin.pokedex.core.domain.repository.DetailRepositoryContract
import com.ervin.pokedex.core.domain.usecase.detail.DetailInteractor
import com.ervin.pokedex.core.domain.usecase.detail.DetailUseCase
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val detailApiModule = module {
    single<DetailApiService> {
        val retrofit: Retrofit = get(named("poke"))
        retrofit.create(DetailApiService::class.java)
    }

}

val detailRepositoryModule = module {
    single<DetailRepositoryContract> {
        DetailRepository(get())
    }
}

val detailActivityModule = module {
    scope<DetailActivity> {

    }
    factory<DetailUseCase> {
        DetailInteractor(get())
    }
    viewModel { DetailViewModel(get()) }
}

val detailModule = listOf(
    detailApiModule,
    detailRepositoryModule,
    detailActivityModule,
)
