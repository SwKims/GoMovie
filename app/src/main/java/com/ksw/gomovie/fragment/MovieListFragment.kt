package com.ksw.gomovie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ksw.gomovie.adapter.MoviePageListAdapter
import com.ksw.gomovie.databinding.MovieListFragmentBinding
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkModule
import com.ksw.gomovie.network.NetworkState
import com.ksw.gomovie.repository.movie.MoviePagedListRepository
import com.ksw.gomovie.viewmodel.MovieListViewModel

/**
 * Created by KSW on 2021-02-23
 */

class MovieListFragment(private val type: String) : Fragment() {

    /*private var _binding: MovieListFragmentBinding? = null
    private val binding get() = _binding!!*/
    private lateinit var binding: MovieListFragmentBinding

    //    private lateinit var rootView : View
    private lateinit var movieAdapter: MoviePageListAdapter
    private lateinit var movieListViewModel: MovieListViewModel
    lateinit var moviePagedListRepository: MoviePagedListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieAdapter = MoviePageListAdapter(
            activity!!.applicationContext
        )
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MovieListFragmentBinding.inflate(inflater, container, false)

        val apiService: MovieServiceApi = NetworkModule.getClient()
        moviePagedListRepository = MoviePagedListRepository(apiService)
        movieListViewModel = movieListViewModel(type)
        populatingViews()

        return binding.root
    }



    private fun movieListViewModel(type: String): MovieListViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MovieListViewModel(moviePagedListRepository, type) as T
            }
        })[MovieListViewModel::class.java]
    }


    private fun populatingViews() {
        movieListViewModel.moviePagedList.observe(viewLifecycleOwner) {
            movieAdapter.submitList(it)
            val layoutManager = GridLayoutManager(activity, 3)
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val viewType = movieAdapter.getItemViewType(position)
                    return if (viewType == movieAdapter.POPULAR_MOVIE_VIEW_TYPE) 1
                    else 3
                }
            }
            binding.rvMovieList.layoutManager = layoutManager
            binding.rvMovieList.setHasFixedSize(true)
            binding.rvMovieList.adapter = movieAdapter
        }

        movieListViewModel.networkState.observe(viewLifecycleOwner) {
            binding.pbMain.visibility =
                if (movieListViewModel.movieListEmpty() && it == NetworkState.LOADING) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            if (!movieListViewModel.movieListEmpty()) {
                movieAdapter.setNetworkState(it)
            }
        }
    }



}
