package com.ksw.gomovie.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.ksw.gomovie.R
import com.ksw.gomovie.adapter.TvDetailTabAdapter
import com.ksw.gomovie.databinding.TvDetailBinding
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkModule
import com.ksw.gomovie.repository.tvshow.detail.TvDetailRepository
import com.ksw.gomovie.util.BaseAppCompatActivity
import com.ksw.gomovie.util.Constants.Companion.IMAGE_BASE_URL
import com.ksw.gomovie.util.Constants.Companion.homePage
import com.ksw.gomovie.viewmodel.TvDetailViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by KSW on 2021-03-15
 */

class TvDetailActivity : BaseAppCompatActivity() {

    private lateinit var binding: TvDetailBinding
    private lateinit var viewModel: TvDetailViewModel
    private lateinit var tvDetailRepository: TvDetailRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TvDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var data = intent.extras
        var tvId = data!!.getInt("id")

        val apiServiceApi: MovieServiceApi = NetworkModule.getClient()
        tvDetailRepository = TvDetailRepository(apiServiceApi)
        viewModel = getViewModel(tvId)

        binding.TvDetailTabLayout.addTab(binding.TvDetailTabLayout.newTab().setText("About"))
        binding.TvDetailTabLayout.addTab(binding.TvDetailTabLayout.newTab().setText("Cast"))
        binding.TvDetailTabLayout.addTab(binding.TvDetailTabLayout.newTab().setText("Crew"))
        binding.TvDetailTabLayout.addTab(binding.TvDetailTabLayout.newTab().setText("Seasons"))

        val adapter = TvDetailTabAdapter(
            tvId, supportFragmentManager, binding.TvDetailTabLayout.tabCount
        )

        binding.TvDetailViewpager.adapter = adapter
        binding.TvDetailTabLayout.setupWithViewPager(binding.TvDetailViewpager)
        binding.TvDetailViewpager.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(
                binding.TvDetailTabLayout
            )
        )

        viewModel.tvDetails.observe(this) {

            setupToolbar(it.name)

            binding.tvTvDetailTitle.text = it.name


            if (it.firstAirDate.isNotEmpty()) {
                val originalFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
                val targetFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                val date: Date = originalFormat.parse(it.firstAirDate)
                val formatDate: String = targetFormat.format(date)
                binding.tvTvReleaseDate.text = formatDate
            }

            homePage = it.homepage

            binding.btTvhomepage.setOnClickListener {
                openTvHomePage()
            }

            /*it?.voteAverage?.let { rating ->
                binding.TvRating.rating = (rating / 2).toFloat()
            }*/

            if (it.status == "Returning Series") {
                binding.tvStatus.text = "방영중"
            } else {
                binding.tvStatus.text = "종영"
            }

            if (it.backdropPath.isNotEmpty()) {
                val detailUrl: String = IMAGE_BASE_URL + it.backdropPath
                Glide.with(this)
                    .load(detailUrl)
                    .into(binding.ivTvDetailImage)
            } else {
                Glide.with(this)
                    .load(R.drawable.ic_error)
                    .centerInside()
                    .into(binding.ivTvDetailImage)
            }

            if (it.posterPath.isNotEmpty()) {
                val tvPosterUrl: String = IMAGE_BASE_URL + it.posterPath
                Glide.with(this)
                    .load(tvPosterUrl)
                    .into(binding.ivTvPoster)
            } else {
                Glide.with(this)
                    .load(R.drawable.ic_error)
                    .centerInside()
                    .into(binding.ivTvPoster)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupToolbar(title: String) {
        setSupportActionBar(binding.toolbarDetail.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)

        binding.tvDetailAppBar.addOnOffsetChangedListener(object :
            AppBarLayout.OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    binding.tvDetailToolbar.title = title
                    binding.tvDetailToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
                    isShow = true
                } else if (isShow) {
                    binding.tvDetailToolbar.title = " "
                    isShow = false
                }
            }

        })

    }

    private fun openTvHomePage() {
        if (homePage == "") {
            Toast.makeText(this, "홈페이지가 없습니다!", Toast.LENGTH_SHORT).show()
        } else {
            if (homePage != "") {
                val browserIntent =
                    Intent(Intent.ACTION_VIEW, Uri.parse(homePage))
                val browserChooserIntent =
                    Intent.createChooser(browserIntent, "Open with")
                startActivity(browserChooserIntent)
            }
        }
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