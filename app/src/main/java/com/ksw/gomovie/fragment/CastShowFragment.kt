package com.ksw.gomovie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ksw.gomovie.adapter.TvAdapter
import com.ksw.gomovie.databinding.CastShowFragmentBinding
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkModule
import com.ksw.gomovie.repository.cast.CastDetailRepository
import com.ksw.gomovie.viewmodel.CastDetailViewModel

/**
 * Created by KSW on 2021-03-19
 */

class CastShowFragment(private val castId: Int): Fragment() {

    private lateinit var binding: CastShowFragmentBinding

    private lateinit var tvAdapter: TvAdapter
    private lateinit var castDetailViewModel: CastDetailViewModel
    lateinit var castDetailRepository: CastDetailRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = CastShowFragmentBinding.inflate(inflater, container, false)
        val apiServiceApi: MovieServiceApi = NetworkModule.getClient()
        castDetailRepository = CastDetailRepository(
            apiServiceApi
        )

        castDetailViewModel = getViewModel(castId)
        castDetailViewModel.tvCredits.observe(viewLifecycleOwner) {

            if (!it.cast.isNullOrEmpty()) {
                tvAdapter = TvAdapter(
                    it.cast, binding.root.context
                )
                val layoutManager = GridLayoutManager(activity, 3)
                binding.rvTvCastsList.layoutManager = layoutManager
                binding.rvTvCastsList.setHasFixedSize(true)
                binding.rvTvCastsList.adapter = tvAdapter
            } else {
                binding.ivNoCastTv.visibility = View.VISIBLE
            }

        }

        return binding.root
    }

    private fun getViewModel(castId: Int): CastDetailViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return CastDetailViewModel(castDetailRepository, castId) as T
            }
        })[CastDetailViewModel::class.java]
    }


}