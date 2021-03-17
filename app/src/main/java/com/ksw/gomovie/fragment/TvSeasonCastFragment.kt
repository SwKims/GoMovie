package com.ksw.gomovie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ksw.gomovie.adapter.CastListAdapter
import com.ksw.gomovie.databinding.TvSeasonCastFragmentBinding
import com.ksw.gomovie.model.detail.CastDetail
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkModule
import com.ksw.gomovie.repository.tvshow.season.SeasonDetailRepository
import com.ksw.gomovie.viewmodel.TvSeasonDetailViewModel

/**
 * Created by KSW on 2021-03-17
 */

class TvSeasonCastFragment(private val tvId: Int, private val seasonNumber: Int) : Fragment() {

    private lateinit var binding: TvSeasonCastFragmentBinding

    private lateinit var tvSeasonCastListAdapter: CastListAdapter
    private lateinit var tvSeasonCastDetail: ArrayList<CastDetail>
    private lateinit var tvSeasonViewModel: TvSeasonDetailViewModel
    private lateinit var tvSeasonRepository: SeasonDetailRepository

    lateinit var linerLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = TvSeasonCastFragmentBinding.inflate(inflater, container, false)
        val apiService: MovieServiceApi = NetworkModule.getClient()
        tvSeasonRepository = SeasonDetailRepository(apiService)
        tvSeasonViewModel = getViewModel(tvId, seasonNumber)

        tvSeasonViewModel.tvSeasonCast.observe(viewLifecycleOwner) {
            if (!it.castDetail.isNullOrEmpty()) {
                tvSeasonCastListAdapter = CastListAdapter(
                    it.castDetail, binding.root.context
                )
                val linearLayoutManager = LinearLayoutManager(activity)
                linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
                binding.rvSeasonCastList.layoutManager = linearLayoutManager
                binding.rvSeasonCastList.setHasFixedSize(true)
                binding.rvSeasonCastList.adapter = tvSeasonCastListAdapter
            } else {
                binding.ivNoCastImage.visibility = View.VISIBLE
                binding.tvNoCastText.visibility = View.VISIBLE
            }
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