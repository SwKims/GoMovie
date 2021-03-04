package com.ksw.gomovie.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ksw.gomovie.adapter.MovieGenreListAdapter
import com.ksw.gomovie.adapter.TrailerListAdapter
import com.ksw.gomovie.databinding.MovieDetailAboutBinding
import com.ksw.gomovie.model.detail.*
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkModule
import com.ksw.gomovie.repository.moviedetail.MovieDetailRepository
import com.ksw.gomovie.viewmodel.MovieDetailViewModel

/**
 * Created by KSW on 2021-02-24
 */
class MovieAboutFragment(private var movieId: Int) : Fragment() {

    private lateinit var binding: MovieDetailAboutBinding
//    private val binding get() = _binding

    private lateinit var movieDetailViewModel: MovieDetailViewModel
    private lateinit var movieDetailRepository: MovieDetailRepository

    private lateinit var trailerAdapter: TrailerListAdapter
    private lateinit var movieGenreListAdapter: MovieGenreListAdapter

    private lateinit var movieBackdrop: List<BackDrop>
    private lateinit var moviePoster: List<Poster>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = MovieDetailAboutBinding.inflate(inflater, container, false)

        val apiServiceApi: MovieServiceApi = NetworkModule.getClient()
        movieDetailRepository = MovieDetailRepository(apiServiceApi)
        movieDetailViewModel = getViewModel(movieId)
        populatingViews()

        return binding.root

    }

    @SuppressLint("SetTextI18n")
    private fun populatingViews() {
        movieDetailViewModel.movieDetails.observe(viewLifecycleOwner) {

            binding.tvOverview.text = it.overview
            binding.tvTitle.text = it.originalTitle

            if (it.runtime.toString() != "0") {
                binding.tvMovieRuntime.text = it.runtime.toString() + " 분"
            } else {
                binding.tvMovieRuntime.text = "?"
            }

//            binding.tvSpokenLanguage.text = "-"

            val spokenLanguageList: List<SpokenLanguage> = it.spokenLanguages
            if (!spokenLanguageList.isNullOrEmpty()) {
                for (i in spokenLanguageList) {
                    binding.tvSpokenLanguage.text = spokenLanguageList[0].name
                }
            } else {
                binding.tvSpokenLanguage.text = "-"
            }

            val productionCompany: List<ProductionCompany> = it.productionCompanies
            if (!productionCompany.isNullOrEmpty()) {
                binding.tvProductionCompanies.text = productionCompany[0].name
            } else {
                binding.tvProductionCompanies.text = "-"
            }

            val productionCountry: List<ProductionCountry> = it.productionCountries
            if (!productionCountry.isNullOrEmpty()) {
                binding.tvProductionCountries.text = productionCountry[0].name
            } else {
                binding.tvProductionCountries.text = "-"
            }

            if (!it.genres.isNullOrEmpty()) {
                movieGenreListAdapter = MovieGenreListAdapter(
                    it.genres, binding.root.context
                )

                val linearLayoutManager2 = LinearLayoutManager(activity)
                linearLayoutManager2.orientation = LinearLayoutManager.HORIZONTAL
                binding.rvMovieGenre.layoutManager = linearLayoutManager2
                binding.rvMovieGenre.setHasFixedSize(true)
                binding.rvMovieGenre.adapter = movieGenreListAdapter
            } else {
                binding.genres.visibility = View.GONE
            }

        }


        movieDetailViewModel.videoDetails.observe(viewLifecycleOwner) {

            if (!it.videosList.isNullOrEmpty()) {
                trailerAdapter =
                    TrailerListAdapter(
                        it.videosList,
                        binding.root.context
                    )
                val linearLayoutManager = LinearLayoutManager(activity)
                linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

                binding.trailerRecyclerview.layoutManager = linearLayoutManager
                binding.trailerRecyclerview.setHasFixedSize(true)
                binding.trailerRecyclerview.adapter = trailerAdapter
            } else {
                binding.trailerRecyclerview.visibility = View.GONE
            }
        }


    }


    private fun getViewModel(movieId: Int): MovieDetailViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MovieDetailViewModel(movieDetailRepository, movieId) as T
            }
        })[MovieDetailViewModel::class.java]
    }
}


