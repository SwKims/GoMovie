package com.ksw.gomovie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.ksw.gomovie.adapter.TvTabAdapter
import com.ksw.gomovie.databinding.TvMainFragmentBinding

/**
 * Created by KSW on 2021-03-12
 */

class TvFragment: Fragment() {

    private lateinit var binding: TvMainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = TvMainFragmentBinding.inflate(inflater, container, false)

        binding.tabTv.addTab(binding.tabTv.newTab().setText("Now Playing"))
        binding.tabTv.addTab(binding.tabTv.newTab().setText("On Air Today"))
        binding.tabTv.addTab(binding.tabTv.newTab().setText("Popular"))

        val adapter = TvTabAdapter(
            requireFragmentManager(),
            binding.tabTv.tabCount
        )

        binding.tvPager.adapter = adapter
        binding.tvPager.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(
                binding.tabTv
            )
        )
        binding.tabTv.setupWithViewPager(binding.tvPager)

        return binding.root
    }

}