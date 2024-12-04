package com.sbaygildin.sequeniafilmexplorer.di

import com.sbaygildin.sequeniafilmexplorer.network.ApiService
import com.sbaygildin.sequeniafilmexplorer.ui.moviedetails.MovieDetailsViewModel
import com.sbaygildin.sequeniafilmexplorer.ui.movieslist.MoviesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    single { ApiService.api }
    viewModel { MoviesListViewModel(get()) }
    viewModel { MovieDetailsViewModel() }
}