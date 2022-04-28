package tech.jhavidit.movie_app.repository

import tech.jhavidit.movie_app.network.ApiService
import tech.jhavidit.movie_app.network.RetrofitBuilder
import tech.jhavidit.movie_app.utilities.API_KEY

class MovieApiRepository(private val apiService: ApiService) {

    suspend fun loadTrendingValues() = apiService.loadTrendingMovies()

}