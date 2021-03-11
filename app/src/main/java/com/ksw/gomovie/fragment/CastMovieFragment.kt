package com.ksw.gomovie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ksw.gomovie.adapter.MovieAdapter
import com.ksw.gomovie.databinding.CastMovieFragmentBinding
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkModule
import com.ksw.gomovie.repository.cast.CastDetailRepository
import com.ksw.gomovie.viewmodel.CastDetailViewModel

/**
 * Created by KSW on 2021-03-09
 */

class CastMovieFragment(private val castId: Int): Fragment() {

    private lateinit var binding: CastMovieFragmentBinding

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var castDetailViewModel: CastDetailViewModel
    lateinit var castDetailRepository: CastDetailRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = CastMovieFragmentBinding.inflate(inflater, container, false)

        val apiServiceApi: MovieServiceApi = NetworkModule.getClient()
        castDetailRepository =
            CastDetailRepository(
                apiServiceApi
            )
        castDetailViewModel = getViewModel(castId)

        castDetailViewModel.movieCredits.observe(viewLifecycleOwner) {

            if (!it.cast.isNullOrEmpty()) {
                movieAdapter =
                    MovieAdapter(
                        it.cast, requireContext()
                    )
                val layoutManager = GridLayoutManager(activity, 3)
                binding.rvMovieCredits.layoutManager = layoutManager
                binding.rvMovieCredits.setHasFixedSize(true)
                binding.rvMovieCredits.adapter = movieAdapter
            } else {
                binding.ivNoCastMovie.visibility = View.VISIBLE
            }
        }

        return binding.root
    }

    private fun getViewModel(castId: Int): CastDetailViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return CastDetailViewModel(castDetailRepository, castId) as T
            }
        })[CastDetailViewModel::class.java]
    }

}