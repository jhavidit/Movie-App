package tech.jhavidit.movie_app.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import tech.jhavidit.movie_app.model.Result
import tech.jhavidit.movie_app.repository.MovieApiRepository
import tech.jhavidit.movie_app.utilities.Resource

class HomeFragmentViewModel(private val movieApiRepository: MovieApiRepository) : ViewModel() {

    private val popularMovie = MutableLiveData<Resource<List<Result?>>>()
    private val popularTvShow = MutableLiveData<Resource<List<Result?>>>()

    init {
        fetchPopular()
    }

    private fun fetchPopular() {
        viewModelScope.launch(Dispatchers.IO) {
            popularMovie.postValue(Resource.loading(null))
            popularTvShow.postValue(Resource.loading(null))
            supervisorScope {
                val popularMovieData =
                    async(Dispatchers.IO) { movieApiRepository.loadTrendingMovies().results }
                val popularTvShowData =
                    async(Dispatchers.IO) { movieApiRepository.loadTrendingTvShows().results }
                try {
                    popularMovie.postValue(Resource.success(popularMovieData.await()))
                } catch (e: Exception) {
                    popularMovie.postValue(Resource.error(e.toString(), null))
                }
                try {
                    popularTvShow.postValue(Resource.success(popularTvShowData.await()))
                } catch (e: Exception) {
                    popularTvShow.postValue(Resource.error(e.toString(), null))
                }
            }
        }
    }

    fun getPopularMovie(): LiveData<Resource<List<Result?>>> = popularMovie

    fun getPopularTvShow(): LiveData<Resource<List<Result?>>> = popularTvShow

}