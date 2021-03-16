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
import com.ksw.gomovie.adapter.TrailerListAdapter
import com.ksw.gomovie.adapter.TvGenreListAdapter
import com.ksw.gomovie.databinding.TvDetailAboutBinding
import com.ksw.gomovie.model.detail.ProductionCompany
import com.ksw.gomovie.model.detail.ProductionCountry
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkModule
import com.ksw.gomovie.repository.tvshow.detail.TvDetailRepository
import com.ksw.gomovie.viewmodel.TvDetailViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by KSW on 2021-03-15
 */

class TvAboutFragment(private val tvId: Int) : Fragment() {

    private lateinit var binding: TvDetailAboutBinding

    private lateinit var tvDetailViewModel: TvDetailViewModel
    private lateinit var tvDetailRepository: TvDetailRepository

    private lateinit var trailerAdapter: TrailerListAdapter
    private lateinit var tvGenreListAdapter: TvGenreListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = TvDetailAboutBinding.inflate(inflater, container, false)

        val apiServiceApi: MovieServiceApi = NetworkModule.getClient()
        tvDetailRepository = TvDetailRepository(apiServiceApi)
        tvDetailViewModel = getViewModel(tvId)
        populatingViews()

        return binding.root
    }


    @SuppressLint("SetTextI18n")
    private fun populatingViews() {
        tvDetailViewModel.tvDetails.observe(viewLifecycleOwner) {

            binding.tvTvoverview.text = it.overview
            binding.tvTvtitle.text = it.originalName
            binding.tvTvSpokenLanguage.text = it.originalLanguage.toUpperCase(Locale.ROOT)


            val runtimeList: List<Int> = it.episodeRunTime
            if (!runtimeList.isNullOrEmpty()) {
                binding.tvTvRuntimes.text =
                    runtimeList[0].toString() + " 분"
            } else {
                binding.tvTvRuntimes.text = "-"
            }

            val tvProductionCompany: List<ProductionCompany> = it.productionCompanies
            if (!tvProductionCompany.isNullOrEmpty()) {
                for (i in tvProductionCompany) {
                    binding.tvTvProductionCompanies.text = it.productionCompanies[0].name
                }
            } else {
                binding.tvTvProductionCompanies.text = "-"
            }

            if (it.numberOfEpisodes.toString().isNotEmpty()) {
                binding.tvTvTotalEpisode.text = it.numberOfEpisodes.toString() + "화"
            } else {
                binding.tvTvTotalEpisode.text = "-"
            }


            if (!it.genres.isNullOrEmpty()) {
                tvGenreListAdapter = TvGenreListAdapter(
                    it.genres, binding.root.context
                )

                val linerLayoutManager = LinearLayoutManager(activity)
                linerLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
                binding.rvTvGenre.layoutManager = linerLayoutManager
                binding.rvTvGenre.setHasFixedSize(true)
                binding.rvTvGenre.adapter = tvGenreListAdapter
            } else {
                binding.tvGenres.visibility = View.GONE
            }

            if (it.firstAirDate.isNotEmpty()) {
                val originalFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
                val targetFormat: DateFormat = SimpleDateFormat("yyyy년-MM월-dd일")
                val date: Date = originalFormat.parse(it.firstAirDate)
                val formattedDate: String = targetFormat.format(date)
                binding.tvFirstEpisodeDay.text = formattedDate
            } else {
                binding.tvFirstEpisodeDay.text = "-"
            }

            if (it.lastAirDate.isNotEmpty()) {
                val originalFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
                val targetFormat: DateFormat = SimpleDateFormat("yyyy년-MM월-dd일")
                val date: Date = originalFormat.parse(it.lastAirDate)
                val formattedDate: String = targetFormat.format(date)
                binding.tvRecentEpisodeDay.text = formattedDate
            } else {
                binding.tvRecentEpisodeDay.text = "종영"
            }

        }

        tvDetailViewModel.tvVideoDetails.observe(viewLifecycleOwner) {
            if (!it.videosList.isNullOrEmpty()) {
                trailerAdapter = TrailerListAdapter(
                    it.videosList, binding.root.context
                )

                val linearLayoutManager2 = LinearLayoutManager(activity)
                linearLayoutManager2.orientation = LinearLayoutManager.HORIZONTAL
                binding.trailerTvRecyclerview.layoutManager = linearLayoutManager2
                binding.trailerTvRecyclerview.setHasFixedSize(true)
                binding.trailerTvRecyclerview.adapter = trailerAdapter
            } else {
                binding.trailerTvRecyclerview.visibility = View.GONE
                binding.tvTrailer.text = ""
            }
        }



    }


    private fun getViewModel(tvId: Int): TvDetailViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return TvDetailViewModel(tvDetailRepository, tvId) as T
            }
        })[TvDetailViewModel::class.java]
    }


}