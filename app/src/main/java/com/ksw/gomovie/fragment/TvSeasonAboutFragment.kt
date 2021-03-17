package com.ksw.gomovie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ksw.gomovie.adapter.TrailerListAdapter
import com.ksw.gomovie.databinding.TvSeasonAboutFragmentBinding
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkModule
import com.ksw.gomovie.repository.tvshow.season.SeasonDetailRepository
import com.ksw.gomovie.viewmodel.TvSeasonDetailViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by KSW on 2021-03-17
 */

class TvSeasonAboutFragment(private val tvId: Int, private val seasonNumber: Int) : Fragment() {

    private lateinit var binding: TvSeasonAboutFragmentBinding

    private lateinit var tvSeasonDetailViewModel: TvSeasonDetailViewModel
    private lateinit var tvSeasonDetailRepository: SeasonDetailRepository
    private lateinit var tvSeasonTrailerAdapter: TrailerListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = TvSeasonAboutFragmentBinding.inflate(inflater, container, false)
        val apiService: MovieServiceApi = NetworkModule.getClient()
        tvSeasonDetailRepository = SeasonDetailRepository(apiService)
        tvSeasonDetailViewModel = getViewModel(tvId, seasonNumber)

        tvSeasonDetailViewModel.tvSeasonDetails.observe(viewLifecycleOwner) {
            if (it.overview.isNotEmpty()) {
                binding.tvSeasonoverview.text = it.overview
            } else {
                binding.seasonSummary.visibility = View.GONE
            }
            binding.tvSeasonEpisodeCounts.text = it.episodes.size.toString()

            if (it.airDate.isNotEmpty()) {
                val originalFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
                val targetFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                val date: Date = originalFormat.parse(it.airDate)
                val formatDate: String = targetFormat.format(date)
                binding.tvSeasonStartday.text = formatDate
            } else {
                binding.tvSeasonStartday.text = "-"
            }

        }

        tvSeasonDetailViewModel.tvSeasonVideos.observe(viewLifecycleOwner) {
            if (!it.videosList.isNullOrEmpty()) {
                tvSeasonTrailerAdapter = TrailerListAdapter(
                    it.videosList, binding.root.context
                )
                val linerLayoutManager = LinearLayoutManager(activity)
                linerLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
                binding.trailerSeasonRecyclerview.layoutManager = linerLayoutManager
                binding.trailerSeasonRecyclerview.setHasFixedSize(true)
                binding.trailerSeasonRecyclerview.adapter = tvSeasonTrailerAdapter
            } else {
                binding.trailer.visibility = View.GONE
            }
        }
        return binding.root
    }

    private fun getViewModel(tvId: Int, seasonNumber: Int): TvSeasonDetailViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return TvSeasonDetailViewModel(tvSeasonDetailRepository, tvId, seasonNumber) as T
            }
        })[TvSeasonDetailViewModel::class.java]
    }


}