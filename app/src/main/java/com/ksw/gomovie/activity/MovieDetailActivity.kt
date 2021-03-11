package com.ksw.gomovie.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.ksw.gomovie.R
import com.ksw.gomovie.adapter.MovieDetailImageSlideAdapter
import com.ksw.gomovie.adapter.MovieDetailTabAdapter
import com.ksw.gomovie.databinding.MovieDetailBinding
import com.ksw.gomovie.model.detail.Image
import com.ksw.gomovie.model.detail.Poster
import com.ksw.gomovie.model.main.Movie
import com.ksw.gomovie.model.main.MovieDetail
import com.ksw.gomovie.model.main.PeopleProfileImages
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkModule
import com.ksw.gomovie.repository.moviedetail.MovieDetailRepository
import com.ksw.gomovie.util.BaseAppCompatActivity
import com.ksw.gomovie.util.Constants.Companion.IMAGE_BASE_URL
import com.ksw.gomovie.util.Constants.Companion.homePage
import com.ksw.gomovie.viewmodel.MovieDetailViewModel
import com.smarteist.autoimageslider.SliderAnimations
import com.stfalcon.imageviewer.StfalconImageViewer
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by KSW on 2021-02-24
 */

class MovieDetailActivity : BaseAppCompatActivity() {

    private lateinit var binding: MovieDetailBinding

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var movieDetailRepository: MovieDetailRepository
//    private lateinit var posterMovie: List<Poster>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var data = intent.extras
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

            setupToolbar(it.title)

            binding.tvMovieDetailTitle.text = it.title

            if (it.releaseDate.isNotEmpty()) {
                val originalFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
                val targetFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                val date: Date = originalFormat.parse(it.releaseDate)
                val formatDate: String = targetFormat.format(date)
                binding.tvReleaseDate.text = formatDate
            }

//            binding.tvStatus.text = it.status
            homePage = it.homepage

            binding.btHomepage.setOnClickListener {
                openMovieSite()
            }

            it?.voteAverage?.let { rating ->
                binding.movieRating.rating = (rating / 2).toFloat()
            }

//            setImageSlider(it.images!!.backdrops)

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

            /* binding.ivMoviePoster.setOnClickListener {
                 StfalconImageViewer.Builder(
                     this, posterMovie
                 ) { posterImage: ImageView, poster: Poster ->
                     Glide.with(this)
                         .load(IMAGE_BASE_URL + poster.filePath)
                         .into(posterImage)
                 }
                     .withHiddenStatusBar(false)
                     .show()
             }*/

        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        super.onCreateOptionsMenu(menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupToolbar(title: String) {
        setSupportActionBar(binding.toolbarDetail.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)

        binding.movieDetailAppBar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener{
            var isShow = true
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1 ) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    binding.movieDetailToolbar.title = title
                    binding.movieDetailToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
                    isShow = true
                } else if (isShow) {
                    binding.movieDetailToolbar.title = " "
                    isShow = false
                }
            }

        })

    }


    private fun openMovieSite() {
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

    /*private fun setImageSlider(image: List<Image>) {
        image?.let {
            binding.ivMovieDetailImage.sliderAdapter = MovieDetailImageSlideAdapter(image)
            binding.ivMovieDetailImage.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION)
            binding.ivMovieDetailImage.setIndicatorVisibility(false)
        }
    }*/

    private fun getViewModel(movieId: Int): MovieDetailViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MovieDetailViewModel(movieDetailRepository, movieId) as T
            }
        })[MovieDetailViewModel::class.java]
    }

}

