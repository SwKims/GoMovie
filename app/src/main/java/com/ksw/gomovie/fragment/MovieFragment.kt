package com.ksw.gomovie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.ksw.gomovie.adapter.MovieTabAdapter
import com.ksw.gomovie.databinding.MovieMainFragmentBinding

/**
 * Created by KSW on 2021-02-23
 */

class MovieFragment: Fragment() {

    /*private var _binding: MovieMainFragmentBinding?= null
    private val binding get() = _binding!!*/
    private lateinit var binding: MovieMainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = MovieMainFragmentBinding.inflate(inflater, container, false)
//        binding.tabMovie.addTab(binding.tabMovie.newTab().setText("Now Playing"))
        binding.tabMovie.addTab(binding.tabMovie.newTab().setText("Popular"))
        binding.tabMovie.addTab(binding.tabMovie.newTab().setText("Top Rated"))
        binding.tabMovie.addTab(binding.tabMovie.newTab().setText("Upcoming"))

        val adapter =
            MovieTabAdapter(
                requireFragmentManager(),
                binding.tabMovie.tabCount
            )

        binding.moviePager.adapter = adapter
        binding.moviePager.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(
                binding.tabMovie
            )
        )
        binding.tabMovie.setupWithViewPager(binding.moviePager)

        return binding.root
    }

}