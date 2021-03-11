//package com.ksw.gomovie.fragment
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import dagger.hilt.android.AndroidEntryPoint
//import androidx.fragment.app.viewModels
//import androidx.recyclerview.widget.ItemTouchHelper
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.ksw.gomovie.adapter.FavoriteMovieAdapter
//import com.ksw.gomovie.databinding.ItemMovieFavoriteBinding
//import com.ksw.gomovie.viewmodel.FavoriteMoviesViewModel
//
///**
// * Created by KSW on 2021-03-10
// */
//
//@AndroidEntryPoint
//class FavoriteMovieFragment : Fragment() {
//
//    private var _binding: ItemMovieFavoriteBinding? = null
//    private val binding: ItemMovieFavoriteBinding
//        get() = _binding!!
//
//    private val favoriteMoviesViewModel: FavoriteMoviesViewModel by viewModels()
//
//    lateinit var favoriteAdapter: FavoriteMovieAdapter
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = ItemMovieFavoriteBinding.inflate(inflater, container, false)
//
//        return binding.root
//
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val linearLayoutManager = LinearLayoutManager(activity)
//        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
//        binding.rvMovies.layoutManager = linearLayoutManager
//        binding.rvMovies.adapter = favoriteAdapter
//
//
//
//    }
//
//
//
//
//
//}