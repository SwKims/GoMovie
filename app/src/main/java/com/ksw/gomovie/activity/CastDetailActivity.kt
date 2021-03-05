package com.ksw.gomovie.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.ksw.gomovie.R
import com.ksw.gomovie.adapter.CastDetailTabAdapter
import com.ksw.gomovie.databinding.CastDetailBinding
import com.ksw.gomovie.model.detail.PeopleDetail
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkModule
import com.ksw.gomovie.repository.cast.CastDetailRepository
import com.ksw.gomovie.util.Constants.Companion.IMAGE_BASE_URL
import com.ksw.gomovie.viewmodel.CastDetailViewModel
import com.ksw.gomovie.viewmodel.MovieDetailViewModel

/**
 * Created by KSW on 2021-03-05
 */

class CastDetailActivity : AppCompatActivity() {

    private lateinit var binding: CastDetailBinding

    lateinit var castDetailRepository: CastDetailRepository
    private lateinit var castDetailViewModel: CastDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CastDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val peopleDetail = intent.extras
        val peopleId: Int = peopleDetail!!.getInt("id")

        val apiService: MovieServiceApi = NetworkModule.getClient()
        castDetailRepository =
            CastDetailRepository(
                apiService
            )
        castDetailViewModel = getViewModel(peopleId)

        binding.castDetailTabLayout.addTab(binding.castDetailTabLayout.newTab().setText("About"))
        binding.castDetailTabLayout.addTab(binding.castDetailTabLayout.newTab().setText("Movies"))
        binding.castDetailTabLayout.addTab(binding.castDetailTabLayout.newTab().setText("Shows"))

        val adapter = CastDetailTabAdapter(
            peopleId, supportFragmentManager, binding.castDetailTabLayout.tabCount
        )

        binding.castDetailViewpager.adapter = adapter

        binding.castDetailTabLayout.setupWithViewPager(binding.castDetailViewpager)
        binding.castDetailViewpager.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(
                binding.castDetailTabLayout
            )
        )

        castDetailViewModel.peopleDetails.observe(this) {
            bindCast(it)
        }

    }

    fun bindCast(it: PeopleDetail) {
        binding.tvPeopleName.text = it.name
        val profilePath = it.profilePath
        if (profilePath.isNotEmpty()) {
            val path = IMAGE_BASE_URL + profilePath
            Glide.with(this)
                .load(path)
                .into(binding.castImage)
        } else {
            Glide.with(this).load(R.drawable.ic_error).into(binding.castImage)
        }
    }

    private fun getViewModel(peopleId: Int): CastDetailViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return CastDetailViewModel(castDetailRepository, peopleId) as T
            }
        })[CastDetailViewModel::class.java]
    }

}