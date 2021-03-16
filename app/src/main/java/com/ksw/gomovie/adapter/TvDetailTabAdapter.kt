package com.ksw.gomovie.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ksw.gomovie.fragment.TvAboutFragment
import com.ksw.gomovie.fragment.TvCastFragment
import com.ksw.gomovie.fragment.TvCrewFragment
import com.ksw.gomovie.fragment.TvSeasonsFragment

/**
 * Created by KSW on 2021-03-15
 */

class TvDetailTabAdapter(
    private val tvId: Int,
    fragmentManager: FragmentManager,
    private var totalTabs: Int
) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return TvAboutFragment(tvId)
            }
            1 -> {
                return TvCastFragment(tvId)
            }
            2 -> {
                return TvCrewFragment(tvId)
            }
            3 -> {
                return TvSeasonsFragment(tvId)
            }
        }
        return TvAboutFragment(tvId)
    }

    override fun getCount(): Int {
        return totalTabs
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "About"
            1 -> "Cast"
            2 -> "Crew"
            3 -> "Seasons"
            else -> null
        }

    }

}