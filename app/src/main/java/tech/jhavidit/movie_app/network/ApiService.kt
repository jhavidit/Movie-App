package tech.jhavidit.movie_app.network

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import tech.jhavidit.movie_app.model.PopularMovieModel
import tech.jhavidit.movie_app.utilities.API_KEY
import tech.jhavidit.movie_app.utilities.Resource

interface ApiService {

    @GET("trending/movies/week")
    suspend fun loadTrendingMovies(
        @Query("api_key") apiKey: String = API_KEY
    ): PopularMovieModel

    @GET("trending/tv/week")
    suspend fun loadTrendingTvShows(
        @Query("api_key") apiKey: String = API_KEY
    ): PopularMovieModel


}