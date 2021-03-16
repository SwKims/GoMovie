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
import com.ksw.gomovie.databinding.TvCastFragmentBinding
import com.ksw.gomovie.model.detail.CastDetail
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkModule
import com.ksw.gomovie.repository.tvshow.detail.TvDetailRepository
import com.ksw.gomovie.viewmodel.TvDetailViewModel

/**
 * Created by KSW on 2021-03-16
 */

class TvCastFragment(private val tvId: Int) : Fragment() {

    private lateinit var binding: TvCastFragmentBinding

    private lateinit var castListAdapter: CastListAdapter
    private lateinit var castDetail: ArrayList<CastDetail>

    lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var tvDetailModel: TvDetailViewModel
    private lateinit var tvDetailRepository: TvDetailRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = TvCastFragmentBinding.inflate(inflater, container, false)

        val apiServiceApi: MovieServiceApi = NetworkModule.getClient()
        tvDetailRepository = TvDetailRepository(apiServiceApi)

        tvDetailModel = getViewModel(tvId)
        tvDetailModel.tvCastDetails.observe(viewLifecycleOwner) {
            castDetail = it.castDetail
            castListAdapter = CastListAdapter(
                castDetail, binding.root.context
            )

            linearLayoutManager = LinearLayoutManager(activity)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

            binding.rvTvCastList.layoutManager = linearLayoutManager
            binding.rvTvCastList.setHasFixedSize(true)
            binding.rvTvCastList.adapter = castListAdapter
        }

        return binding.root
    }

    private fun getViewModel(tvId: Int): TvDetailViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return TvDetailViewModel(tvDetailRepository, tvId) as T
            }
        })[TvDetailViewModel::class.java]

    }

}