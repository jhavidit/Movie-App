package tech.jhavidit.movie_app.repository

import tech.jhavidit.movie_app.model.PopularMovieModel

interface MovieApiRepository {

    suspend fun loadTrendingMovies(): PopularMovieModel

    suspend fun loadTrendingTvShows(): PopularMovieModel

}