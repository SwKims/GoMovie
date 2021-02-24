package com.ksw.gomovie.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ksw.gomovie.fragment.MovieAboutFragment

/**
 * Created by KSW on 2021-02-24
 */
class MovieDetailTabAdapter(
    private val movieId: Int,
    fragmentManager: FragmentManager,
    private var totalTabs: Int
): FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> {
                return MovieAboutFragment(movieId)
            }
            1 -> {
                return MovieAboutFragment(movieId)
            }
            2 -> {
                return MovieAboutFragment(movieId)
            }
        }
        return MovieAboutFragment(movieId)
    }

    override fun getCount(): Int {
        return totalTabs
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "About"
            1 -> "Cast"
            2 -> "Crew"
            else -> null
        }
    }

}
