package com.sbaygildin.sequeniafilmexplorer.network

import com.sbaygildin.sequeniafilmexplorer.model.FilmResponse
import retrofit2.http.GET

interface ApiService {
    @GET("films.json")
    suspend fun getFilms(): FilmResponse

    companion object {
        val api: ApiService by lazy {
            RetrofitInstance.retrofit.create(ApiService::class.java)
        }
    }
}
