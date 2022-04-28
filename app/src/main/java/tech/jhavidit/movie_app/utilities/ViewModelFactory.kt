package tech.jhavidit.movie_app.utilities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tech.jhavidit.movie_app.repository.MovieApiRepository
import tech.jhavidit.movie_app.view.HomeFragmentViewModel

class ViewModelFactory(
    private val movieApiRepository: MovieApiRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeFragmentViewModel::class.java))
            return HomeFragmentViewModel(movieApiRepository) as T
        throw IllegalArgumentException("Unknown class name")
    }
}