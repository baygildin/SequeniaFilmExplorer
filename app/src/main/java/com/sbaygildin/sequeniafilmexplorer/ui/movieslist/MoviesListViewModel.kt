package com.sbaygildin.sequeniafilmexplorer.ui.movieslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbaygildin.sequeniafilmexplorer.model.Film
import com.sbaygildin.sequeniafilmexplorer.network.ApiService
import kotlinx.coroutines.launch

class MoviesListViewModel(private val apiService: ApiService) : ViewModel() {

    private val _films = MutableLiveData<List<Film>>()
    val films: LiveData<List<Film>> get() = _films

    private val _genres = MutableLiveData<List<String>>()
    val genres: LiveData<List<String>> get() = _genres

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var allFilms: List<Film> = emptyList()
    private var selectedGenre: String? = null

    fun fetchFilms() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiService.api.getFilms()
                allFilms = response.films.sortedBy { it.localized_name }
                _films.value = allFilms
                _genres.value = response.films.flatMap { it.genres }.distinct()
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Ошибка подключения к сети"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun filterByGenre(genre: String?) {
        selectedGenre = genre
        _films.value = if (genre.isNullOrEmpty()) {
            allFilms
        } else {
            allFilms.filter { it.genres.contains(genre) }
        }
    }

    fun getSelectedGenre(): String? = selectedGenre
}
