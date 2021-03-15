package com.ksw.gomovie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ksw.gomovie.adapter.TvPagedListAdapter
import com.ksw.gomovie.databinding.TvListFragmentBinding
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkModule
import com.ksw.gomovie.network.NetworkState
import com.ksw.gomovie.repository.tvshow.TvPagedListRepository
import com.ksw.gomovie.viewmodel.TvListViewModel

/**
 * Created by KSW on 2021-03-12
 */

class TvListFragment(private var type: String) : Fragment() {

    private lateinit var binding: TvListFragmentBinding

    private lateinit var tvPagedListAdapter: TvPagedListAdapter
    private lateinit var tvListViewModel: TvListViewModel
    lateinit var tvPagedListRepository: TvPagedListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tvPagedListAdapter = TvPagedListAdapter(
            requireActivity().applicationContext
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = TvListFragmentBinding.inflate(inflater, container, false)

        val apiService: MovieServiceApi = NetworkModule.getClient()
        tvPagedListRepository = TvPagedListRepository(apiService)

        tvListViewModel = tvListViewModel(type)
        populatingViews()

        return binding.root
    }


    private fun tvListViewModel(type: String): TvListViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return TvListViewModel(tvPagedListRepository, type) as T
            }
        })[TvListViewModel::class.java]
    }


    private fun populatingViews() {
        tvListViewModel.tvPagedList.observe(viewLifecycleOwner) {
            tvPagedListAdapter.submitList(it)
            val layoutManager = GridLayoutManager(activity, 3)
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val viewType = tvPagedListAdapter.getItemViewType(position)
                    return if (viewType == tvPagedListAdapter.POPULAR_TV_VIEW_TYPE) 1
                    else 3
                }
            }
            binding.rvTvList.layoutManager = layoutManager
            binding.rvTvList.setHasFixedSize(true)
            binding.rvTvList.adapter = tvPagedListAdapter
        }

        tvListViewModel.networkState.observe(viewLifecycleOwner) {
            binding.pbMain.visibility =
                if (tvListViewModel.tvListEmpty() && it == NetworkState.LOADING) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            if (!tvListViewModel.tvListEmpty()) {
                tvPagedListAdapter.setNetworkState(it)
            }
        }


    }


}