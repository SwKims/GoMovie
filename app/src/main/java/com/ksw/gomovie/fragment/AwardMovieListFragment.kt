package com.ksw.gomovie.fragment

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.GridLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ksw.gomovie.R
import com.ksw.gomovie.adapter.MovieAdapter
import com.ksw.gomovie.databinding.AwardMovieBinding
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkModule
import com.ksw.gomovie.network.NetworkState
import com.ksw.gomovie.repository.awards.list.FeaturedListRepository
import com.ksw.gomovie.viewmodel.FeatureViewModel

/**
 * Created by KSW on 2021-03-18
 */

class AwardMovieListFragment : AppCompatActivity() {

    private lateinit var binding: AwardMovieBinding

    private lateinit var featuredListRepository: FeaturedListRepository
    private lateinit var featureViewModel: FeatureViewModel
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AwardMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.extras
        var listId = data!!.getInt("listId")

        val apiService: MovieServiceApi = NetworkModule.getClient()
        featuredListRepository = FeaturedListRepository(apiService)
        featureViewModel = getMovie(listId)

        val linearLayOutManager = GridLayoutManager(this, 2)
        featureViewModel.featuredMovieList.observe(this) {
            val movieList = it.items
            movieAdapter = MovieAdapter(movieList, this)
            binding.rvAwardMovieList.layoutManager = linearLayOutManager
            binding.rvAwardMovieList.setHasFixedSize(true)
            binding.rvAwardMovieList.adapter = movieAdapter
        }

        featureViewModel.networkState.observe(this) {
            binding.pbAwardMain.visibility =
                if (it == NetworkState.LOADING) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
        }

    }

    private fun getMovie(listId: Int): FeatureViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return FeatureViewModel(featuredListRepository, listId) as T
            }
        })[FeatureViewModel::class.java]
    }

}