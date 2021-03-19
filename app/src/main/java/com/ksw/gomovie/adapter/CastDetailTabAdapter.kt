package com.ksw.gomovie.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ksw.gomovie.fragment.CastAboutFragment
import com.ksw.gomovie.fragment.CastMovieFragment
import com.ksw.gomovie.fragment.CastShowFragment

/**
 * Created by KSW on 2021-03-05
 */

class CastDetailTabAdapter(
    private val castId: Int,
    fragmentManager: FragmentManager,
    private var totalTabs: Int
): FragmentPagerAdapter(fragmentManager) {


    override fun getItem(position: Int): Fragment {
       when(position) {
           0 -> {
               return CastAboutFragment(
                   castId
               )
           }
           1 -> {
               return CastMovieFragment(
                   castId
               )
           }
       }
        return CastShowFragment(
            castId
        )
    }

    override fun getCount(): Int {
        return totalTabs
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "About"
            1 -> "Movies"
            2 -> "Shows"
            else -> null
        }
    }

}