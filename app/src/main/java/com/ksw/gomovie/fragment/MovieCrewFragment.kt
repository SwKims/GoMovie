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
import com.ksw.gomovie.databinding.MovieCrewFramentBinding
import com.ksw.gomovie.model.detail.CrewDetail
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkModule
import com.ksw.gomovie.repository.moviedetail.MovieDetailRepository
import com.ksw.gomovie.viewmodel.MovieDetailViewModel

/**
 * Created by KSW on 2021-02-26
 */

class MovieCrewFragment(private var movieId: Int): Fragment() {

    private lateinit var binding: MovieCrewFramentBinding

    private lateinit var crewListAdapter: CrewListAdapter
    private lateinit var crewDetailList: ArrayList<CrewDetail>

    lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var movieDetailModel: MovieDetailViewModel
    private lateinit var movieDetailRepository: MovieDetailRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = MovieCrewFramentBinding.inflate(inflater, container, false)

        val apiServiceApi: MovieServiceApi = NetworkModule.getClient()
        movieDetailRepository = MovieDetailRepository(apiServiceApi)
        movieDetailModel = getViewModel(movieId)
        movieDetailModel.castDetails.observe(viewLifecycleOwner) {
            crewDetailList = it.crewDetail
            crewListAdapter = CrewListAdapter(
                crewDetailList,
                binding.root.context
            )

            linearLayoutManager = LinearLayoutManager(activity)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

            binding.rvCrewtList.layoutManager = linearLayoutManager
            binding.rvCrewtList.setHasFixedSize(true)
            binding.rvCrewtList.adapter = crewListAdapter
        }

        return binding.root
    }

    private fun getViewModel(movieId: Int): MovieDetailViewModel {
        return ViewModelProvider(this, object: ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MovieDetailViewModel(movieDetailRepository, movieId) as T
            }
        })[MovieDetailViewModel::class.java]

    }


}