package com.sbaygildin.sequeniafilmexplorer.ui.moviedetails


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sbaygildin.sequeniafilmexplorer.model.Film

class MovieDetailsViewModel : ViewModel() {

    private val _film = MutableLiveData<Film>()
    val film: LiveData<Film> get() = _film

    fun setFilm(film: Film) {
        _film.value = film
    }
}
