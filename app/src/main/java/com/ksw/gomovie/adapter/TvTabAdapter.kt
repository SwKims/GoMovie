package com.ksw.gomovie.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ksw.gomovie.fragment.TvListFragment

/**
 * Created by KSW on 2021-03-12
 */

class TvTabAdapter(
    fragmentManager: FragmentManager,
    private var totalTabs: Int
) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return TvListFragment(
                    "airing_today"
                )
            }
            1 -> {
                return TvListFragment(
                    "on_the_air"
                )
            }
            2 -> {
                return TvListFragment(
                    "popular"
                )
            }
        }
        return TvListFragment("popular")
    }

    override fun getCount(): Int {
        return totalTabs
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Now Playing"
            1 -> "On Air Today"
            2 -> "Popular"
            else -> null
        }
    }

}