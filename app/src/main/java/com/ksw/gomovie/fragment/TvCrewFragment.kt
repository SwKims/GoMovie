package com.ksw.gomovie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ksw.gomovie.adapter.CrewListAdapter
import com.ksw.gomovie.databinding.TvCrewFragmentBinding
import com.ksw.gomovie.model.detail.CrewDetail
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkModule
import com.ksw.gomovie.repository.tvshow.detail.TvDetailRepository
import com.ksw.gomovie.viewmodel.TvDetailViewModel

/**
 * Created by KSW on 2021-03-16
 */

class TvCrewFragment(private val tvId: Int) : Fragment() {

    private lateinit var binding: TvCrewFragmentBinding

    private lateinit var crewListAdapter: CrewListAdapter
    private lateinit var crewDetailList: ArrayList<CrewDetail>

    lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var tvDetailModel: TvDetailViewModel
    private lateinit var tvDetailRepository: TvDetailRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = TvCrewFragmentBinding.inflate(inflater, container, false)

        val apiServiceApi: MovieServiceApi = NetworkModule.getClient()
        tvDetailRepository = TvDetailRepository(apiServiceApi)
        tvDetailModel = getViewModel(tvId)
        tvDetailModel.tvCastDetails.observe(viewLifecycleOwner) {

            if (it.crewDetail.isNotEmpty()) {
                crewDetailList = it.crewDetail
                crewListAdapter = CrewListAdapter(
                    crewDetailList, binding.root.context
                )

                linearLayoutManager = LinearLayoutManager(activity)
                linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

                binding.rvTvCrewList.layoutManager = linearLayoutManager
                binding.rvTvCrewList.setHasFixedSize(true)
                binding.rvTvCrewList.adapter = crewListAdapter

            } else {
                binding.ivNoCrewImage.visibility = View.VISIBLE
                binding.tvNoCrewText.visibility = View.VISIBLE
            }

        }

        return binding.root

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