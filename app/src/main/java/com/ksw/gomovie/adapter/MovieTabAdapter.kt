package com.ksw.gomovie.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ksw.gomovie.fragment.MovieListFragment

/**
 * Created by KSW on 2021-02-23
 */

class MovieTabAdapter(
    fragmentManager: FragmentManager,
    private var totalTabs: Int
): FragmentPagerAdapter(fragmentManager!!) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return MovieListFragment(
                    "now_playing"
                )
            }
            1 -> {
                return MovieListFragment(
                    "popular"
                )
            }
            2 -> {
                return MovieListFragment(
                    "top_rated"
                )
            }
            3 -> {
                return MovieListFragment(
                    "upcoming"
                )
            }
        }
        return MovieListFragment("upcoming")
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Now Playing"
            1 -> "Popular"
            2 -> "Top Rated"
            3 -> "Upcoming"
            else -> null
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }


}
