package com.ksw.gomovie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ksw.gomovie.adapter.AwardsMovieListAdapter
import com.ksw.gomovie.databinding.AwardsFragmentBinding
import com.ksw.gomovie.model.main.FeaturedMovieItem
import com.ksw.gomovie.network.MovieServiceApi
import com.ksw.gomovie.network.NetworkModule

/**
 * Created by KSW on 2021-03-18
 */

// https://api.themoviedb.org/3/discover/movie?api_key=4defd070082d28cda9bb902140bcf8d0&language=ko-KR&region=kr&sort_by=popularity.desc&include_adult=false&include_video=false&primary_release_year=2019
// https://api.themoviedb.org/3/list/144105?api_key=4defd070082d28cda9bb902140bcf8d0&language=ko-KR


class AwardsFragment : Fragment() {

    private lateinit var binding: AwardsFragmentBinding

    private lateinit var awardsMovieListAdapter: AwardsMovieListAdapter
    private val awardMovieList = ArrayList<FeaturedMovieItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = AwardsFragmentBinding.inflate(inflater, container, false)
//        val apiServiceApi: MovieServiceApi = NetworkModule.getClient()

        awardMovieList()
        populatingAwardList()

        return binding.root
    }


    private fun awardMovieList() {
        awardMovieList.add(
            0,
            FeaturedMovieItem(
                "All Time 영화 Top 10",
                "https://image.tmdb.org/t/p/original/iNh3BivHyg5sQRPP1KOkzguEX0H.jpg",
                144105
            )
        )
        awardMovieList.add(
            1,
            FeaturedMovieItem(
                "Golden Globe Winners 2020",
                "https://image.tmdb.org/t/p/original/ApiBzeaa95TNYliSbQ8pJv4Fje7.jpg",
                132860
            )
        )
        awardMovieList.add(
            2,
            FeaturedMovieItem(
                "Oscar 2019",
                "https://image.tmdb.org/t/p/original/jNUpYq2jRSwQM89vST9yQZelMSu.jpg",
                118240
            )
        )
        awardMovieList.add(
            3,
            FeaturedMovieItem(
                "Oscar 2018",
                "https://image.tmdb.org/t/p/original/rgyhSn3mINvkuy9iswZK0VLqQO3.jpg",
                63978
            )
        )

    }

    private fun populatingAwardList() {
        val linerLayoutManager = LinearLayoutManager(activity)
        linerLayoutManager.orientation = LinearLayoutManager.VERTICAL
        awardsMovieListAdapter = AwardsMovieListAdapter(awardMovieList, binding.root.context)
        binding.rvAwardsList.layoutManager = linerLayoutManager
        binding.rvAwardsList.setHasFixedSize(true)
        binding.rvAwardsList.adapter = awardsMovieListAdapter
    }

    override fun onResume() {
        populatingAwardList()
        super.onResume()
    }

}