package com.ksw.gomovie.activity

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.ksw.gomovie.R
import com.ksw.gomovie.adapter.SeasonDetailTabAdapter
import com.ksw.gomovie.databinding.SeasonDetailBinding
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkModule
import com.ksw.gomovie.repository.tvshow.detail.TvDetailRepository
import com.ksw.gomovie.repository.tvshow.season.SeasonDetailRepository
import com.ksw.gomovie.util.BaseAppCompatActivity
import com.ksw.gomovie.util.Constants.Companion.IMAGE_BASE_URL
import com.ksw.gomovie.viewmodel.TvDetailViewModel
import com.ksw.gomovie.viewmodel.TvSeasonDetailViewModel

/**
 * Created by KSW on 2021-03-16
 */

class SeasonActivity : BaseAppCompatActivity() {

    private lateinit var binding: SeasonDetailBinding
    private lateinit var tvSeasonDetailViewModel: TvSeasonDetailViewModel
    private lateinit var tvSeasonRepository: SeasonDetailRepository

    private lateinit var tvDetailViewModel: TvDetailViewModel
    private lateinit var tvDetailRepository: TvDetailRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SeasonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.extras
        val seasonNumber = data!!.getInt("seasonNumber")
        val tvId = data.getInt("tvId")

        val apiService: MovieServiceApi = NetworkModule.getClient()
        tvSeasonRepository = SeasonDetailRepository(apiService)
        tvSeasonDetailViewModel = getViewModel(tvId, seasonNumber)

        tvDetailRepository = TvDetailRepository(apiService)
        tvDetailViewModel = getViewModel2(tvId)

        binding.seasonDetailTabLayout.addTab(
            binding.seasonDetailTabLayout.newTab().setText("About")
        )
        binding.seasonDetailTabLayout.addTab(
            binding.seasonDetailTabLayout.newTab().setText("Episodes")
        )
        binding.seasonDetailTabLayout.addTab(binding.seasonDetailTabLayout.newTab().setText("Cast"))

        val adapter = SeasonDetailTabAdapter(
            seasonNumber, tvId, this, supportFragmentManager, binding.seasonDetailTabLayout.tabCount
        )
        binding.seasonDetailViewpager.adapter = adapter

        binding.seasonDetailTabLayout.setupWithViewPager(binding.seasonDetailViewpager)
        binding.seasonDetailViewpager.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(
                binding.seasonDetailTabLayout
            )
        )

        tvDetailViewModel.tvDetails.observe(this) {
            binding.tvSeasonReleaseDate.text = it.name
            if (it.backdropPath.isNotEmpty()) {
                val backDropUrl: String = IMAGE_BASE_URL + it.backdropPath
                Glide.with(this)
                    .load(backDropUrl)
                    .into(binding.ivSeasonDetailImage)
            } else {
                Glide.with(this)
                    .load(R.drawable.ic_error)
                    .centerInside()
                    .into(binding.ivSeasonDetailImage)
            }
        }

        tvSeasonDetailViewModel.tvSeasonDetails.observe(this) {
            setupToolbar(it.name)
            binding.tvSeasonDetailTitle.text = it.name
            if (it.posterPath.isNotEmpty()) {
                val posterUrl: String = IMAGE_BASE_URL + it.posterPath
                Glide.with(this)
                    .load(posterUrl)
                    .into(binding.ivSeasonPoster)
            } else {
                Glide.with(this)
                    .load(R.drawable.ic_error)
                    .centerInside()
                    .into(binding.ivSeasonDetailImage)
            }
        }

    }

    private fun setupToolbar(title: String) {
        setSupportActionBar(binding.toolbarDetail.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)

        binding.seasonDetailAppBar.addOnOffsetChangedListener(object :
            AppBarLayout.OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    binding.seasonDetailToolbar.title = title
                    binding.seasonDetailToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
                    isShow = true
                } else if (isShow) {
                    binding.seasonDetailToolbar.title = " "
                    isShow = false
                }
            }

        })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    private fun getViewModel(tvId: Int, seasonNumber: Int): TvSeasonDetailViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return TvSeasonDetailViewModel(tvSeasonRepository, tvId, seasonNumber) as T
            }
        })[TvSeasonDetailViewModel::class.java]
    }

    private fun getViewModel2(tvId: Int): TvDetailViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return TvDetailViewModel(tvDetailRepository, tvId) as T
            }
        })[TvDetailViewModel::class.java]
    }
}