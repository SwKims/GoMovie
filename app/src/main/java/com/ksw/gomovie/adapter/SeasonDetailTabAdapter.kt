package com.ksw.gomovie.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ksw.gomovie.fragment.TvSeasonAboutFragment
import com.ksw.gomovie.fragment.TvSeasonCastFragment
import com.ksw.gomovie.fragment.TvSeasonEpisodeListFragment

/**
 * Created by KSW on 2021-03-17
 */

class SeasonDetailTabAdapter(
    private val seasonNumber: Int,
    private val tvId: Int,
    private val context: Context,
    fragmentManager: FragmentManager,
    private var totalTabs: Int
) : FragmentPagerAdapter(fragmentManager) {


    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return TvSeasonAboutFragment(tvId, seasonNumber)
            }
            1 -> {
                return TvSeasonEpisodeListFragment(tvId, seasonNumber)
            }
        }
        return TvSeasonCastFragment(tvId, seasonNumber)
    }

    override fun getCount(): Int {
        return totalTabs
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "About"
            1 -> "Episodes"
            2 -> "Cast"
            else -> null
        }
    }

}
