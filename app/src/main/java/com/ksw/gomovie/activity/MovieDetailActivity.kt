package com.ksw.gomovie.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.ksw.gomovie.R
import com.ksw.gomovie.adapter.MovieDetailTabAdapter
import com.ksw.gomovie.databinding.MovieDetailBinding
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkModule
import com.ksw.gomovie.repository.moviedetail.MovieDetailRepository
import com.ksw.gomovie.util.Constants.Companion.IMAGE_BASE_URL
import com.ksw.gomovie.util.Constants.Companion.homePage
import com.ksw.gomovie.viewmodel.MovieDetailViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by KSW on 2021-02-24
 */

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: MovieDetailBinding

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var movieDetailRepository: MovieDetailRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.extras
        var movieId = data!!.getInt("id")

        val apiService: MovieServiceApi = NetworkModule.getClient()
        movieDetailRepository = MovieDetailRepository(apiService)
        viewModel = getViewModel(movieId)

        binding.movieDetailTabLayout.addTab(binding.movieDetailTabLayout.newTab().setText("About"))
        binding.movieDetailTabLayout.addTab(binding.movieDetailTabLayout.newTab().setText("Cast"))
        binding.movieDetailTabLayout.addTab(binding.movieDetailTabLayout.newTab().setText("Crew"))

        val adapter = MovieDetailTabAdapter(
            movieId,
            supportFragmentManager,
            binding.movieDetailTabLayout.tabCount
        )
        binding.movieDetailViewpager.adapter = adapter

        binding.movieDetailTabLayout.setupWithViewPager(binding.movieDetailViewpager)
        binding.movieDetailViewpager.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(
                binding.movieDetailTabLayout
            )
        )

        viewModel.movieDetails.observe(this, Observer {
            binding.tvMovieDetailTitle.text = it.title

            if (it.releaseDate.isNotEmpty()) {
                val originalFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
                val targetFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                val date: Date = originalFormat.parse(it.releaseDate)
                val formatDate: String = targetFormat.format(date)
                binding.tvReleaseDate.text = formatDate
            }

            binding.tvStatus.text = it.status
            homePage = it.homepage

            binding.btHomepage.setOnClickListener {
                openMovieSite()
            }

            if (it.backdropPath.isNotEmpty()) {
                val detailUrl: String = IMAGE_BASE_URL + it.backdropPath
                Glide.with(this)
                    .load(detailUrl)
                    .into(binding.ivMovieDetailImage)
            } else {
                Glide.with(this)
                    .load(R.drawable.ic_error)
                    .centerInside()
                    .into(binding.ivMovieDetailImage)
            }

            if (it.posterPath.isNotEmpty()) {
                val moviePosterUrl: String = IMAGE_BASE_URL + it.posterPath
                Glide.with(this)
                    .load(moviePosterUrl)
                    .into(binding.ivMoviePoster)
            } else {
                Glide.with(this)
                    .load(R.drawable.ic_error)
                    .centerInside()
                    .into(binding.ivMoviePoster)
            }
        })

    }

    private fun openMovieSite() {
        if (homePage == "") {
            Toast.makeText(this, "홈페이지가 없습니다!", Toast.LENGTH_SHORT).show()
        }
        else {
            if (homePage != "") {
                val browserIntent =
                    Intent(Intent.ACTION_VIEW, Uri.parse(homePage))
                val browserChooserIntent =
                    Intent.createChooser(browserIntent, "Open with")
                startActivity(browserChooserIntent)
            }
        }
    }

    private fun getViewModel(movieId: Int): MovieDetailViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MovieDetailViewModel(movieDetailRepository, movieId) as T
            }
        })[MovieDetailViewModel::class.java]
    }

}