package com.ksw.gomovie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ksw.gomovie.R
import com.ksw.gomovie.adapter.TvSeasonListAdapter
import com.ksw.gomovie.databinding.TvSeasonFragmentBinding
import com.ksw.gomovie.model.tv.Season
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkModule
import com.ksw.gomovie.repository.tvshow.detail.TvDetailRepository
import com.ksw.gomovie.viewmodel.TvDetailViewModel

/**
 * Created by KSW on 2021-03-16
 */

class TvSeasonsFragment(val tvId: Int): Fragment() {

    private lateinit var binding: TvSeasonFragmentBinding

    private lateinit var tvSeasonAdapter: TvSeasonListAdapter
    private lateinit var tvSeasonList: List<Season>

    private lateinit var tvDetailViewModel: TvDetailViewModel
    private lateinit var tvRepository: TvDetailRepository

    lateinit var linerLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = TvSeasonFragmentBinding.inflate(inflater, container, false)
        val apiService: MovieServiceApi = NetworkModule.getClient()
        tvRepository = TvDetailRepository(apiService)

        tvDetailViewModel = getViewModel(tvId)
        tvDetailViewModel.tvDetails.observe(viewLifecycleOwner) {
            tvSeasonList = it.seasons
            tvSeasonAdapter = TvSeasonListAdapter(
                tvId, tvSeasonList, binding.root.context
            )
            linerLayoutManager = LinearLayoutManager(activity)
            linerLayoutManager.orientation = LinearLayoutManager.VERTICAL

            binding.rvTvSeasonList.layoutManager = linerLayoutManager
            binding.rvTvSeasonList.setHasFixedSize(true)
            binding.rvTvSeasonList.adapter = tvSeasonAdapter
        }

        return binding.root
    }

    private fun getViewModel(tvId: Int): TvDetailViewModel {
        return ViewModelProvider(this, object :ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return TvDetailViewModel(tvRepository, tvId) as T
            }
        })[TvDetailViewModel::class.java]
    }

}