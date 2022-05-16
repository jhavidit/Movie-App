package tech.jhavidit.movie_app.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.*
import tech.jhavidit.movie_app.model.Result
import tech.jhavidit.movie_app.repository.MovieApiRepository
import tech.jhavidit.movie_app.utilities.Resource
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val movieApiRepository: MovieApiRepository,
) : ViewModel() {

    private val popularMovie = MutableLiveData<Resource<List<Result?>>>()
    private val popularTvShow = MutableLiveData<Resource<List<Result?>>>()

    init {
        fetchPopular()
    }

    private fun fetchPopular() {
        viewModelScope.launch {
            popularMovie.postValue(Resource.loading(null))
            popularTvShow.postValue(Resource.loading(null))
            supervisorScope {
                val popularMovieData =
                    async(Dispatchers.IO) { movieApiRepository.loadTrendingMovies().results }
                val popularTvShowData =
                    async(Dispatchers.IO) { movieApiRepository.loadTrendingTvShows().results }
                try {
                    popularMovie.postValue(Resource.success(popularMovieData.await()))
                    Log.d("apiResult", popularMovieData.await().toString())
                } catch (e: Exception) {
                    popularMovie.postValue(Resource.error(e.toString(), null))
                }
                try {
                    popularTvShow.postValue(Resource.success(popularTvShowData.await()))
                    Log.d("apiResult", popularTvShowData.await().toString())
                } catch (e: Exception) {
                    popularTvShow.postValue(Resource.error(e.toString(), null))
                }
            }
        }
    }

    fun getPopularMovie(): LiveData<Resource<List<Result?>>> = popularMovie

    fun getPopularTvShow(): LiveData<Resource<List<Result?>>> = popularTvShow

}