package tech.jhavidit.movie_app.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Retrofit
import tech.jhavidit.movie_app.databinding.FragmentHomeFragmantBinding
import tech.jhavidit.movie_app.model.PopularMovieModel
import tech.jhavidit.movie_app.network.ApiService
import tech.jhavidit.movie_app.network.ApplicationModule
import tech.jhavidit.movie_app.network.ApplicationModule_ProvideApiHelperFactory.provideApiHelper
import tech.jhavidit.movie_app.network.ApplicationModule_ProvideApiServiceFactory.provideApiService
import tech.jhavidit.movie_app.repository.MovieApiRepositoryImplementation
import tech.jhavidit.movie_app.utilities.Status
import tech.jhavidit.movie_app.utilities.ViewModelFactory
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeFragmantBinding
    private val viewModel: HomeFragmentViewModel by viewModels()
    private lateinit var movieListAdapter: MovieListAdapter
    private lateinit var tvShowListAdapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeFragmantBinding.inflate(inflater, container, false)

        movieListAdapter = MovieListAdapter()
        tvShowListAdapter = MovieListAdapter()
        binding.rvPopularMovie.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = movieListAdapter
        }
        binding.rvPopularTv.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = tvShowListAdapter
        }
        loadMovie()
        loadTvShow()
        return binding.root
    }


    private fun loadMovie() {
        viewModel.getPopularMovie().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressCircular.visibility = GONE
                    binding.popularMovie.visibility = VISIBLE
                    binding.rvPopularMovie.visibility = VISIBLE
                    it.data?.let { movieItem ->
                        movieListAdapter.setMovieData(movieItem)
                    }
                }
                Status.LOADING -> {
                    binding.progressCircular.visibility = VISIBLE
                    binding.popularMovie.visibility = GONE
                    binding.rvPopularMovie.visibility = GONE
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    binding.progressCircular.visibility = GONE
                    binding.popularMovie.visibility = GONE
                    binding.rvPopularMovie.visibility = GONE
                }
            }
        })
    }

    private fun loadTvShow() {
        viewModel.getPopularTvShow().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressCircular.visibility = GONE
                    binding.popularTv.visibility = VISIBLE
                    binding.rvPopularTv.visibility = VISIBLE
                    it.data?.let { movieItem ->
                        tvShowListAdapter.setMovieData(movieItem)
                    }
                }
                Status.LOADING -> {
                    binding.progressCircular.visibility = VISIBLE
                    binding.popularTv.visibility = GONE
                    binding.rvPopularTv.visibility = GONE
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    binding.progressCircular.visibility = GONE
                    binding.popularTv.visibility = GONE
                    binding.rvPopularTv.visibility = GONE
                }
            }
        })
    }


}