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
import com.ksw.gomovie.adapter.CastListAdapter
import com.ksw.gomovie.databinding.MovieCastFramentBinding
import com.ksw.gomovie.model.detail.CastDetail
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkModule
import com.ksw.gomovie.repository.moviedetail.MovieDetailRepository
import com.ksw.gomovie.viewmodel.MovieDetailViewModel

/**
 * Created by KSW on 2021-02-26
 */


class MovieCastFragment(private var movieId: Int) : Fragment() {

    private lateinit var binding: MovieCastFramentBinding

    private lateinit var castListAdapter: CastListAdapter
    private lateinit var castDetail: ArrayList<CastDetail>

    lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var movieDetailModel: MovieDetailViewModel
    private lateinit var movieDetailRepository: MovieDetailRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = MovieCastFramentBinding.inflate(inflater, container, false)

        val apiServiceApi: MovieServiceApi = NetworkModule.getClient()
        movieDetailRepository = MovieDetailRepository(apiServiceApi)
        movieDetailModel = getViewModel(movieId)
        movieDetailModel.castDetails.observe(viewLifecycleOwner) {
            castDetail = it.castDetail
            castListAdapter =
                CastListAdapter(
                    castDetail,
                    binding.root.context
                )

            linearLayoutManager = LinearLayoutManager(activity)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

            binding.rvCastList.layoutManager = linearLayoutManager
            binding.rvCastList.setHasFixedSize(true)
            binding.rvCastList.adapter = castListAdapter
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