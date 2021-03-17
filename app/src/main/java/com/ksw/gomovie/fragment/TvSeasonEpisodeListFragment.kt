package com.ksw.gomovie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ksw.gomovie.adapter.SeasonEpisodeListAdapter
import com.ksw.gomovie.databinding.TvSeasonEpisodeFragmentBinding
import com.ksw.gomovie.model.tv.Episode
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkModule
import com.ksw.gomovie.repository.tvshow.season.SeasonDetailRepository
import com.ksw.gomovie.viewmodel.TvSeasonDetailViewModel

/**
 * Created by KSW on 2021-03-17
 */

class TvSeasonEpisodeListFragment(private val tvId: Int, val seasonNumber: Int): Fragment() {

    private lateinit var binding: TvSeasonEpisodeFragmentBinding

    private lateinit var tvSeasonListAdapter: SeasonEpisodeListAdapter
    private lateinit var tvSeasonEpisodeList: List<Episode>

    private lateinit var seasonViewModel: TvSeasonDetailViewModel
    private lateinit var tvSeasonRepository: SeasonDetailRepository
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = TvSeasonEpisodeFragmentBinding.inflate(inflater, container, false)
        val apiService: MovieServiceApi = NetworkModule.getClient()
        tvSeasonRepository = SeasonDetailRepository(apiService)
        seasonViewModel = getViewModel(tvId, seasonNumber)
        seasonViewModel.tvSeasonDetails.observe(viewLifecycleOwner) {
            tvSeasonEpisodeList = it.episodes
            tvSeasonListAdapter = SeasonEpisodeListAdapter(
                tvSeasonEpisodeList, binding.root.context
            )
            linearLayoutManager = LinearLayoutManager(activity)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

            binding.rvEpisodeList.layoutManager = linearLayoutManager
            binding.rvEpisodeList.setHasFixedSize(true)
            binding.rvEpisodeList.adapter = tvSeasonListAdapter
        }

        return binding.root

    }

    private fun getViewModel(tvId: Int, seasonNumber: Int): TvSeasonDetailViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return TvSeasonDetailViewModel(tvSeasonRepository, tvId, seasonNumber) as T
            }
        })[TvSeasonDetailViewModel::class.java]

    }


}