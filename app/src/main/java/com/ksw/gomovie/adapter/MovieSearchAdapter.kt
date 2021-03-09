package com.ksw.gomovie.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ksw.gomovie.R
import com.ksw.gomovie.activity.MovieDetailActivity
import com.ksw.gomovie.databinding.ItemMovieListBinding
import com.ksw.gomovie.model.main.Movie
import com.ksw.gomovie.util.Constants.Companion.IMAGE_BASE_URL

/**
 * Created by KSW on 2021-03-03여
 */

class MovieSearchAdapter(private val movies: ArrayList<Movie>, private val context: Context) :
    RecyclerView.Adapter<MovieSearchAdapter.SearchListHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchListHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.item_movie_list, parent, false)
        return SearchListHolder(
            ItemMovieListBinding.bind(view)
        )
    }


    override fun onBindViewHolder(holder: SearchListHolder, position: Int) {
        val itemSearch = movies[position]
        holder.bindSearchData(itemSearch, context)
    }

    override fun getItemCount(): Int {
        return movies.size
    }


    class SearchListHolder(private val binding: ItemMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bindSearchData(itemSearch: Movie?, context: Context) {

            binding.tvMovieTitle.text = itemSearch?.title
            binding.tvRating.text = "⭐ " + itemSearch?.voteAverage.toString()

            if (!itemSearch?.posterPath.isNullOrEmpty()) {
                val posterUrl = IMAGE_BASE_URL + itemSearch?.posterPath
                Glide.with(itemView.context)
                    .load(posterUrl)
                    .into(binding.ivMovieImage)
            } else {
                Glide.with(itemView.context)
                    .load(R.drawable.ic_error)
                    .into(binding.ivMovieImage)
            }

            itemView.setOnClickListener {

                val intent = Intent(context, MovieDetailActivity::class.java)
                intent.putExtra("id", itemSearch?.id)
                val options = ActivityOptions.makeSceneTransitionAnimation(
                    context as Activity?,
                    Pair(binding.movieCard, "imageTransition")
                )
                context.startActivity(intent, options.toBundle())

            }


        }

    }
}