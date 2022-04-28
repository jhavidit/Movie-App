package tech.jhavidit.movie_app.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import tech.jhavidit.movie_app.databinding.FragmentHomeFragmantBinding
import tech.jhavidit.movie_app.network.RetrofitBuilder
import tech.jhavidit.movie_app.repository.MovieApiRepository
import tech.jhavidit.movie_app.utilities.Status
import tech.jhavidit.movie_app.utilities.ViewModelFactory


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeFragmantBinding
    private lateinit var viewModel: HomeFragmentViewModel
    private lateinit var movieListAdapter: MovieListAdapter
    private lateinit var tvShowListAdapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeFragmantBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(MovieApiRepository(RetrofitBuilder().apiService))
        ).get(HomeFragmentViewModel::class.java)
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadMovie()
        loadTvShow()
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