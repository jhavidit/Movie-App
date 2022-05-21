package tech.jhavidit.movie_app.repository

import tech.jhavidit.movie_app.model.PopularMovieModel
import tech.jhavidit.movie_app.network.ApiService
import javax.inject.Inject

class MovieApiRepositoryImplementation @Inject constructor(private val apiService: ApiService) :
    MovieApiRepository {
    override suspend fun loadTrendingMovies(): PopularMovieModel = apiService.loadTrendingMovies()

    override suspend fun loadTrendingTvShows(): PopularMovieModel = apiService.loadTrendingTvShows()

}